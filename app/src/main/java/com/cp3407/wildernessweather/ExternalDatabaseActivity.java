package com.cp3407.wildernessweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.cp3407.wildernessweather.database.ExternalBaseIntegration;

public class ExternalDatabaseActivity extends AppCompatActivity {
    TextView databaseTextView;
    Button getDataButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_database);

        databaseTextView = findViewById(R.id.connectionStatus);
        getDataButton = findViewById(R.id.getData);


        // Begin ASync task to collect data from database.
        ExternalBaseIntegration asyncTask = new ExternalBaseIntegration(new ExternalBaseIntegration.AsyncResponse() {
            @Override
            public void processFinish(Object output) {
            }
        });
        asyncTask.execute();
    }
}