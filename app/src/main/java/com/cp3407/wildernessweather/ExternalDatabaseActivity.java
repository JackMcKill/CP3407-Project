package com.cp3407.wildernessweather;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

    private WeatherReportViewModel viewModel;
    private RecyclerView rv_reportsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_database);

        viewModel = new ViewModelProvider(this).get(WeatherReportViewModel.class);

        tv_connectionStatus = findViewById(R.id.connectionStatus);
        tv_databaseDisplay = findViewById(R.id.databaseString);
        btn_retryConnection = findViewById(R.id.retryConButton);

        localDatabaseList = new ArrayList<>();

        rv_reportsList = findViewById(R.id.weatherReportList);
        adapter = new ExternalWeatherReportListAdapter(this);
        rv_reportsList.setAdapter(adapter);
        rv_reportsList.setLayoutManager(new LinearLayoutManager(this));

        // Begin ASync task to collect data from database.
        viewModel.getAllWeatherReports().observe(this, weatherReportModels -> {
            localDatabaseList = weatherReportModels;
            createAsyncTask();
        });
    }

    private void createAsyncTask() {
        asyncTask = new ExternalBaseIntegration(localDatabaseList, new ExternalBaseIntegration.AsyncResponse() {
            @Override
            public void processFinish(Object output) {
                if (output == null) {
                    tv_connectionStatus.setText("Failed");
                    btn_retryConnection.setVisibility(View.VISIBLE);
                    asyncTask.cancel(true);
                } else {
                    tv_connectionStatus.setText("Connected. Fetching data from External DB...");
                    try {
                        databaseOutput = (List<WeatherReportModel>) output;
                        rv_reportsList.setAdapter(adapter);
                        rv_reportsList.setLayoutManager(new LinearLayoutManager(ExternalDatabaseActivity.this));
                        adapter.setWeatherReports(databaseOutput);
                    } catch (Exception e) {
                        tv_databaseDisplay.setText(e.toString());
                        Log.i("external db", e.toString());
                    }
                }
            }
        });
        asyncTask.execute();
    }

    public void retryConnectionPressed(View view) {
        btn_retryConnection.setVisibility(View.INVISIBLE);
        asyncTask.cancel(true);
        tv_connectionStatus.setText("Attempting to reconnect...");
        createAsyncTask();
    }
}