package com.cp3407.wildernessweather;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.cp3407.wildernessweather.database.WeatherReportViewModel;

import org.parceler.Parcels;

public class SingleWeatherReportActivity extends AppCompatActivity {
    WeatherReportModel singleWeatherReport;
    WeatherReportViewModel viewModel;

    TextView cityNameView, stateView, applicableDateView, minTempView,
            maxTempView, tempView, windSpeedView, windDirectionView,
            airPressureView, humidityView, visibilityView, predictabilityView;

    ImageView stateImage;

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

        singleWeatherReport = (WeatherReportModel) Parcels.unwrap(getIntent().getParcelableExtra("report"));
        populateFields();

        final ImageButton backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goBack();
            }
        });

        final ImageButton downloadButton = findViewById(R.id.btn_download);
        downloadButton.setOnClickListener(new View.OnClickListener() {

            // TODO change this back before merge - temporarily using this download button to act as a "add to database" button
            public void onClick(View v) {
//                downloadData();

                singleWeatherReport.setId(0); // id is set to 0 so that it can be autoincremented by Room
                viewModel.insert(singleWeatherReport);
                Toast.makeText(SingleWeatherReportActivity.this, "Added to database", Toast.LENGTH_SHORT).show();

            }
        });
    }

    // Adds the single weather report to the local database
    public void addToDatabase(View view) {
        singleWeatherReport.setId(0); // id is set to 0 so that it can be autoincremented by Room
        viewModel.insert(singleWeatherReport);

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