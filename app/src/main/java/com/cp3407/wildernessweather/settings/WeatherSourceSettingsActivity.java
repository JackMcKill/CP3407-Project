package com.cp3407.wildernessweather.settings;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cp3407.wildernessweather.R;

public class WeatherSourceSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_source_settings);

        // Setup custom app bar.
        TextView titleView = findViewById(R.id.tv_title);
        titleView.setText(String.valueOf("Change Weather Source"));

        final ImageButton backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                finish();
            }
        });

    }

    public void savePressed(View view) {
        // TODO Sprint 2: save selections to SharedPreferences
        // Go back to SettingsActivity
    }
}