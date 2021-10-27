package com.cp3407.wildernessweather;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;


public class MainActivity extends AppCompatActivity {

    private SharedPreferences favourites;
    private SharedPreferences settingsData;

    private WeatherDataService weatherDataService;

    SearchView searchBox;
    ListView locationList;
    TextView currentDate, locationName;
    ImageButton settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        favourites = getSharedPreferences("favourites", Context.MODE_PRIVATE);
        settingsData = getSharedPreferences("settings", Context.MODE_PRIVATE);

        weatherDataService = new WeatherDataService(MainActivity.this);

        currentDate = findViewById(R.id.tv_currentDate);
        locationName = findViewById(R.id.locationName);
        searchBox = findViewById(R.id.sv_search);
        settingsButton = findViewById(R.id.btn_settingsButton);
        locationList = findViewById(R.id.lv_locationList);

        currentDate.setText(getDate());
        searchBox.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String searchText) {
                Log.i("main", "String: " + searchText);

                Intent intent = new Intent(MainActivity.this, APIactivity.class);
                intent.putExtra("cityName", searchText);
                startActivity(intent);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        initialiseList();
    }

    private String getDate() {
        Calendar calendar = Calendar.getInstance();

        String dayName = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String daySuffix = getSuffix(day);
        String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        int year = calendar.get(Calendar.YEAR);

        return dayName + " " + day + daySuffix + " " + month + ", " + year;
    }

    public String getSuffix(int day) {
        switch (day) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }


    public void initialiseList() {

        dummySetSharedPreferences();
        String[] cityNames = getFavCityNames();
        Log.i("main", "about to get weather reports");
        weatherDataService.getFavourites(cityNames, weatherReportModels -> {
            WeatherReportModelListAdapter adapter = new WeatherReportModelListAdapter(MainActivity.this, R.layout.weather_report_list_item, weatherReportModels);
            locationList.setAdapter(adapter);
        });
    }

    public void dummySetSharedPreferences() {
        String[] testCityNames = {"Rome", "Paris", "New York"};

        Set<String> citiesAsSet = convertArrayToSet(testCityNames);
        SharedPreferences.Editor editor = favourites.edit();
        editor.putStringSet("locations", citiesAsSet);

        editor.apply();
    }

    public String[] getFavCityNames() {
        Set<String> cityNamesAsSet = favourites.getStringSet("locations", new HashSet<>());
        return convertSetToArray(cityNamesAsSet);
    }

    public <T> Set<T> convertArrayToSet(T[] array) {
        return new HashSet<>(Arrays.asList(array));
    }

    public String[] convertSetToArray(Set<String> setOfString) {
        return setOfString.toArray(new String[0]);
    }
}
