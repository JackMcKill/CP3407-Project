package com.cp3407.wildernessweather.settings;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.cp3407.wildernessweather.R;

public class DisplaySettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner unitsOfMeasurementSpinner;
    private Spinner fontSizeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_settings);
        this.setTitle("Display Settings");

        unitsOfMeasurementSpinner = findViewById(R.id.spin_unitOfMeasurement);
        fontSizeSpinner = findViewById(R.id.spin_fontSize);

        setArrayAdapters();
    }

    // This method sets the values of the spinners using array adapters
    public void setArrayAdapters() {
        // Array adapter for units of measurement spinner
        ArrayAdapter<CharSequence> unitsOfMeasurementAdapter = ArrayAdapter.createFromResource(this, R.array.units_of_measurement, R.layout.spinner_item);
        unitsOfMeasurementAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitsOfMeasurementSpinner.setAdapter(unitsOfMeasurementAdapter);

        unitsOfMeasurementSpinner.setOnItemSelectedListener(this);

        // Array adapter for font size spinner
        ArrayAdapter<CharSequence> textSizeAdapter = ArrayAdapter.createFromResource(this, R.array.font_sie, R.layout.spinner_item);
        textSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fontSizeSpinner.setAdapter(textSizeAdapter);
        fontSizeSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // code here to save item selection
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void savePressed(View view) {
        // TODO Sprint 2: save selections to SharedPreferences
        // Go back to SettingsActivity
    }
}