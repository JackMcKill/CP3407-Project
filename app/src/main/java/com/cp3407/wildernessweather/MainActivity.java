package com.cp3407.wildernessweather;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button getWeatherData;
    TextView weatherData;

    WeatherDataService weatherDataService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWeatherData = findViewById(R.id.btn_getCityID);
        weatherData = findViewById(R.id.tv_weatherView);

        weatherDataService = new WeatherDataService(MainActivity.this);

    }

    public void getCityIdPressed(View view) {

        // Brisbane is temporarily hardcoded for testing
        weatherDataService.getCityID("brisbane", new WeatherDataService.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String cityID) {
                Toast.makeText(MainActivity.this, "Returned an ID of " + cityID, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getWeatherPressed(View view) {
        weatherDataService.getCityForecastByID("1100661");
    }
}