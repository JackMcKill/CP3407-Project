package com.cp3407.wildernessweather;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.cp3407.wildernessweather.database.WeatherReportViewModel;

import org.parceler.Parcels;

import java.util.Calendar;

public class SingleWeatherReportActivity extends AppCompatActivity {
    WeatherReportModel singleWeatherReport;
    WeatherReportViewModel viewModel;
    WeatherDataService weatherDataService;
    boolean isNewEntry;

    TextView cityNameView, stateView, applicableDateView, minTempView,
            maxTempView, tempView, windSpeedView, windDirectionView,
            airPressureView, humidityView, visibilityView, predictabilityView;

    ImageView stateImage;

    private DatePickerDialog datePickerDialog;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_weather_report);

        cityNameView = findViewById(R.id.tv_cityName);
        stateView = findViewById(R.id.tv_weatherStateName);
        stateImage = findViewById(R.id.iv_stateImage);
        applicableDateView = findViewById(R.id.tv_applicableDate);
        minTempView = findViewById(R.id.tv_minTemp);
        maxTempView = findViewById(R.id.tv_maxTemp);
        tempView = findViewById(R.id.tv_weatherTemp);
        windSpeedView = findViewById(R.id.tv_windSpeed);
        windDirectionView = findViewById(R.id.tv_windDirection);
        airPressureView = findViewById(R.id.tv_airPressure);
        humidityView = findViewById(R.id.tv_humidity);
        visibilityView = findViewById(R.id.tv_visibility);
        predictabilityView = findViewById(R.id.tv_predictability);

        progressDialog = new ProgressDialog(SingleWeatherReportActivity.this);

        viewModel = new ViewModelProvider(this).get(WeatherReportViewModel.class);
        weatherDataService = new WeatherDataService(SingleWeatherReportActivity.this);

        singleWeatherReport = (WeatherReportModel) Parcels.unwrap(getIntent().getParcelableExtra("report"));
        populateFields();

        applicableDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open a date-picker pop-up
                datePickerDialog.show();
            }
        });

        final ImageButton backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goBack();
            }
        });

        final ImageButton downloadButton = findViewById(R.id.btn_download);
        downloadButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                downloadData();
            }
        });
        updateDatabase();
        initialiseDatePicker();
    }

    private void initialiseDatePicker() {
        // Runs when new date is set
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1; // Default behaviour is Jan = 0, Dec = 11. This line changes it to Jan = 1, Dec = 12.
                String displayDate = convertDateString(day, month, year);
                String apiCallDate = convertDateString(year, month, day); // This is just the date in YYYY/MM/DD format, to be used in the API call below
                // Convert date string before inserting.
                applicableDateView.setText(displayDate);

                goToNewDate(apiCallDate);
            }
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

    public void populateFields() {
        cityNameView.setText(String.valueOf(singleWeatherReport.getCityName()));
        stateView.setText(String.valueOf(singleWeatherReport.getWeatherStateName()));
        // Set weather state image field to correct resource.
        stateImage.setImageResource(getStateImageResId(singleWeatherReport.getWeatherStateAbbr()));
        // Convert date string before inserting.
        applicableDateView.setText(String.valueOf(convertDateString(singleWeatherReport.getApplicableDate())));
        minTempView.setText(String.valueOf(" " + Math.round(singleWeatherReport.getMinTemp()) + "°"));
        maxTempView.setText(String.valueOf(" " + Math.round(singleWeatherReport.getMaxTemp()) + "°"));
        tempView.setText(String.valueOf(" " + Math.round(singleWeatherReport.getTheTemp()) + "°"));
        windSpeedView.setText(String.valueOf((Math.round(singleWeatherReport.getWindSpeed() * 100.0) / 100.0) + " Km/h"));
        windDirectionView.setText(String.valueOf(singleWeatherReport.getWindDirectionCompass() + " (" + Math.round(singleWeatherReport.getWindDirection()) + "°)"));
        airPressureView.setText(String.valueOf(singleWeatherReport.getAirPressure() + " in"));
        humidityView.setText(String.valueOf(singleWeatherReport.getHumidity() + "%"));
        visibilityView.setText(String.valueOf((Math.round(singleWeatherReport.getVisibility() * 100.0) / 100.0) + "mi"));
        predictabilityView.setText(String.valueOf(singleWeatherReport.getPredictability() + "%"));
    }

    /**
     * Returns the resource id of the image that corresponds to a weather state abbreviation.
     * For example:
     * "lc" -> R.drawable.light_cloud
     * "hr" -> R.drawable.heavy_rain
     *
     * @param abbreviation weather state abbreviation to get image for.
     * @return resource id of the corresponding image.
     */
    public int getStateImageResId(String abbreviation) {
        switch (abbreviation) {
            case "c":
                return R.drawable.clear;
            case "lc":
                return R.drawable.light_cloud;
            default:
                return R.drawable.clear;
        }
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
     * Returns to APIActivity.
     */
    public void goBack() {

    }

    /**
     * Downloads weather data to the local database.
     */
    public void downloadData() {

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