package com.cp3407.wildernessweather;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cp3407.wildernessweather.database.WeatherReportViewModel;

public class DbActivity extends AppCompatActivity implements WeatherReportListAdapter.OnDeleteClickListener {

    private WeatherReportViewModel viewModel;
    private WeatherReportListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);

        // Setup custom app bar.
        TextView titleView = findViewById(R.id.tv_title);
        titleView.setText(R.string.history);
        ImageButton btn_externalDB = findViewById(R.id.btn_externalDB);

        final ImageButton backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(v -> finish());

        RecyclerView recyclerView = findViewById(R.id.rv_databaseList);
        adapter = new WeatherReportListAdapter(this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        int woeid = Integer.parseInt(getIntent().getStringExtra("woeid"));
        viewModel = new ViewModelProvider(this).get(WeatherReportViewModel.class);

        if (woeid == 0) {
            Log.i("database", "woeid = " + woeid);
            viewModel.getAllWeatherReports().observe(this, weatherReportModels -> adapter.setWeatherReports(weatherReportModels));
        } else {
            titleView.setText(getIntent().getStringExtra("cityName") + " History");
            btn_externalDB.setVisibility(View.INVISIBLE);
            Log.i("database", "woeid = " + woeid);
            viewModel.getSelectWeatherReports(woeid).observe(this, weatherReportModels -> {
                Log.i("database", "number of entries: " + weatherReportModels.size());
                adapter.setWeatherReports(weatherReportModels);
            });
        }

        btn_externalDB.setOnClickListener(view -> {
            Intent intent = new Intent(this, ExternalDatabaseActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void OnDeleteClickListener(WeatherReportModel myModel) {
        Toast.makeText(this, "Item Deleted", Toast.LENGTH_SHORT).show();
        viewModel.delete(myModel);
    }
}