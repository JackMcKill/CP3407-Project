package com.cp3407.wildernessweather.settings;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.cp3407.wildernessweather.R;

public class WeatherSourceSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_source_settings);
        this.setTitle("Weather Source Settings"); // This changes the text displayed on the Action Bar
    }

    public void savePressed(View view) {
        // TODO Sprint 2: save selections to SharedPreferences
        // Go back to SettingsActivity
    }
}