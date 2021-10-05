package com.cp3407.wildernessweather;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.cp3407.wildernessweather.settings.SettingsActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    EditText searchBox;
    ListView locationList;
    TextView homeTitle, currentDate, locationName, editFavs;
    ImageButton settingsButton;

    String City[] = {"Brisbane", "Townsville"};
    String minTemp[] = {"17", "25"};
    String maxTemp[] = {"29", "30"};
    int icons[] = {R.drawable.ic_c, R.drawable.ic_c};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeTitle = findViewById(R.id.homeTitle);
        currentDate = findViewById(R.id.currentDate);
        locationName = findViewById(R.id.locationName);
        editFavs = findViewById(R.id.editFav);
        searchBox = findViewById(R.id.searchBox);
        settingsButton = findViewById(R.id.settingsButton);

        ListView locationList = findViewById(R.id.locationList);

        ArrayAdapter




    }





}