package com.cp3407.wildernessweather;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    Button getWeatherData;
    TextView weatherData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWeatherData = findViewById(R.id.btn_getWeather);
        weatherData = findViewById(R.id.tv_weatherView);
    }

    public void getWeatherPressed(View view) {
        // Instantiate the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        String url = "https://www.metaweather.com/api/location/1100661/"; // 1100661 is the code for Brisbane. Just hardcoded for now while prototyping.

        // Request string response
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            weatherData.setText(response.toString());
            Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
        }, error -> Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show());
        // Add the request to the RequestQueue
        queue.add(request);
    }
}