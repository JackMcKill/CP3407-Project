package com.cp3407.wildernessweather.database;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface WeatherReportDao {

    @Insert
    void insert(WeatherReport weatherReport);
}
