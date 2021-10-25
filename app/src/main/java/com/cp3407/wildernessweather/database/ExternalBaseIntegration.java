package com.cp3407.wildernessweather.database;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.cp3407.wildernessweather.APIactivity;
import com.cp3407.wildernessweather.WeatherReportModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class ExternalBaseIntegration extends AsyncTask<Void, Void, Object> {
    private static final String url = "jdbc:mysql://db-mysql-weatherapp.c5hi3iqblfad.ap-southeast-2.rds.amazonaws.com:3306/db_mysql_weatherapp";
    private static final String user = "admin";
    private static final String pass = "jackandharper";

    String dataBaseOutput;

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

    public String readDatabase(Connection con) throws SQLException {
        Log.i("external db", "Reading database");
        // Once connection is made begin building output
        StringBuilder result = new StringBuilder();
        Statement st = con.createStatement();
        //Selects all from Test Table
        ResultSet rs = st.executeQuery("select * from Weatherapp");
        ResultSetMetaData rsmd = rs.getMetaData();
        // While loop goes through all different entries
        while(rs.next()){
            int i = 1;
            // For loop collects 1 entry from external database
            for (i = 1; i < 16; i++)
                result.append(rsmd.getColumnName(i)).append(": ").append(rs.getString(i)).append("\n");
            // #TODO use new WeatherReportModel to build from external database
            // #TODO maybe use recycler view to print multiple thingys
        }
        dataBaseOutput = (result.toString());
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
            dataBaseOutput = (e.toString());
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
        ResultSet rs = st.executeQuery(query);

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
