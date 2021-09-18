package com.cp3407.wildernessweather;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.parceler.Parcels;

public class SingleWeatherReportActivity extends AppCompatActivity {
    WeatherReportModel weatherReport;

    TextView idView, stateView, stateAbbrView, windDirectionCompassView, createdView,
            applicableDateView, minTempView, maxTempView, tempView, windspeedView, windDirectionView,
            airPressureView, humidityView, visibilityView, predictabilityView; // TODO find a better way to do this (this is disgusting)

    Button unwrapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_weather_report);

        // Get the report from the intent and unwrap it
        weatherReport = (WeatherReportModel) Parcels.unwrap(getIntent().getParcelableExtra("report"));

        idView = findViewById(R.id.tv_id);
        stateView = findViewById(R.id.tv_weatherStateName);
        stateAbbrView = findViewById(R.id.tv_weatherStateAbbr);
        windDirectionCompassView = findViewById(R.id.tv_windDirectionCompass);
        createdView = findViewById(R.id.tv_created);
        applicableDateView = findViewById(R.id.tv_applicableDate);
        minTempView = findViewById(R.id.tv_minTemp);
        maxTempView = findViewById(R.id.tv_maxTemp);
        tempView = findViewById(R.id.tv_weatherTemp);
        windspeedView = findViewById(R.id.tv_windSpeed);
        windDirectionView = findViewById(R.id.tv_windDirection);
        airPressureView = findViewById(R.id.tv_airPressure);
        humidityView = findViewById(R.id.tv_humidity);
        visibilityView = findViewById(R.id.tv_visibility);
        predictabilityView = findViewById(R.id.tv_predictability);


        unwrapButton = findViewById(R.id.btn_unwrap);

    }


    public void addToDatabase(View view) {
        // TODO functionality to add a new entry to the database here
        Toast.makeText(SingleWeatherReportActivity.this, "Added to database", Toast.LENGTH_SHORT).show();
    }





    // This is just a helper method used to demo the unwrapping of the Parcelable object
    public void unWrapData(View view) {
        String stateName = weatherReport.getWeather_state_name();
        Log.i("parcel", "State name: " + stateName);

        idView.setText(String.valueOf("ID: " + weatherReport.getId()));
        stateView.setText("Weather State: " + weatherReport.getWeather_state_name());
        stateAbbrView.setText("Weather State Abbreviation: " + weatherReport.getWeather_state_abbr());
        windDirectionCompassView.setText(String.valueOf("Wind Direction Compass: " + weatherReport.getWind_direction_compass()));
        createdView.setText(String.valueOf("Created: " + weatherReport.getCreated()));
        applicableDateView.setText(String.valueOf("Created: " + weatherReport.getCreated()));
        minTempView.setText(String.valueOf("Min Temp: " + weatherReport.getMin_temp()));
        minTempView.setText(String.valueOf("Max Temp: " + weatherReport.getMax_temp()));
        tempView.setText(String.valueOf("Weather Temp: " + weatherReport.getThe_temp()));
        windspeedView.setText(String.valueOf("Wind Speed: " + weatherReport.getWind_speed()));
        windDirectionView.setText(String.valueOf("Wind Direction: " + weatherReport.getWind_direction()));
        airPressureView.setText(String.valueOf("Air Pressure: " + weatherReport.getAir_pressure()));
        humidityView.setText(String.valueOf("Humidity: " + weatherReport.getHumidity()));
        visibilityView.setText(String.valueOf("Visibility: " + weatherReport.getVisibility()));
        predictabilityView.setText(String.valueOf("Predictability: " + weatherReport.getPredictability()));
    }
}