package com.cp3407.wildernessweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.cp3407.wildernessweather.database.ConnectToDatabase;

public class ExternalDatabaseActivity extends AppCompatActivity {
    TextView databaseTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_database);

        databaseTextView = findViewById(R.id.connectionStatus);


        // Begin ASync task to collect data from database.
        ConnectToDatabase asyncTask = new ConnectToDatabase(new ConnectToDatabase.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                databaseTextView.setText( output);
            }
        });
        asyncTask.execute();
    }
}