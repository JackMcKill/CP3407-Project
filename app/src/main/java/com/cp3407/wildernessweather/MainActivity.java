package com.cp3407.wildernessweather;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button apiPrototype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiPrototype = findViewById(R.id.btn_apiPrototype);
    }

    public void apiPrototypePressed(View view) {
        Intent intent = new Intent(this, APIactivity.class);
        startActivity(intent);
    }
}