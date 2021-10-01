package com.cp3407.wildernessweather.database;

import android.os.AsyncTask;

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
            // #TODO Buttons blocked until connection made.
            // Method made to connect to the database.
            Connection con = connectToDatabase();
            if (con != null){
                // #TODO unblock buttons when connection made.

                // #TODO if buttonPressed or onConditionMet then read database.

                // Once connection is made read data from tables
                return con;

                // #TODO if buttonPressed or onConditionMet then write to database
                // writeToDatabase();
            }
            // #TODO add retry to connect to database button if connection failed
        }
        catch (Exception e){
            e.printStackTrace();
            return e;
        }
        return dataBaseOutput;
    }

    public String readDatabase(Connection con) throws SQLException {
        // Once connection is made begin building output
        StringBuilder result = new StringBuilder();
        Statement st = con.createStatement();
        //Selects all from Test Table
        // #TODO needs to be changed to selecting all from different table
        ResultSet rs = st.executeQuery("select * from TestTable");
        ResultSetMetaData rsmd = rs.getMetaData();
        while(rs.next()){
            // #TODO Add for loop to count getColumnName(i) to set amount
            result.append(rsmd.getColumnName(1)).append(": ").append(rs.getInt(1)).append("\n");
            // #TODO use new WeatherReportModel to build from external database
            result.append(rsmd.getColumnName(2)).append(": ").append(rs.getString(2)).append("\n");
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
        // When data is finished gathering, send to Activity.
        System.out.println("Finished gathering Data");
        delegate.processFinish(result);
    }

    public interface AsyncResponse {
        void processFinish(Object output);
    }

}
