package com.cp3407.wildernessweather;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cp3407.wildernessweather.database.ConnectToDatabase;
import com.cp3407.wildernessweather.database.WeatherReportViewModel;

import java.util.List;

public class DbActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private WeatherReportViewModel viewModel;
    private WeatherReportListAdapter adapter;
    TextView databaseTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);

        recyclerView = findViewById(R.id.rv_databaseList);
        adapter = new WeatherReportListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider(this).get(WeatherReportViewModel.class);
        viewModel.getAllWeatherReports().observe(this, new Observer<List<WeatherReportModel>>() {
            @Override
            public void onChanged(List<WeatherReportModel> weatherReportModels) {
                adapter.setWeatherReports(weatherReportModels);
            }
        });

        databaseTextView = findViewById(R.id.connectionStatus);

        // Begin ASync task to collect data from database.
        ConnectToDatabase asyncTask = new ConnectToDatabase(new ConnectToDatabase.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                databaseTextView.setText(output);
            }
        });
        asyncTask.execute();
    }
}