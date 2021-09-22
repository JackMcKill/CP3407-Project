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
    Button dbPrototype;
    TextView someTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiPrototype = findViewById(R.id.btn_apiPrototype);
        dbPrototype = findViewById(R.id.dbPrototype);
        someTextView = findViewById(R.id.epicText);

        someTextView.setText("Some different text");


    }

    public void apiPrototypePressed(View view) {
        Intent intent = new Intent(this, APIactivity.class);
        startActivity(intent);
    }

    public void btn_dbPrototypeClicked(View view) {
        Intent intent = new Intent(this, DbActivity.class);
        startActivity(intent);
    }

    public void someEpicMethod(View view) {
        someTextView.setText("Text has been changed");
    }
}