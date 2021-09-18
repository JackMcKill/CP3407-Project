package com.cp3407.wildernessweather.database;

import androidx.room.Dao;
import androidx.room.Insert;

import com.cp3407.wildernessweather.WeatherReportModel;

@Dao
public interface WeatherReportDao {
    @Insert
    void insert(WeatherReportModel weatherReportModel);
}
