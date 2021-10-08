package com.cp3407.wildernessweather.database;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cp3407.wildernessweather.WeatherReportModel;

import java.util.List;

public class WeatherReportViewModel extends AndroidViewModel {

    private WeatherReportDao weatherReportDao;
    private WeatherReportDatabase weatherDB;
    private LiveData<List<WeatherReportModel>> allWeatherReports;

    public WeatherReportViewModel(Application application) {
        super(application);

        weatherDB = WeatherReportDatabase.getDatabase(application); // Application context provided by the AndroidViewModel
        weatherReportDao = weatherDB.weatherReportDao(); // Fetching the instance of the database
        allWeatherReports = weatherReportDao.getAllWeatherReports();
    }

    // Wrapper function for the insert method
    public void insert(WeatherReportModel weatherReportModel) {
        new InsertAsyncTask(weatherReportDao).execute(weatherReportModel);
    }

    // Wrapper function for returning all database items
    public LiveData<List<WeatherReportModel>> getAllWeatherReports() {
        return allWeatherReports;
    }

    // Wrapper function for returning a single weather report
    public LiveData<WeatherReportModel> getWeatherReport(long trueID) {
        return weatherReportDao.getWeatherReport(trueID);
    }

    // Wrapper function for deleting a weather report
    public void delete(WeatherReportModel weatherReportModel) {
        new DeleteAsyncTask(weatherReportDao).execute(weatherReportModel);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i("ViewModel", "ViewModel Destroyed");
    }

    // Performs insert operations on a background thread
    private class InsertAsyncTask extends AsyncTask<WeatherReportModel, Void, Void> {

        WeatherReportDao weatherReportDao;

        public InsertAsyncTask(WeatherReportDao weatherReportDao) {
            this.weatherReportDao = weatherReportDao;
        }

        @Override
        protected Void doInBackground(WeatherReportModel... weatherReportModels) {
            weatherReportDao.insert(weatherReportModels[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends AsyncTask<WeatherReportModel, Void, Void> {
        WeatherReportDao weatherReportDao;

        public DeleteAsyncTask(WeatherReportDao weatherReportDao) {
            this.weatherReportDao = weatherReportDao;
        }

        @Override
        protected Void doInBackground(WeatherReportModel... weatherReportModels) {
            weatherReportDao.delete(weatherReportModels[0]);
            return null;
        }
    }
}
