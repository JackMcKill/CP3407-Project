package com.cp3407.wildernessweather;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cp3407.wildernessweather.database.ConnectToDatabase;

public class MainActivity extends AppCompatActivity {


    Button apiPrototype;
    TextView databaseTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseTextView = findViewById(R.id.connectionStatus);

        // Begin ASync task to collect data from database.
        ConnectToDatabase asyncTask = new ConnectToDatabase(new ConnectToDatabase.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                databaseTextView.setText( output);
            }
        });
        asyncTask.execute();

        //API Button
        apiPrototype = findViewById(R.id.btn_apiPrototype);
    }

    public void apiPrototypePressed(View view) {
        Intent intent = new Intent(this, APIactivity.class);
        startActivity(intent);
    }
}