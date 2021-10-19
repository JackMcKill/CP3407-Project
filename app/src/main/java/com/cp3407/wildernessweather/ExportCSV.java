package com.cp3407.wildernessweather;

import android.util.Log;

import org.supercsv.io.CsvListWriter;
import org.supercsv.io.ICsvListWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ExportCSV {
    /*
     http://super-csv.github.io/super-csv/examples_writing.html
     This is what is being used for this. Needs more research (Specifically CsvListWriter {Maybe}).
    */

    // Callback method that is passed when calling the writeWithCsvListWriter method
    public interface ExportCSVCallback {
        void onSuccess(String successMessage);

        void onError(String errorMessage);
    }

    public static void writeWithCsvListWriter(WeatherReportModel weatherReportModel, ExportCSVCallback exportCSVCallback) throws Exception {
        List<Object> data = Arrays.asList(weatherReportModel);
        ICsvListWriter listWriter = null;
        try {
            Log.i("export", "Start");
            String path = "/data/data/com.cp3407.wildernessweather/" + weatherReportModel.getCityName() + "-" + weatherReportModel.getApplicableDate() + ".csv";

            listWriter = new CsvListWriter(new FileWriter(path),
                    CsvPreference.STANDARD_PREFERENCE);


            final String[] header = new String[]{"trueID", "weather_state_name", "weather_state_abbr", "wind_direction_compass",
                    "created", "applicable_date", "min_temp", "max_temp", "the_temp", "wind_speed", "wind_direction",
                    "air_pressure", "humidity", "visibility", "predictability"};

            // write the header
            listWriter.writeHeader(header);

            // write the customer lists
            listWriter.write(data);

            exportCSVCallback.onSuccess("CSV created");

        } catch (Exception e) {
            exportCSVCallback.onError("Error creating CSV");
        } finally {
            if (listWriter != null) {
                listWriter.close();
                Log.i("export", "Exported");
            }
        }
    }
}
