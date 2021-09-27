package com.cp3407.wildernessweather.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.cp3407.wildernessweather.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        this.setTitle("Settings"); // This changes the text displayed on the Action Bar
        // TODO add a "back" button in the Action Bar
        // TODO add functionality
    }

    public void displayPrefsClicked(View view) {
        Intent intent = new Intent(this, DisplaySettingsActivity.class);
        startActivity(intent);
    }

    public void weatherSourceClicked(View view) {
        Intent intent = new Intent(this, WeatherSourceSettingsActivity.class);
        startActivity(intent);
    }
}