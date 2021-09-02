package com.cp3407.wildernessweather;

import android.content.Intent;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cp3407.wildernessweather.database.ConnectDatabase;

public class MainActivity extends AppCompatActivity {

    String x;
    Button apiPrototype;
    TextView tv;
    ConnectDatabase connectDatabase = new ConnectDatabase();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x = connectDatabase.connectToDB();
        tv = findViewById(R.id.connectionStatus);
        tv.setText(x);
        apiPrototype = findViewById(R.id.btn_apiPrototype);
    }

    public void apiPrototypePressed(View view) {
        Intent intent = new Intent(this, APIactivity.class);
        startActivity(intent);
    }
}