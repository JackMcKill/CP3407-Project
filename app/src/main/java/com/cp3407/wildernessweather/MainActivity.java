package com.cp3407.wildernessweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class MainActivity extends AppCompatActivity {

    Button getWeatherData;
    TextView weatherData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWeatherData = findViewById(R.id.btn_getWeather);
        weatherData = findViewById(R.id.tv_weatherView);

        getWeatherData.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //Instantiate the RequestQueue
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                String url = "https://www.metaweather.com/api/location/search/?query=london";

                // Request string response
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });

                // Add request to the RequestQueue
                queue.add(stringRequest);
            }
        });
    }


}