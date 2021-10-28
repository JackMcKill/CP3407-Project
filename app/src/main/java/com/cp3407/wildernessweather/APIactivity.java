package com.cp3407.wildernessweather;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.parceler.Parcels;

import java.util.ArrayList;

public class APIactivity extends AppCompatActivity {

    SearchView searchBox;
    ListView weatherReports;
    WeatherDataService weatherDataService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apiactivity);

        searchBox = findViewById(R.id.sv_searchResultPage);
        weatherReports = findViewById(R.id.lv_weatherReports);

        searchBox.setQuery(getIntent().getStringExtra("cityName"), false);

        weatherDataService = new WeatherDataService(APIactivity.this);
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
                WeatherReportModelListAdapter arrayAdapter = new WeatherReportModelListAdapter(APIactivity.this, R.layout.weather_report_list_item, weatherReportModels);
                weatherReports.setAdapter(arrayAdapter);

                // This code is run when the user clicks on a list item
                weatherReports.setOnItemClickListener((adapterView, view, i, l) -> {

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
