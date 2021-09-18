package com.cp3407.wildernessweather;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.parceler.Parcels;

public class SingleWeatherReportActivity extends AppCompatActivity {
    TextView stateView, stateAbbrView, tempView;
    Button unwrapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_weather_report);

        stateView = findViewById(R.id.tv_weatherStateName);
        stateAbbrView = findViewById(R.id.tv_weatherStateAbbr);
        tempView = findViewById(R.id.tv_weatherTemp);

        unwrapButton = findViewById(R.id.btn_unwrap);

    }

    // This method is just used to demo the unwrapping of the Parcelable object
    public void unWrapData(View view) {
        // Get the report from the intent and unwrap it
        WeatherReportModel singleReport = (WeatherReportModel) Parcels.unwrap(getIntent().getParcelableExtra("report"));

        String stateName = singleReport.getWeather_state_name();
        Log.i("parcel", "State name: " + stateName);

        stateView.setText("Weather State: " + singleReport.getWeather_state_name());
        stateAbbrView.setText("Weather State Abbreviation: " + singleReport.getWeather_state_abbr());
        tempView.setText(String.valueOf("Weather Temp: " + singleReport.getThe_temp()));
    }
}