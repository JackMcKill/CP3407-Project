package com.cp3407.wildernessweather.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cp3407.wildernessweather.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Setup custom app bar.
        TextView titleView = findViewById(R.id.tv_title);
        titleView.setText(String.valueOf("Settings"));

        final ImageButton backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                finish();
            }
        });

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