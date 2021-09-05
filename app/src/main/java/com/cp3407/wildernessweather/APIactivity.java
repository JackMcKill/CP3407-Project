package com.cp3407.wildernessweather;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

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
            public void onResponse(List<WeatherReportModel> weatherReportModels) {
                ArrayAdapter<WeatherReportModel> arrayAdapter = new ArrayAdapter<>(APIactivity.this, android.R.layout.simple_list_item_1, weatherReportModels);
                weatherReports.setAdapter(arrayAdapter);
            }
        });
    }
}
