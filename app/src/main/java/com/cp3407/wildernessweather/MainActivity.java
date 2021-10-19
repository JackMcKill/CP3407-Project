package com.cp3407.wildernessweather;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class MainActivity extends AppCompatActivity {

    private SharedPreferences favourites;

    EditText searchBox;
    ListView locationList;
    TextView homeTitle, currentDate, locationName, editFavs;
    ImageButton settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        favourites = getSharedPreferences("favourites", Context.MODE_PRIVATE);

        homeTitle = findViewById(R.id.homeTitle);
        currentDate = findViewById(R.id.currentDate);
        locationName = findViewById(R.id.locationName);
        editFavs = findViewById(R.id.editFav);
        searchBox = findViewById(R.id.searchBox);
        settingsButton = findViewById(R.id.settingsButton);

        locationList = findViewById(R.id.locationList);

        initialiseList();
    }

    public void initialiseList() {

        /*
         * This method handles fetching the list of favourite locations from our SharedPreferences instance.
         * It then generates an array of WeatherReportModels for the favourite locations and displays
         * them in a simple list adapter.
         *
         * Because we have not yet implemented a way for users to add locations to their favourites list,
         * we can generate some dummy information in the meantime (dummySetSharedPreferences()).
         *
         * @Caroline once you have finished implementing the custom ArrayAdapter, we can replace the
         * current ArrayAdapter in this method with the new one, and pass in the array of WeatherReportModels (favLocationReports)
         * instead of the array of Strings (cityNames)
         */

        dummySetSharedPreferences(); // sets some favourite locations into SharedPreferences (delete this once locations can be added to sharedPrefs by the user
        String[] cityNames = getFavCityNames(); // gets favourite locations from SharedPreferences and puts them into an array
        WeatherReportModel[] favLocationReports = getWeatherReports(cityNames); // generates an array of weather reports using the array of city names

        ArrayAdapter<String> simpleArrayAdapterDEMO = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, cityNames);
        locationList.setAdapter(simpleArrayAdapterDEMO);
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

    // Generates a list of WeatherReports
    public WeatherReportModel[] getWeatherReports(String[] names) {
        WeatherReportModel[] reports = new WeatherReportModel[names.length];
        int i = 0;
        for (String location : names) {
            // initialise a new WeatherReportModel
            WeatherReportModel report = new WeatherReportModel();

            // make an API call to get the data
            report.setCityName(location); // replace this line with API call

            // add to the array
            reports[i] = report;
            i++;
        }
        return reports;
    }
}