package com.cp3407.wildernessweather.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.cp3407.wildernessweather.R;

public class DisplaySettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private SharedPreferences settingsData;

    private SwitchCompat darkModeSwitch;
    private Spinner unitsOfMeasurementSpinner;
    private Spinner fontSizeSpinner;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_settings);
        this.setTitle("Display Settings");

        settingsData = getSharedPreferences("settings", Context.MODE_PRIVATE);

        darkModeSwitch = findViewById(R.id.sw_darkmode);
        unitsOfMeasurementSpinner = findViewById(R.id.spin_unitOfMeasurement);
        fontSizeSpinner = findViewById(R.id.spin_fontSize);
        saveButton = findViewById(R.id.btn_saveDisplaySettings);

        initialiseSettings();
    }


    // Initialises the UI and restores the displayed settings to what is stored in SharedPrefs
    private void initialiseSettings() {

        boolean isDarkMode = settingsData.getBoolean("isDarkMode", false);
        boolean isMetric = settingsData.getBoolean("isMetric", true);
        boolean isRegular = settingsData.getBoolean("isRegular", true);

        setSwitch(isDarkMode);
        setArrayAdapters(isMetric, isRegular);
    }

    private void setSwitch(boolean isDarkMode) {
        darkModeSwitch.setChecked(isDarkMode);
    }

    // This method sets the values of the spinners using array adapters
    public void setArrayAdapters(boolean isMetric, boolean isRegular) {

        // Array adapter for units of measurement spinner
        ArrayAdapter<CharSequence> unitsOfMeasurementAdapter = ArrayAdapter.createFromResource(this, R.array.units_of_measurement, R.layout.spinner_item);
        unitsOfMeasurementAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitsOfMeasurementSpinner.setAdapter(unitsOfMeasurementAdapter);
        unitsOfMeasurementSpinner.setOnItemSelectedListener(this);
        if (isMetric) {
            unitsOfMeasurementSpinner.setSelection(0); // 0 = metric, 1 = imperial
        } else {
            unitsOfMeasurementSpinner.setSelection(1);
        }

        // Array adapter for font size spinner
        ArrayAdapter<CharSequence> textSizeAdapter = ArrayAdapter.createFromResource(this, R.array.font_sie, R.layout.spinner_item);
        textSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fontSizeSpinner.setAdapter(textSizeAdapter);
        fontSizeSpinner.setOnItemSelectedListener(this);
        if (isRegular) {
            fontSizeSpinner.setSelection(0);
        } else {
            fontSizeSpinner.setSelection(1);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // code here to save item selection
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // do nothing
    }

    public void savePressed(View view) {
        // save selections to SharedPreferences
        // Go back to SettingsActivity
    }
}