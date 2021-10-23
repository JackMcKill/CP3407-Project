package com.cp3407.wildernessweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cp3407.wildernessweather.database.ExternalBaseIntegration;

import java.sql.Connection;
import java.sql.SQLException;

public class ExternalDatabaseActivity extends AppCompatActivity {
    TextView tv_connectionStatus, tv_databaseDisplay;
    Button btn_getData, btn_retryConnection;
    ExternalBaseIntegration asyncTask;
    Connection con;
    String databaseString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_database);

        tv_connectionStatus = findViewById(R.id.connectionStatus);
        tv_databaseDisplay = findViewById(R.id.databaseString);
        btn_getData = findViewById(R.id.getData);
        btn_retryConnection = findViewById(R.id.retryConButton);


        // Begin ASync task to collect data from database.
        createAsyncTask();
    }

    private void createAsyncTask(){
        asyncTask = new ExternalBaseIntegration(new ExternalBaseIntegration.AsyncResponse() {
            @Override
            public void processFinish(Object output) {
                if (output == null){
                    tv_connectionStatus.setText("Failed");
                    btn_retryConnection.setVisibility(View.VISIBLE);
                    asyncTask.cancel(true);
                }
                else{
                    tv_connectionStatus.setText("Connected");
                    databaseString = (String) output;
                    tv_databaseDisplay.setText(databaseString);
                }

            }

        });
        asyncTask.execute();
    }

    private void displayExternalDatabase(){
//        try {
//            btn_getData.setEnabled(false);
//
//            databaseString = asyncTask.readDatabase(con, new ExternalBaseIntegration.ReadDatabaseCallback() {
//                @Override
//                public void onSuccess(String successMessage) {
//                    System.out.println(databaseString + " x");
//                    tv_databaseDisplay.setText(databaseString);
//                }
//
//                @Override
//                public void onError(String errorMessage) {
//                    System.out.println("Epic fail");
//                }
//            });
//            System.out.println("poop");
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
    }

    public void fetchDataPressed(View view) {
        tv_connectionStatus.setText("Fetching data from External DB...");
//        displayExternalDatabase();
    }

    public void retryConnectionPressed(View view){
        btn_retryConnection.setVisibility(View.INVISIBLE);
        asyncTask.cancel(true);
        tv_connectionStatus.setText("Attempting to reconnect...");
        createAsyncTask();

    }
}