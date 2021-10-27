package com.cp3407.wildernessweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cp3407.wildernessweather.database.ExternalBaseIntegration;
import com.cp3407.wildernessweather.database.WeatherReportViewModel;

import java.sql.Connection;
import java.util.List;

public class ExternalDatabaseActivity extends AppCompatActivity implements WeatherReportListAdapter.OnDeleteClickListener {
    TextView tv_connectionStatus, tv_databaseDisplay;
    Button btn_getData, btn_retryConnection;
    ExternalBaseIntegration asyncTask;
    Connection con;
    private WeatherReportListAdapter adapter;
    List<WeatherReportModel> databaseOutput;
    ListView lv_display;

    private WeatherReportViewModel viewModel;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_database);

        tv_connectionStatus = findViewById(R.id.connectionStatus);
        tv_databaseDisplay = findViewById(R.id.databaseString);
        btn_getData = findViewById(R.id.getData);
        btn_retryConnection = findViewById(R.id.retryConButton);

        recyclerView = findViewById(R.id.weatherReportList);
        adapter = new WeatherReportListAdapter(this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Begin ASync task to collect data from database.
        createAsyncTask();
    }

    private void createAsyncTask(){
        asyncTask = new ExternalBaseIntegration(new ExternalBaseIntegration.AsyncResponse() {
            @Override
            public void processFinish(Object output) {
                if (output == null){
                    tv_connectionStatus.setText("Failed");
                    btn_retryConnection.setVisibility(View.VISIBLE);
                    asyncTask.cancel(true);
                }
                else{
                    tv_connectionStatus.setText("Connected. Fetching data from External DB...");

                    databaseOutput = (List<WeatherReportModel>) output;
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ExternalDatabaseActivity.this));
                    adapter.setWeatherReports(databaseOutput);
                }

            }

        });
        asyncTask.execute();
    }


    public void fetchDataPressed(View view) {

//        displayExternalDatabase();
    }

    public void retryConnectionPressed(View view){
        btn_retryConnection.setVisibility(View.INVISIBLE);
        asyncTask.cancel(true);
        tv_connectionStatus.setText("Attempting to reconnect...");
        createAsyncTask();

    }

    @Override
    public void OnDeleteClickListener(WeatherReportModel myModel) {
        Toast.makeText(this, "Item Deleted", Toast.LENGTH_SHORT).show();
        viewModel.delete(myModel);
    }
}