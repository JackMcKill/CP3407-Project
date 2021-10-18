package com.cp3407.wildernessweather;

import android.content.Intent;
import android.util.Log;

import org.supercsv.io.CsvListWriter;
import org.supercsv.io.ICsvListWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;

public class ExportCSV {
    /*
     http://super-csv.github.io/super-csv/examples_writing.html
     This is what is being used for this. Needs more research (Specifically CsvListWriter {Maybe}).
    */



    public static void writeWithCsvListWriter(WeatherReportModel weatherReportModel) throws Exception {
        List<Object> data = Arrays.asList(weatherReportModel);
        ICsvListWriter listWriter = null;
        try {
            Log.i("export", "Start");
            String path = "/data/data/com.cp3407.wildernessweather/" + weatherReportModel.getCityName() + "-" + weatherReportModel.getApplicableDate() + ".csv";

            listWriter = new CsvListWriter(new FileWriter(path),
                    CsvPreference.STANDARD_PREFERENCE);


            final String[] header = new String[]{"id", "weather_state_name", "weather_state_abbr", "wind_direction_compass",
                    "created", "applicable_date", "min_temp", "max_temp", "the_temp", "wind_speed", "wind_direction",
                    "air_pressure", "humidity", "visibility", "predictability"};

            // write the header
            listWriter.writeHeader(header);

            // write the customer lists
            listWriter.write(data);

        } finally {
            if (listWriter != null) {
                listWriter.close();
                Log.i("export", "Exported");
            }
        }
    }


}
