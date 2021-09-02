package com.cp3407.wildernessweather.database;

import android.annotation.SuppressLint;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cp3407.wildernessweather.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class ConnectDatabase {
    private static final String url = "db-mysql-weatherapp.c5hi3iqblfad.ap-southeast-2.rds.amazonaws.com";
    private static final String user = "admin";
    private static final String pass = "jackandharper";

    String tv;

    @SuppressLint("SetTextI18n")
    public String connectToDB(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, pass);
            StringBuilder result = new StringBuilder("Database connected \n");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from TestTable");
            ResultSetMetaData rsmd = rs.getMetaData();
            while(rs.next()){
                // This is will be changed to a loop to get all data from table (also setting differnt strings to display)
                result.append(rsmd.getColumnName(1)).append(": ").append(rs.getInt(1)).append("\n");
                result.append(rsmd.getColumnName(2)).append(": ").append(rs.getString(2)).append("\n");
            }
            tv = (result.toString());
            return tv;
        }
        catch (Exception e){
            e.printStackTrace();
            tv = (e.toString());
            return tv;
        }

    }

}
