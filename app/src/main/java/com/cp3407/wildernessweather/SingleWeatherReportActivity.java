package com.cp3407.wildernessweather;

import android.os.Bundle;
import android.util.Log;
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

        cityNameView = findViewById(R.id.tv_title);
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
            updateFavouriteButton();

        } else {
            Log.i("Parcelable", "No parcelable object received");
            // TODO code for when this activity is called without a Parcel object
        }

        // Setup custom app bar.

        final ImageButton backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                finish();
            }
        });

        final ImageButton downloadButton = findViewById(R.id.btn_download);
        downloadButton.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                downloadData();
            }
        });

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
        int weatherStateImageResID = getResources().getIdentifier("ic_" + singleWeatherReport.getWeatherStateAbbr(), "drawable", getPackageName());
        stateImage.setImageResource(weatherStateImageResID);
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

    private void updateFavouriteButton() {

        final ImageButton favouriteButton = findViewById(R.id.btn_favourite);
        favouriteButton.setImageResource(R.drawable.ic_favourite_gray);
        // TODO: set button image to ic_favourite if location is in favourites, else ic_favourite_gray.
        // This means that we need to keep track of which locations we have favourited.
        favouriteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                // TODO: favourite method in WeatherReportModel.
                favouriteButton.setImageResource(R.drawable.ic_favourite);
            }
        });
    }

    /**
     * Converts date string in format YYYY-MM-DD to the format DD/MM/YYYY.
     * @param dateString date string in old format.
     * @return date string in new format.
     */
    public String convertDateString(String dateString) {
        String[] parts = dateString.split("-");
        return parts[2] + "/" + parts[1] + "/" + parts[0];
    }

    /**
     * Downloads weather data to the local database.
     */
    public void downloadData() {

    }

}