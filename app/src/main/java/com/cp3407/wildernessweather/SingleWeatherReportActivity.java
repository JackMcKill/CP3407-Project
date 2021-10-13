package com.cp3407.wildernessweather;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.cp3407.wildernessweather.database.WeatherReportViewModel;

import org.parceler.Parcels;

import java.util.Calendar;

public class SingleWeatherReportActivity extends AppCompatActivity {
    WeatherReportModel singleWeatherReport;
    WeatherReportViewModel viewModel;

    TextView cityNameView, stateView, applicableDateView, minTempView,
            maxTempView, tempView, windSpeedView, windDirectionView,
            airPressureView, humidityView, visibilityView, predictabilityView;

    ImageView stateImage;

    private DatePickerDialog datePickerDialog;

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

        viewModel = new ViewModelProvider(this).get(WeatherReportViewModel.class);

        if (Parcels.unwrap(getIntent().getParcelableExtra("report")) != null) {
            Log.i("Parcelable", "Parcelable object received");
            // Get the report from the intent and unwrap it
            singleWeatherReport = (WeatherReportModel) Parcels.unwrap(getIntent().getParcelableExtra("report"));
            populateFields();

        } else {
            Log.i("Parcelable", "No parcelable object received");
            // TODO code for when this activity is called without a Parcel object
        }

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

        initialiseDatePicker();

    }

    private void initialiseDatePicker() {
        // Runs when new date is set
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1; // Default behaviour is Jan = 0, Dec = 11. This line changes it to Jan = 1, Dec = 12.
                String date = convertDateString(day, month, year);
                // Convert date string before inserting.
                applicableDateView.setText(date);
            }

            // TODO open a new SingleWeatherReportActivity showing weather information for the new date

        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    // Adds the single weather report to the local database
    public void addToDatabase(View view) {
        viewModel.insert(singleWeatherReport); // TODO The ID value provided by metaweather is irrelevant to us, we need to create our own ID value for storing in the database

        Toast.makeText(SingleWeatherReportActivity.this, "Added to database", Toast.LENGTH_SHORT).show();
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
     * date elements as individual int values instead
     *
     * @param day   the calendar day (int)
     * @param month the calendar month (int)
     * @param year  the calendar year (int)
     * @return date string in DD/MM/YYYY format
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
}