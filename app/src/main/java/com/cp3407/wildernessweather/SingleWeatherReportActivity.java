package com.cp3407.wildernessweather;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.cp3407.wildernessweather.database.WeatherReportViewModel;

import org.parceler.Parcels;

public class SingleWeatherReportActivity extends AppCompatActivity {
    WeatherReportModel singleWeatherReport;
    WeatherReportViewModel viewModel;
    boolean isNewEntry;

    TextView cityNameView, stateView, applicableDateView, minTempView,
            maxTempView, tempView, windSpeedView, windDirectionView,
            airPressureView, humidityView, visibilityView, predictabilityView;

    ImageView stateImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_weather_report);

        cityNameView = findViewById(R.id.tv_cityName);
        stateView = findViewById(R.id.tv_weatherStateName);
        stateImage = findViewById(R.id.iv_stateImage);
        applicableDateView = findViewById(R.id.tv_applicableDate);
        minTempView = findViewById(R.id.tv_minTemp);
        maxTempView = findViewById(R.id.tv_maxTemp);
        tempView = findViewById(R.id.tv_weatherTemp);
        windSpeedView = findViewById(R.id.tv_windSpeed);
        windDirectionView = findViewById(R.id.tv_windDirection);
        airPressureView = findViewById(R.id.tv_airPressure);
        humidityView = findViewById(R.id.tv_humidity);
        visibilityView = findViewById(R.id.tv_visibility);
        predictabilityView = findViewById(R.id.tv_predictability);

        viewModel = new ViewModelProvider(this).get(WeatherReportViewModel.class);

        singleWeatherReport = Parcels.unwrap(getIntent().getParcelableExtra("report"));
        populateFields();

        final ImageButton backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goBack();
            }
        });

        final ImageButton downloadButton = findViewById(R.id.btn_download);
        downloadButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                try {
                    downloadData();
//                    shareCSV();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
//        viewModel.insert(singleWeatherReport);
        updateDatabase();
    }

    /*
     * Checks whether the database already contains an entry for the weather report being viewed
     * If the database does not contain an entry for this weather report, a new one is added
     * If the database already contains an entry, then nothing is added
     * */
    private void updateDatabase() {
        Log.i("newEntry", "current trueID: " + singleWeatherReport.getTrueID());
        viewModel.getAllWeatherReports().observe(this, weatherReportModels -> {

            if (weatherReportModels.size() == 0) { // this checks if the database is empty
                isNewEntry = true;
            } else {
                for (WeatherReportModel model : weatherReportModels) {
                    Log.i("newEntry", "trueID: " + model.getTrueID());
                    if (model.getTrueID() != singleWeatherReport.getTrueID()) {
                        isNewEntry = true;
                    } else {
                        isNewEntry = false;
                        Log.i("newEntry", "result set to false");
                        break;
                    }
                }
            }

            if (isNewEntry) {
                Log.i("newEntry", "result set to true, so this should be added to the database");
                viewModel.insert(singleWeatherReport);

            } else {
                Log.i("newEntry", "result set to false, so this should NOT be added to the database");
            }
        });
    }

    public void populateFields() {

        cityNameView.setText(String.valueOf(singleWeatherReport.getCityName()));
        stateView.setText(singleWeatherReport.getWeatherStateName());
        // Set weather state image field to correct resource.
        stateImage.setImageResource(getStateImageResId(singleWeatherReport.getWeatherStateAbbr()));
        // Convert date string before inserting.
        applicableDateView.setText(String.valueOf(convertDateString(singleWeatherReport.getApplicableDate())));
        minTempView.setText(" " + Math.round(singleWeatherReport.getMinTemp()) + "°");
        maxTempView.setText(" " + Math.round(singleWeatherReport.getMaxTemp()) + "°");
        tempView.setText(" " + Math.round(singleWeatherReport.getTheTemp()) + "°");
        windSpeedView.setText((Math.round(singleWeatherReport.getWindSpeed() * 100.0) / 100.0) + " Km/h");
        windDirectionView.setText(singleWeatherReport.getWindDirectionCompass() + " (" + Math.round(singleWeatherReport.getWindDirection()) + "°)");
        airPressureView.setText(singleWeatherReport.getAirPressure() + " in");
        humidityView.setText(singleWeatherReport.getHumidity() + "%");
        visibilityView.setText((Math.round(singleWeatherReport.getVisibility() * 100.0) / 100.0) + "mi");
        predictabilityView.setText(singleWeatherReport.getPredictability() + "%");
    }

    /**
     * Returns the resource id of the image that corresponds to a weather state abbreviation.
     * For example:
     * "lc" -> R.drawable.light_cloud
     * "hr" -> R.drawable.heavy_rain
     *
     * @param abbreviation weather state abbreviation to get image for.
     * @return resource id of the corresponding image.
     */
    public int getStateImageResId(String abbreviation) {
        switch (abbreviation) {
            case "c":
                return R.drawable.clear;
            case "lc":
                return R.drawable.light_cloud;
            default:
                return R.drawable.clear;
        }
    }

    /**
     * Converts date string in format YYYY-MM-DD to the format DD/MM/YYYY.
     *
     * @param dateString date string in old format.
     * @return date string in new format.
     */
    public String convertDateString(String dateString) {
        String[] parts = dateString.split("-");
        return parts[2] + "/" + parts[1] + "/" + parts[0];
    }

    /**
     * Returns to APIActivity.
     */
    public void goBack() {

    }

    /**
     * Downloads weather data to the local database.
     */
    public void downloadData() throws Exception {
        ExportCSV.writeWithCsvListWriter(singleWeatherReport);
        Toast.makeText(this, "CSV created", Toast.LENGTH_SHORT).show();

        String fileName = singleWeatherReport.getCityName() + "-" + singleWeatherReport.getApplicableDate() + ".csv";
        Log.i("export", "String: " + fileName);

        // wait for 2 seconds while file is created
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                shareCSV(fileName);
            }
        }, 2000);
    }

    // Opens a share sheet to allow sharing of the CSV file
    public void shareCSV(String fileName) {
        // Generate the path to the CSV file
        final String FILEPATH = "data/data/com.cp3407.wildernessweather/";

        // Create a URI to identify and gain access to the CSV file
        Uri uri = Uri.parse("content://" + FILEPATH + fileName);
        Log.i("export", "uri: " + uri.toString());

        // Create an Intent to open a share sheet
        Intent sendIntent = new Intent(android.content.Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_STREAM, uri);

        startActivity(Intent.createChooser(sendIntent, "Send Mail"));
    }
}


