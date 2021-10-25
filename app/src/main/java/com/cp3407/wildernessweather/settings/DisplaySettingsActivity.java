package com.cp3407.wildernessweather.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.cp3407.wildernessweather.R;

public class DisplaySettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private SharedPreferences settingsData;

    private SwitchCompat darkModeSwitch;
    private Spinner unitsOfMeasurementSpinner;
    private Spinner fontSizeSpinner;

    private boolean isDarkMode;
    private boolean isMetric;
    private boolean isRegular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_settings);
        this.setTitle("Display Settings");

        settingsData = getSharedPreferences("settings", Context.MODE_PRIVATE);

        darkModeSwitch = findViewById(R.id.sw_darkmode);
        unitsOfMeasurementSpinner = findViewById(R.id.spin_unitOfMeasurement);
        fontSizeSpinner = findViewById(R.id.spin_fontSize);

        isDarkMode = settingsData.getBoolean("isDarkMode", false);
        isMetric = settingsData.getBoolean("isMetric", true);
        isRegular = settingsData.getBoolean("isRegular", true);

        initialiseSettings();
    }


    // Initialises the UI and restores the displayed settings to what is stored in SharedPrefs
    private void initialiseSettings() {
        // Sets the value of the switch
        darkModeSwitch.setChecked(isDarkMode);

        // Sets the values of the spinners using array adapters
        setArrayAdapters();
    }


    public void setArrayAdapters() {
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
            fontSizeSpinner.setSelection(0); // 0 = regular font size, 1 = large font size
        } else {
            fontSizeSpinner.setSelection(1);
        }

    }

    // Save selections to SharedPreferences
    public void savePressed(View view) {
        SharedPreferences.Editor editor = settingsData.edit();

        editor.putBoolean("isDarkMode", darkModeSwitch.isChecked());
        editor.putBoolean("isMetric", unitsOfMeasurementSpinner.getSelectedItemPosition() == 0);
        editor.putBoolean("isRegular", fontSizeSpinner.getSelectedItemPosition() == 0);
        editor.apply();

        Toast.makeText(DisplaySettingsActivity.this, "Settings Saved", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // code here to save item selection
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // do nothing
    }

}