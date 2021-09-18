package com.cp3407.wildernessweather.database;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class WeatherReportViewModel extends AndroidViewModel {

    private WeatherReportDao weatherReportDao;
    private WeatherReportDatabase weatherDB;

    public WeatherReportViewModel(Application application) {
        super(application);

        weatherDB = WeatherReportDatabase.getDatabase(application); // Application context provided by the AndroidViewModel
        weatherReportDao = weatherDB.weatherReportDao(); // Fetching the instance of the database
    }

    // Wrapper method for the insert method
    public void insert(WeatherReport weatherReport) {
        new InsertAsyncTask(weatherReportDao).execute(weatherReport);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i("ViewModel", "ViewModel Destroyed");
    }

    // Performs insert operations on a background thread
    private class InsertAsyncTask extends AsyncTask<WeatherReport, Void, Void> {

        WeatherReportDao mWeatherReportDao;

        public InsertAsyncTask(WeatherReportDao mWeatherReportDao){
            this.mWeatherReportDao = mWeatherReportDao;
        }

        @Override
        protected Void doInBackground(WeatherReport... weatherReports) {
            mWeatherReportDao.insert(weatherReports[0]);
            return null;
        }
    }

}
