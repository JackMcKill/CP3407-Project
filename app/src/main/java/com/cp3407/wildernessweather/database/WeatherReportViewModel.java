package com.cp3407.wildernessweather.database;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;

import com.cp3407.wildernessweather.WeatherReportModel;

public class WeatherReportViewModel extends AndroidViewModel {

    private WeatherReportDao weatherReportDao;
    private WeatherReportDatabase weatherDB;

    public WeatherReportViewModel(Application application) {
        super(application);

        weatherDB = WeatherReportDatabase.getDatabase(application); // Application context provided by the AndroidViewModel
        weatherReportDao = weatherDB.weatherReportDao(); // Fetching the instance of the database
    }

    // Wrapper method for the insert method
    public void insert(WeatherReportModel weatherReportModel) {
        new InsertAsyncTask(weatherReportDao).execute(weatherReportModel);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i("ViewModel", "ViewModel Destroyed");
    }

    // Performs insert operations on a background thread
    private class InsertAsyncTask extends AsyncTask<WeatherReportModel, Void, Void> {

        WeatherReportDao mWeatherReportDao;

        public InsertAsyncTask(WeatherReportDao mWeatherReportDao) {
            this.mWeatherReportDao = mWeatherReportDao;
        }

        @Override
        protected Void doInBackground(WeatherReportModel... weatherReportModels) {
            mWeatherReportDao.insert(weatherReportModels[0]);
            return null;
        }
    }

}
