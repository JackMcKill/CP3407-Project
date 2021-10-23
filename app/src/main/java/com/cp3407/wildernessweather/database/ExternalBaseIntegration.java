package com.cp3407.wildernessweather.database;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;

import com.cp3407.wildernessweather.APIactivity;

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

        // Once connection is made begin building output
        StringBuilder result = new StringBuilder();
        Statement st = con.createStatement();
        //Selects all from Test Table
        // #TODO needs to be changed to selecting all from different table
        ResultSet rs = st.executeQuery("select * from Weatherapp");
        ResultSetMetaData rsmd = rs.getMetaData();
        while(rs.next()){
            for (int i = 1; i < 16; i++)
                result.append(rsmd.getColumnName(i)).append(": ").append(rs.getString(i)).append("\n");
            // #TODO use new WeatherReportModel to build from external database

        }
        dataBaseOutput = (result.toString());
        return dataBaseOutput;
    }

    private Connection connectToDatabase(){
        try{
            return DriverManager.getConnection(url, user, pass);
        } catch(Exception e){
            e.printStackTrace();
            System.out.println("Could not connect to database");
            dataBaseOutput = (e.toString());
        }

        return null;
    }

    private void writeToDatabase(){
        // This function will act as a writing to database from local database data.
    }

    @Override
    protected void onPostExecute(Object result) {
        // When connection made send to activity.
        System.out.println("Finished Connecting");
        delegate.processFinish(result);
    }

    public interface AsyncResponse {
        void processFinish(Object output);
    }

    public interface ReadDatabaseCallback {
        void onSuccess(String successMessage);

        void onError(String errorMessage);
    }

}
