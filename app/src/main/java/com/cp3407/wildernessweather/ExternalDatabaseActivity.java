package com.cp3407.wildernessweather;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cp3407.wildernessweather.database.ExternalBaseIntegration;
import com.cp3407.wildernessweather.database.ExternalWeatherReportListAdapter;
import com.cp3407.wildernessweather.database.WeatherReportViewModel;

import java.util.ArrayList;
import java.util.List;

public class ExternalDatabaseActivity extends AppCompatActivity {
    TextView tv_connectionStatus, tv_databaseDisplay;
    Button btn_retryConnection;
    ExternalBaseIntegration asyncTask;

    private ExternalWeatherReportListAdapter adapter;
    List<WeatherReportModel> databaseOutput;
    List<WeatherReportModel> localDatabaseList;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_database);
        // Setup custom app bar.
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText(R.string.external_database);

        final ImageButton backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(v -> finish());

        WeatherReportViewModel viewModel = new ViewModelProvider(this).get(WeatherReportViewModel.class);

        tv_connectionStatus = findViewById(R.id.connectionStatus);
        btn_retryConnection = findViewById(R.id.retryConButton);

        localDatabaseList = new ArrayList<>();

        recyclerView = findViewById(R.id.weatherReportList);
        adapter = new ExternalWeatherReportListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Begin ASync task to collect data from database.
        viewModel.getAllWeatherReports().observe(this, weatherReportModels -> {
            localDatabaseList = weatherReportModels;
            createAsyncTask();
        });
    }

    @SuppressLint("SetTextI18n")
    private void createAsyncTask() {
        asyncTask = new ExternalBaseIntegration(localDatabaseList, output -> {
            if (output == null) {
                tv_connectionStatus.setText("Failed");
                btn_retryConnection.setVisibility(View.VISIBLE);
                asyncTask.cancel(true);
            } else {
                tv_connectionStatus.setText("Data fetched from SQL database");
                try {
                    databaseOutput = (List<WeatherReportModel>) output;
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ExternalDatabaseActivity.this));
                    adapter.setWeatherReports(databaseOutput);
                } catch (Exception e) {
                    tv_databaseDisplay.setText(e.toString());
                    Log.i("external db", e.toString());
                }
            }
        });
        asyncTask.execute();
    }

    @SuppressLint("SetTextI18n")
    public void retryConnectionPressed(View view) {
        btn_retryConnection.setVisibility(View.INVISIBLE);
        asyncTask.cancel(true);
        tv_connectionStatus.setText("Attempting to reconnect...");
        createAsyncTask();
    }
}