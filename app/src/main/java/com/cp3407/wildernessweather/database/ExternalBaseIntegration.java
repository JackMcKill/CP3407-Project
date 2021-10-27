package com.cp3407.wildernessweather.database;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.cp3407.wildernessweather.APIactivity;
import com.cp3407.wildernessweather.WeatherReportModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ExternalBaseIntegration extends AsyncTask<Void, Void, Object> {
    private static final String url = "jdbc:mysql://db-mysql-weatherapp.c5hi3iqblfad.ap-southeast-2.rds.amazonaws.com:3306/db_mysql_weatherapp";
    private static final String user = "admin";
    private static final String pass = "jackandharper";
    WeatherReportModel weatherReportModel;
    List<WeatherReportModel> weatherReportModelList;

    List<WeatherReportModel> dataBaseOutput;

    public AsyncResponse delegate = null; //Call back interface

    public ExternalBaseIntegration(AsyncResponse asyncResponse) {
        delegate = asyncResponse; //Assigning call back interface through constructor
    }

    @Override
    protected Object doInBackground(Void... voids) {
        try{
            // Method made to connect to the database.
            Connection con = connectToDatabase();
            if (con != null){
                // Might put the writing to external database here from local db.
                dataBaseOutput = readDatabase(con);
                return dataBaseOutput;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return e;
        }
        return null;
    }

    public List<WeatherReportModel> readDatabase(Connection con) throws SQLException {
        Log.i("external db", "Reading database");
        // Once connection is made begin building output
        StringBuilder result = new StringBuilder();
        Statement st = con.createStatement();
        //Selects all from Test Table
        ResultSet rs = st.executeQuery("select * from Weatherapp");
        ResultSetMetaData rsmd = rs.getMetaData();
        String[] string_list = new String[16];
        weatherReportModelList = new ArrayList<WeatherReportModel>();
        // While loop goes through all different entries
        while(rs.next()){
            weatherReportModel = new WeatherReportModel();
            // For loop collects 1 entry from external database
            for (int i = 0; i < 16; i++){
//                result.append(rsmd.getColumnName(i)).append(": ").append(rs.getString(i)).append("\n");
                string_list[i] = rs.getString(i+1);
            }
            weatherReportModel.setId(Long.parseLong(string_list[0]));
            weatherReportModel.setWeatherStateName(string_list[1]);
            weatherReportModel.setWeatherStateAbbr(string_list[2]);
            weatherReportModel.setWindDirectionCompass(string_list[3]);
            weatherReportModel.setCreated(string_list[4]);
            weatherReportModel.setApplicableDate(string_list[5]);
            weatherReportModel.setMinTemp(Float.parseFloat(string_list[6]));
            weatherReportModel.setMaxTemp(Float.parseFloat(string_list[7]));
            weatherReportModel.setTheTemp(Float.parseFloat(string_list[8]));
            weatherReportModel.setWindSpeed(Float.parseFloat(string_list[9]));
            weatherReportModel.setWindDirectionCompass(string_list[10]);
            weatherReportModel.setAirPressure(Integer.parseInt(string_list[11]));
            weatherReportModel.setHumidity(Integer.parseInt(string_list[12]));
            weatherReportModel.setVisibility(Integer.parseInt(string_list[13]));
            weatherReportModel.setPredictability(Integer.parseInt(string_list[14]));
            weatherReportModel.setTrueID(Long.parseLong(string_list[15]));
//            Log.i("external db", weatherReportModel.toString());
            Log.i("external db", weatherReportModelList.toString());
            weatherReportModelList.add(weatherReportModel);
            Log.i("external db", weatherReportModelList.toString());
        }
        result.append("blah");
        dataBaseOutput = weatherReportModelList;
        Log.i("external db", "Finished reading database");
        return dataBaseOutput;
    }

    private Connection connectToDatabase(){
        Log.i("external db", "Trying to connect");
        try{
            return DriverManager.getConnection(url, user, pass);
        } catch(Exception e){
            e.printStackTrace();
            Log.i("external db", "Could not connect to database");
        }

        return null;
    }

    private void writeToDatabase(Connection con) throws SQLException {
        // This function will act as a writing to database from local database data.
        Log.i("external db", "writing to external db");
        Statement st = con.createStatement();
        // #TODO for loop here for all different things to insert

        // These will change every time in the for loop to insert data
        String column_name = ""; // #TODO get column name from internal database
        String weather_report_model_data = ""; // #TODO get data from internal database
        String query = "INSERT INTO Weatherapp (" + column_name + ") VALUES('" + weather_report_model_data + "')";
        st.executeQuery(query);
        // end loop
        // #TODO should somehow call internal database to delete data after this is done?
        Log.i("external db", "Finished writing to database");
    }

    @Override
    protected void onPostExecute(Object result) {
        // When connection made send to activity.

        Log.i("external db", "Finished everything");
        delegate.processFinish(result);
    }

    public interface AsyncResponse {
        void processFinish(Object output);
    }

}
