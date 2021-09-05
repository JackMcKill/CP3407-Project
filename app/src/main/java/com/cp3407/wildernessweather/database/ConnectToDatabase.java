package com.cp3407.wildernessweather.database;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class ConnectToDatabase extends AsyncTask<Void, Void, String> {
    private static final String url = "jdbc:mysql://db-mysql-weatherapp.c5hi3iqblfad.ap-southeast-2.rds.amazonaws.com:3306/db_mysql_weatherapp";
    private static final String user = "admin";
    private static final String pass = "jackandharper";

    String dataBaseOutput;

    public AsyncResponse delegate = null; //Call back interface

    public ConnectToDatabase(AsyncResponse asyncResponse) {
        delegate = asyncResponse;//Assigning call back interface through constructor
    }

    @Override
    protected String doInBackground(Void... voids) {
        try{
            Connection con = DriverManager.getConnection(url, user, pass);
            StringBuilder result = new StringBuilder("Database connected \n");
            Statement st = con.createStatement();
            //Selects all from Test Table
            ResultSet rs = st.executeQuery("select * from TestTable");
            ResultSetMetaData rsmd = rs.getMetaData();
            while(rs.next()){
                // This is will be changed to a new function to get all data from table (also setting differnt strings to display)
                result.append(rsmd.getColumnName(1)).append(": ").append(rs.getInt(1)).append("\n");
                result.append(rsmd.getColumnName(2)).append(": ").append(rs.getString(2)).append("\n");
            }
            dataBaseOutput = (result.toString());
        }
        catch (Exception e){
            e.printStackTrace();
            dataBaseOutput = (e.toString());
        }
        return dataBaseOutput;
    }

    @Override
    protected void onPostExecute(String result) {
        // When data is finished gathering, send to Activity.
        System.out.println("Finished gathering Data");
        delegate.processFinish(result);
    }

    public interface AsyncResponse {
        void processFinish(String output);
    }
}
