package com.cp3407.wildernessweather;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.parceler.Parcels;

import java.util.ArrayList;

public class APIactivity extends AppCompatActivity {

    EditText searchBox;
    Button getWeatherData;
    ListView weatherReports;
    WeatherDataService weatherDataService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apiactivity);

        searchBox = findViewById(R.id.et_searchBox);
        getWeatherData = findViewById(R.id.btn_getWeatherData);
        weatherReports = findViewById(R.id.lv_weatherReports);

        weatherDataService = new WeatherDataService(APIactivity.this);
    }

    public void getWeatherPressed(View view) {

        weatherDataService.getCityForecastByName(searchBox.getText().toString(), new WeatherDataService.ForecastByNameCallback() {
            @Override
            public void onError(String errorMessage) {
                Toast.makeText(APIactivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(ArrayList<WeatherReportModel> weatherReportModels) {
                WeatherReportModelListAdapter arrayAdapter = new WeatherReportModelListAdapter(APIactivity.this, R.layout.weather_report_list_item, weatherReportModels);
                weatherReports.setAdapter(arrayAdapter);

                weatherReports.setClickable(true);

                weatherReports.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    // This code is run when the user clicks on a list item
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        Log.i("ListView", i + " clicked!");

                        // Store the selected weather report in a new variable
                        WeatherReportModel singleWeatherReport = weatherReportModels.get(i);

                        // Send singleWeatherReport to a new activity inside an intent
                        Intent intent = new Intent(APIactivity.this, SingleWeatherReportActivity.class);
                        intent.putExtra("report", Parcels.wrap(singleWeatherReport));
                        startActivity(intent);
                    }
                });
            }
        });
    }
}
