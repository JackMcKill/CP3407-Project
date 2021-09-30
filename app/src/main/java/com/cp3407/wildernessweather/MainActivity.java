package com.cp3407.wildernessweather;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;



import com.cp3407.wildernessweather.settings.SettingsActivity;


public class MainActivity extends AppCompatActivity {

    Button apiPrototype;
    Button dbPrototype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiPrototype = findViewById(R.id.btn_apiPrototype);
        dbPrototype = findViewById(R.id.dbPrototype);
        dbPrototype = findViewById(R.id.externalDBPrototype);

    }

    public void apiPrototypePressed(View view) {
        Intent intent = new Intent(this, APIactivity.class);
        startActivity(intent);
    }

    public void btn_dbPrototypeClicked(View view) {
        Intent intent = new Intent(this, DbActivity.class);
        startActivity(intent);
    }
    public void btn_externalDBPrototypeClicked(View view) {
        Intent intent = new Intent(this, ExternalDatabaseActivity.class);
        startActivity(intent);
    }

    public void settingsPressed(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}