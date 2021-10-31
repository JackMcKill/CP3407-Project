package com.cp3407.wildernessweather;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cp3407.wildernessweather.database.WeatherReportViewModel;

import java.util.List;

public class DbActivity extends AppCompatActivity implements WeatherReportListAdapter.OnDeleteClickListener {

    private RecyclerView rv_databaseList;
    private WeatherReportViewModel viewModel;
    private WeatherReportListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);

        // Setup custom app bar.
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText(R.string.history);

        final ImageButton btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        rv_databaseList = findViewById(R.id.rv_databaseList);
        adapter = new WeatherReportListAdapter(this, this);
        rv_databaseList.setAdapter(adapter);
        rv_databaseList.setLayoutManager(new LinearLayoutManager(this));

        int woeid = Integer.parseInt(getIntent().getStringExtra("woeid"));
        viewModel = new ViewModelProvider(this).get(WeatherReportViewModel.class);

        if (woeid == 0) {
            Log.i("database", "woeid = " + woeid);
            viewModel.getAllWeatherReports().observe(this, new Observer<List<WeatherReportModel>>() {
                @Override
                public void onChanged(List<WeatherReportModel> weatherReportModels) {
                    adapter.setWeatherReports(weatherReportModels);
                }
            });
        } else {
            tv_title.setText(getIntent().getStringExtra("cityName") + " History");
            Log.i("database", "woeid = " + woeid);
            viewModel.getSelectWeatherReports(woeid).observe(this, new Observer<List<WeatherReportModel>>() {
                @Override
                public void onChanged(List<WeatherReportModel> weatherReportModels) {
                    Log.i("database", "number of entries: " + weatherReportModels.size());
                    adapter.setWeatherReports(weatherReportModels);
                }
            });
        }
    }

    @Override
    public void OnDeleteClickListener(WeatherReportModel myModel) {
        Toast.makeText(this, "Item Deleted", Toast.LENGTH_SHORT).show();
        viewModel.delete(myModel);
    }
}