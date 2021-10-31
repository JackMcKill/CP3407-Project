package com.cp3407.wildernessweather;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.cp3407.wildernessweather.database.WeatherReportViewModel;

import org.parceler.Parcels;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class SingleWeatherReportActivity extends AppCompatActivity {
    private SharedPreferences favourites;

    WeatherReportModel singleWeatherReport;
    WeatherReportViewModel viewModel;
    WeatherDataService weatherDataService;
    boolean isNewEntry;

    TextView tv_cityName, tv_state, tv_applicableDate, tv_minTemp,
            tv_maxTemp, tv_temp, tv_windSpeed, tv_windDirection,
            tv_airPressure, tv_humidity, tv_visibility, tv_predictability;

    ImageView iv_state;
    ImageButton btn_back, btn_download, btn_history;

    private DatePickerDialog datePickerDialog;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_weather_report);
        SharedPreferences settingsData = getSharedPreferences("settings", Context.MODE_PRIVATE);
        favourites = getSharedPreferences("favourites", Context.MODE_PRIVATE);

        tv_cityName = findViewById(R.id.tv_title);
        tv_state = findViewById(R.id.tv_weatherStateName);
        iv_state = findViewById(R.id.iv_stateImage);
        tv_applicableDate = findViewById(R.id.tv_applicableDate);
        tv_minTemp = findViewById(R.id.tv_minTemp);
        tv_maxTemp = findViewById(R.id.tv_maxTemp);
        tv_temp = findViewById(R.id.tv_weatherTemp);
        tv_windSpeed = findViewById(R.id.tv_windSpeed);
        tv_windDirection = findViewById(R.id.tv_windDirection);
        tv_airPressure = findViewById(R.id.tv_airPressure);
        tv_humidity = findViewById(R.id.tv_humidity);
        tv_visibility = findViewById(R.id.tv_visibility);
        tv_predictability = findViewById(R.id.tv_predictability);

        btn_back = findViewById(R.id.btn_back);
        btn_download = findViewById(R.id.btn_download);
        btn_history = findViewById(R.id.btn_SingleWeatherHistory);

        progressDialog = new ProgressDialog(SingleWeatherReportActivity.this);

        viewModel = new ViewModelProvider(this).get(WeatherReportViewModel.class);
        weatherDataService = new WeatherDataService(SingleWeatherReportActivity.this);

        singleWeatherReport = Parcels.unwrap(getIntent().getParcelableExtra("report"));
        updateFavouriteButton();

        boolean isMetric = settingsData.getBoolean("isMetric", true);
        populateFields(isMetric);

        tv_applicableDate.setOnClickListener(view -> {
            // open a date-picker pop-up
            datePickerDialog.show();
        });

        updateDatabase();
        intialiseButtons();
        initialiseDatePicker();
    }

    private void intialiseButtons() {
        btn_back.setOnClickListener(v -> finish());
        btn_download.setOnClickListener(v -> {
            try {
                downloadData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        btn_history.setOnClickListener(view -> {
            Intent intent = new Intent(SingleWeatherReportActivity.this, DatabaseActivity.class);
            intent.putExtra("woeid", singleWeatherReport.getWoeid());
            intent.putExtra("cityName", singleWeatherReport.getCityName());
            startActivity(intent);
        });
    }

    private void initialiseDatePicker() {
        // Runs when new date is set
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month += 1; // Default behaviour is Jan = 0, Dec = 11. This line changes it to Jan = 1, Dec = 12.
            String displayDate = convertDateString(day, month, year);
            String apiCallDate = convertDateString(year, month, day); // This is just the date in YYYY/MM/DD format, to be used in the API call below
            // Convert date string before inserting.
            tv_applicableDate.setText(displayDate);

            goToNewDate(apiCallDate);
        };
        String initialDate = singleWeatherReport.getApplicableDate();

        // Sets the date that the datePicker is displays upon initialisation (set to current date of the weather report being viewed)
        int year = Integer.parseInt(initialDate.substring(0, 4));
        int month = Integer.parseInt(initialDate.substring(5, 7)) - 1;
        int day = Integer.parseInt(initialDate.substring(8, 10));

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);

        // Prevents users from selecting a date more than 1 week into the future
        long oneWeekFromNow = System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7;
        datePickerDialog.getDatePicker().setMaxDate(oneWeekFromNow);

        // Prevents users from selecting a date earlier than 2014
        Calendar minDate = Calendar.getInstance();
        minDate.set(2014, 0, 1); // Year, Month - 1, Day
        datePickerDialog.getDatePicker().setMinDate(minDate.getTimeInMillis() - 1000);
    }

    /*
     * Checks whether the database already contains an entry for the weather report being viewed
     * If the database does not contain an entry for this weather report, a new one is added
     * If the database already contains an entry, then nothing is added
     * */
    private void updateDatabase() {
        Log.i("newEntry", "current trueID: " + singleWeatherReport.getTrueID());
        viewModel.getAllWeatherReports().observe(this, weatherReportModels -> {

            if (weatherReportModels.size() == 0) { // this checks if the database is empty
                isNewEntry = true;
            } else {
                for (WeatherReportModel model : weatherReportModels) {
                    Log.i("newEntry", "trueID: " + model.getTrueID());
                    if (model.getTrueID() != singleWeatherReport.getTrueID()) {
                        isNewEntry = true;
                    } else {
                        isNewEntry = false;
                        Log.i("newEntry", "result set to false");
                        break;
                    }
                }
            }

            if (isNewEntry) {
                Log.i("newEntry", "result set to true, so this should be added to the database");
                viewModel.insert(singleWeatherReport);

            } else {
                Log.i("newEntry", "result set to false, so this should NOT be added to the database");
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void populateFields(boolean isMetric) {
        if (isMetric) {
            tv_cityName.setText(String.valueOf(singleWeatherReport.getCityName()));
            tv_state.setText(singleWeatherReport.getWeatherStateName());
            // Set weather state image field to correct resource.
            int weatherStateImageResID = getResources().getIdentifier("ic_" + singleWeatherReport.getWeatherStateAbbr(), "drawable", getPackageName());
            iv_state.setImageResource(weatherStateImageResID);
            // Convert date string before inserting.
            tv_applicableDate.setText(String.valueOf(convertDateString(singleWeatherReport.getApplicableDate())));
            tv_minTemp.setText(" " + Math.round(singleWeatherReport.getMinTemp()) + "°");
            tv_maxTemp.setText(" " + Math.round(singleWeatherReport.getMaxTemp()) + "°");
            tv_temp.setText(" " + Math.round(singleWeatherReport.getTheTemp()) + "°");
            tv_windSpeed.setText((Math.round(singleWeatherReport.getWindSpeed() * 100.0) / 100.0) + " Km/h");
            tv_windDirection.setText(singleWeatherReport.getWindDirectionCompass() + " (" + Math.round(singleWeatherReport.getWindDirection()) + "°)");
            tv_airPressure.setText(singleWeatherReport.getAirPressure() + " hPa");
            tv_humidity.setText(singleWeatherReport.getHumidity() + "%");
            tv_visibility.setText((Math.round(singleWeatherReport.getVisibility() * 100.0) / 100.0) + "km");
            tv_predictability.setText(singleWeatherReport.getPredictability() + "%");
        } else {
            // change the vales to the imperial system

            tv_cityName.setText(String.valueOf(singleWeatherReport.getCityName()));
            tv_state.setText(singleWeatherReport.getWeatherStateName());
            // Set weather state image field to correct resource.
            int weatherStateImageResID = getResources().getIdentifier("ic_" + singleWeatherReport.getWeatherStateAbbr(), "drawable", getPackageName());
            iv_state.setImageResource(weatherStateImageResID);
            // Convert date string before inserting.
            tv_applicableDate.setText(String.valueOf(convertDateString(singleWeatherReport.getApplicableDate())));
            tv_minTemp.setText(" " + Math.round(singleWeatherReport.getMinTempImperial()) + "°");
            tv_maxTemp.setText(" " + Math.round(singleWeatherReport.getMaxTempImperial()) + "°");
            tv_temp.setText(" " + Math.round(singleWeatherReport.getTheTempImperial()) + "°");
            tv_windSpeed.setText((Math.round(singleWeatherReport.getWindSpeedImperial() * 100.0) / 100.0) + " mph");
            tv_windDirection.setText(singleWeatherReport.getWindDirectionCompass() + " (" + Math.round(singleWeatherReport.getWindDirection()) + "°)");
            tv_airPressure.setText(singleWeatherReport.getAirPressure() + " hPa");
            tv_humidity.setText(singleWeatherReport.getHumidity() + "%");
            tv_visibility.setText((Math.round(singleWeatherReport.getVisibilityImperial() * 100.0) / 100.0) + "mi");
            tv_predictability.setText(singleWeatherReport.getPredictability() + "%");
        }

    }

    private void updateFavouriteButton() {
        boolean isFavourite;
        final ImageButton favouriteButton = findViewById(R.id.btn_favourite);
        // Get favourites as a set
        Set<String> favouriteLocations = favourites.getStringSet("locations", new HashSet<>());
        Log.i("favourites", "favourites: " + favouriteLocations);

        // If current location is in set, yellow star, else grey star
        if (favouriteLocations.contains(singleWeatherReport.getCityName())) {
            Log.i("favourites", "this is a favourite");
            favouriteButton.setImageResource(R.drawable.ic_favourite);
            isFavourite = true;
        } else {
            Log.i("favourites", "this is not a favourite");
            favouriteButton.setImageResource(R.drawable.ic_favourite_gray);
            isFavourite = false;
        }

        favouriteButton.setOnClickListener(v -> toggleFavourite(isFavourite));
    }

    public void toggleFavourite(boolean isFavourite) {
        Set<String> favouriteLocations = favourites.getStringSet("locations", new HashSet<>());
        SharedPreferences.Editor editor = favourites.edit();
        HashSet<String> favouriteLocationsEdited = new HashSet<>(favouriteLocations);
        if (isFavourite) {
            favouriteLocationsEdited.remove(singleWeatherReport.getCityName());
            Log.i("favourites", "removing this from favourites");
        } else {
            favouriteLocationsEdited.add(singleWeatherReport.getCityName());
            Log.i("favourites", "adding this to favourites");
        }
        editor.putStringSet("locations", favouriteLocationsEdited);
        editor.apply();
        Log.i("favourites", "favourites: " + favouriteLocations);
        updateFavouriteButton();
    }

    /**
     * Converts date string in format YYYY-MM-DD to the format DD/MM/YYYY.
     *
     * @param dateString date string in old format.
     * @return date string in new format.
     */
    public String convertDateString(String dateString) {
        String[] parts = dateString.split("-");
        return parts[2] + "/" + parts[1] + "/" + parts[0];
    }

    /**
     * Works just like {@link SingleWeatherReportActivity#convertDateString(String)}, but takes
     * date elements as individual int values instead. Can be used to build dates in different
     * formats, depending on which order the date elements are given in the method call.
     *
     * @param day   the calendar day (int)
     * @param month the calendar month (int)
     * @param year  the calendar year (int)
     * @return date string in param1/param2/param3 format
     */
    public String convertDateString(int day, int month, int year) {
        return day + "/" + month + "/" + year;
    }

    /**
     * Downloads weather data to the local database.
     */
    public void downloadData() throws Exception {
        ExportCSV.writeWithCsvListWriter(singleWeatherReport, new ExportCSV.ExportCSVCallback() {
            @Override
            public void onSuccess(String successMessage) {
                Toast.makeText(SingleWeatherReportActivity.this, successMessage, Toast.LENGTH_SHORT).show();
                String fileName = singleWeatherReport.getCityName() + "-" + singleWeatherReport.getApplicableDate() + ".csv";
                Log.i("export", "String: " + fileName);
                shareCSV(fileName);

            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(SingleWeatherReportActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Opens a share sheet to allow sharing of the CSV file
    public void shareCSV(String fileName) {
        // Generate the path to the CSV file
        final String FILEPATH = "data/data/com.cp3407.wildernessweather/";

        // Create a URI to identify and gain access to the CSV file
        Uri uri = Uri.parse("content://" + FILEPATH + fileName);
        Log.i("export", "uri: " + uri.toString());

        // Create an Intent to open a share sheet
        Intent sendIntent = new Intent(android.content.Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_STREAM, uri);

        startActivity(Intent.createChooser(sendIntent, "Send Mail"));
    }


    // This method performs an API call to retrieve weather information for a specified date and reloads the activity
    private void goToNewDate(String date) {
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        weatherDataService.getCityForecastByDate(singleWeatherReport.getWoeid(), date, singleWeatherReport.getCityName(), new WeatherDataService.ForecastByDateCallback() {
            @Override
            public void onError(String errorMessage) {
                System.out.println(errorMessage);
            }

            @Override
            public void onResponse(WeatherReportModel weatherReportModel) {
                Intent intent = new Intent(SingleWeatherReportActivity.this, SingleWeatherReportActivity.class);
                intent.putExtra("report", Parcels.wrap(weatherReportModel));
                finish();
                startActivity(intent);
            }
        });
    }
}
