package com.cp3407.wildernessweather;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cp3407.wildernessweather.database.ConnectToDatabase;
import com.cp3407.wildernessweather.database.WeatherReportViewModel;

import java.util.List;

public class DbActivity extends AppCompatActivity implements WeatherReportListAdapter.OnDeleteClickListener {

    private RecyclerView recyclerView;
    private WeatherReportViewModel viewModel;
    private WeatherReportListAdapter adapter;
    TextView databaseTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);

        // Setup custom app bar.
        TextView titleView = findViewById(R.id.tv_title);
        titleView.setText(String.valueOf("DBActivity"));

        final ImageButton backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.rv_databaseList);
        adapter = new WeatherReportListAdapter(this, this);
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

    @Override
    public void OnDeleteClickListener(WeatherReportModel myModel) {
        Toast.makeText(this, "Item Deleted", Toast.LENGTH_SHORT).show();
        viewModel.delete(myModel);
    }
}