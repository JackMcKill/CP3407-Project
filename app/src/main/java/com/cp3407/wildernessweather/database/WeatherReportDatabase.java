package com.cp3407.wildernessweather.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.cp3407.wildernessweather.WeatherReportModel;

@Database(entities = WeatherReportModel.class, version = 1)
public abstract class WeatherReportDatabase extends RoomDatabase {

    public abstract WeatherReportDao weatherReportDao();

    // Singleton design pattern
    private static volatile WeatherReportDatabase weatherDatabaseInstance;

    static WeatherReportDatabase getDatabase(final Context context) {
        if (weatherDatabaseInstance == null) {
            synchronized (WeatherReportDatabase.class) {
                if (weatherDatabaseInstance == null) {
                    weatherDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                            WeatherReportDatabase.class, "weather_database")
                            .build();
                }
            }
        }
        return weatherDatabaseInstance;
    }
}
