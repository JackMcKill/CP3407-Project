package com.cp3407.wildernessweather;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    EditText searchBox;
    ListView locationList;
    TextView homeTitle, currentDate, locationName, editFavs;
    ImageButton settingsButton;

    String[] City = {"Brisbane", "Townsville"};
    String[] minTemp = {"17", "25"};
    String[] maxTemp = {"29", "30"};
    int[] icons = {R.drawable.ic_c, R.drawable.ic_c};

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

        WeatherReportModel[] reports = demoCreateWeatherReports();

        // TODO delete everything between the ==== comments once you have had a look, and re-implement it correctly
// =================================================================================================

        /*
        Below is a super basic example of how to use an ArrayAdapter to populate a ListView with
        items from an array.

        First we declare a new ArrayAdapter object, and tell it what data type it is going to use
        (in our case that's <String>).

        Then we pass in the required arguments to its constructor:
            - context: MainActivity.this
            - layoutResource (what layout file do we want to act as each list item): android.R.layout.simple_list_item_1 (this is a default layout provided by Android, it's very simple)
            - data (the actual data to be displayed, as a List or Array): City

        Once we have created our new ArrayAdapter object we can use the setAdapter function to
        attach it to our ListView using the .setAdapter method.

        If you run this code you can see that it creates a super simple list, using the items inside
        the City array to populate the text in the list view.

        However, because we want slightly more complicated list items (as in they display more than
        just a single textView, we need to create out own custom ArrayAdapter that can handle our
        custom views.
         */

        ArrayAdapter<String> simpleArrayAdapterDEMO = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, City);
        locationList.setAdapter(simpleArrayAdapterDEMO);


//        CustomArrayAdapter customArrayAdapterDEMO = new CustomArrayAdapter(MainActivity.this, R.layout.location_row, reports);
//        locationList.setAdapter(customArrayAdapterDEMO);
    }



    /*
    Below I have implemented a CustomArrayAdapter, which extends/inherits from the standard
    ArrayAdapter class which we used above. The correct thing to do is to actually place all of the
    following code into a brand new Java file, but for the sake of showing you the basics I've just
    created it as a nested class.
     */

    private static class CustomArrayAdapter extends ArrayAdapter<WeatherReportModel> {

        private final Context context;
        private final int resource;

        public CustomArrayAdapter(@NonNull Context context, int resource, @NonNull WeatherReportModel[] objects) {
            super(context, resource, objects);
            this.context = context;
            this.resource = resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            String cityName = getItem(position).getCityName();
            float maxTemp = getItem(position).getMaxTemp();
            float minTemp = getItem(position).getMinTemp();


            // Create a new weatherReportModel
            WeatherReportModel weatherReportModel = new WeatherReportModel();
            weatherReportModel.setCityName(cityName);
            weatherReportModel.setMaxTemp(maxTemp);
            weatherReportModel.setMinTemp(minTemp);

            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, parent, false);

            TextView locationName = convertView.findViewById(R.id.locationName);
            TextView locationMinTemp = convertView.findViewById(R.id.hs_lowTemp);
            TextView locationMaxTemp = convertView.findViewById(R.id.hs_highTemp);

            locationName.setText(cityName);
            locationMaxTemp.setText(String.valueOf(maxTemp));
            locationMinTemp.setText(String.valueOf(minTemp));

            return convertView;
        }

    }

// =================================================================================================


    // This method just creates an array of WeatherReports
    public WeatherReportModel[] demoCreateWeatherReports() {
        WeatherReportModel report1 = new WeatherReportModel();
        WeatherReportModel report2 = new WeatherReportModel();

        report1.setCityName("Townsville");
        report1.setMaxTemp(30);
        report1.setMinTemp(25);

        report2.setCityName("Brisbane");
        report2.setMaxTemp(29);
        report2.setMinTemp(17);

        return new WeatherReportModel[]{report1, report2};
    }
}