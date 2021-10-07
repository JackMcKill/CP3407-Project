package com.cp3407.wildernessweather.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.cp3407.wildernessweather.WeatherReportModel;

import java.util.List;

@Dao
public interface WeatherReportDao {
    @Insert
    void insert(WeatherReportModel weatherReportModel);

    /*  The @Query annotation highlights as an error, but the code compiles fine. I think it's an
    issue with Android Studio not knowing what to do with the @Query annotation     */

    @Query("SELECT * FROM weather")
    LiveData<List<WeatherReportModel>> getAllWeatherReports();

    @Query("SELECT * FROM weather WHERE id=:id")
    LiveData<WeatherReportModel> getWeatherReport(long id);

    @Delete
    int delete(WeatherReportModel weatherReportModel);

    // TODO some form of update method
}
