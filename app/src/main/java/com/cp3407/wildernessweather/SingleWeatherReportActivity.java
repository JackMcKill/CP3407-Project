package com.cp3407.wildernessweather;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cp3407.wildernessweather.database.WeatherReport;
import com.cp3407.wildernessweather.database.WeatherReportViewModel;

import org.parceler.Parcels;

import java.lang.reflect.Field;

public class SingleWeatherReportActivity extends AppCompatActivity {
    WeatherReportModel singleWeatherReport;
    WeatherReportViewModel viewModel;

    TextView idView, stateView, stateAbbrView, windDirectionCompassView, createdView,
            applicableDateView, minTempView, maxTempView, tempView, windspeedView, windDirectionView,
            airPressureView, humidityView, visibilityView, predictabilityView; // TODO find a better way to do this (this is disgusting)

    Button unwrapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_weather_report);

        // Get the report from the intent and unwrap it
        singleWeatherReport = (WeatherReportModel) Parcels.unwrap(getIntent().getParcelableExtra("report"));

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

        viewModel = new ViewModelProvider(this).get(WeatherReportViewModel.class);

    }


    public void addToDatabase(View view) {
        // TODO functionality to add a new entry to the database here


        long id = singleWeatherReport.id;
        String weather_state_name = singleWeatherReport.weather_state_name;
        String weather_state_abbr = singleWeatherReport.weather_state_abbr;
        String wind_direction_compass = singleWeatherReport.wind_direction_compass;
        String created = singleWeatherReport.created;
        String applicable_date = singleWeatherReport.applicable_date;
        float min_temp = singleWeatherReport.min_temp;
        float max_temp = singleWeatherReport.max_temp;
        float the_temp = singleWeatherReport.the_temp;
        float wind_speed = singleWeatherReport.wind_speed;
        float wind_direction = singleWeatherReport.wind_direction;
        int air_pressure = singleWeatherReport.air_pressure;
        int humidity = singleWeatherReport.humidity;
        float visibility = singleWeatherReport.visibility;
        int predictability = singleWeatherReport.predictability;

        // TODO this needs fixing - we should be able to reuse the WeatherReportModel class. (See the TODO in WeatherReportModel file)
        WeatherReport weatherReport = new WeatherReport(id, weather_state_name, weather_state_abbr,
                wind_direction_compass, created, applicable_date, min_temp, max_temp, the_temp,
                wind_speed, wind_direction, air_pressure, humidity, visibility, predictability);

        viewModel.insert(weatherReport);

        Toast.makeText(SingleWeatherReportActivity.this, "Added to database", Toast.LENGTH_SHORT).show();
    }


    // This is just a helper method used to demo the unwrapping of the Parcelable object
    public void unWrapData(View view) {
        String stateName = singleWeatherReport.getWeather_state_name();
        Log.i("parcel", "State name: " + stateName);

        idView.setText(String.valueOf("ID: " + singleWeatherReport.getId()));
        stateView.setText(String.valueOf("Weather State: " + singleWeatherReport.getWeather_state_name()));
        stateAbbrView.setText(String.valueOf("Weather State Abbreviation: " + singleWeatherReport.getWeather_state_abbr()));
        windDirectionCompassView.setText(String.valueOf("Wind Direction Compass: " + singleWeatherReport.getWind_direction_compass()));
        createdView.setText(String.valueOf("Created: " + singleWeatherReport.getCreated()));
        applicableDateView.setText(String.valueOf("Created: " + singleWeatherReport.getApplicable_date()));
        minTempView.setText(String.valueOf("Min Temp: " + singleWeatherReport.getMin_temp()));
        minTempView.setText(String.valueOf("Max Temp: " + singleWeatherReport.getMax_temp()));
        tempView.setText(String.valueOf("Weather Temp: " + singleWeatherReport.getThe_temp()));
        windspeedView.setText(String.valueOf("Wind Speed: " + singleWeatherReport.getWind_speed()));
        windDirectionView.setText(String.valueOf("Wind Direction: " + singleWeatherReport.getWind_direction()));
        airPressureView.setText(String.valueOf("Air Pressure: " + singleWeatherReport.getAir_pressure()));
        humidityView.setText(String.valueOf("Humidity: " + singleWeatherReport.getHumidity()));
        visibilityView.setText(String.valueOf("Visibility: " + singleWeatherReport.getVisibility()));
        predictabilityView.setText(String.valueOf("Predictability: " + singleWeatherReport.getPredictability()));
    }
}