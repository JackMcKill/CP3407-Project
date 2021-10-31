package com.cp3407.wildernessweather;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.parceler.Parcels;

import java.util.ArrayList;

public class APIactivity extends AppCompatActivity {
    private SharedPreferences settingsData;

    SearchView sv_searchBox;
    ListView lv_weatherReports;
    WeatherDataService weatherDataService;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apiactivity);

        sv_searchBox = findViewById(R.id.sv_searchResultPage);
        settingsData = getSharedPreferences("settings", Context.MODE_PRIVATE);

        lv_weatherReports = findViewById(R.id.lv_weatherReports);

        sv_searchBox.setQuery(getIntent().getStringExtra("cityName"), false);
        sv_searchBox.setIconified(false);
        weatherDataService = new WeatherDataService(APIactivity.this);

        // Setup custom app bar.
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("Location Search");

        final ImageButton btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(v -> finish());

        getWeatherReports(getIntent().getStringExtra("cityName"));
    }

    public void getWeatherReports(String cityName) {
        weatherDataService.getCityForecastByName(cityName, new WeatherDataService.ForecastByNameCallback() {
            @Override
            public void onError(String errorMessage) {
                Toast.makeText(APIactivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(ArrayList<WeatherReportModel> weatherReportModels) {
                boolean isMetric = settingsData.getBoolean("isMetric", true);
                WeatherReportModelListAdapter arrayAdapter = new WeatherReportModelListAdapter(APIactivity.this, R.layout.weather_report_list_item, weatherReportModels, isMetric);
                lv_weatherReports.setAdapter(arrayAdapter);

                // This code is run when the user clicks on a list item
                lv_weatherReports.setOnItemClickListener((adapterView, view, i, l) -> {

                    Log.i("ListView", i + " clicked!");

                    // Store the selected weather report in a new variable
                    WeatherReportModel singleWeatherReport = weatherReportModels.get(i);

                    // Send singleWeatherReport to a new activity inside an intent
                    Intent intent = new Intent(APIactivity.this, SingleWeatherReportActivity.class);
                    intent.putExtra("report", Parcels.wrap(singleWeatherReport));
                    startActivity(intent);
                });
            }
        });
    }
}
