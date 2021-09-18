package com.cp3407.wildernessweather.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "weather")
public class WeatherReport {

    @PrimaryKey
    @NonNull
    private long id;

    @NonNull
    private String weather_state_name;

    @NonNull
    private String weather_state_abbr;

    @NonNull
    private String wind_direction_compass;

    @NonNull
    private String created;

    @NonNull
    private String applicable_date;

    @NonNull
    private float min_temp;

    @NonNull
    private float max_temp;

    @NonNull
    private float the_temp;

    @NonNull
    private float wind_speed;

    @NonNull
    private float wind_direction;

    @NonNull
    private int air_pressure;

    @NonNull
    private int humidity;

    @NonNull
    private float visibility;

    @NonNull
    private int predictability;

    public WeatherReport(long id, @NonNull String weather_state_name, @NonNull String weather_state_abbr, @NonNull String wind_direction_compass, @NonNull String created, @NonNull String applicable_date, float min_temp, float max_temp, float the_temp, float wind_speed, float wind_direction, int air_pressure, int humidity, float visibility, int predictability) {
        this.id = id;
        this.weather_state_name = weather_state_name;
        this.weather_state_abbr = weather_state_abbr;
        this.wind_direction_compass = wind_direction_compass;
        this.created = created;
        this.applicable_date = applicable_date;
        this.min_temp = min_temp;
        this.max_temp = max_temp;
        this.the_temp = the_temp;
        this.wind_speed = wind_speed;
        this.wind_direction = wind_direction;
        this.air_pressure = air_pressure;
        this.humidity = humidity;
        this.visibility = visibility;
        this.predictability = predictability;
    }

    public long getId() {
        return id;
    }

    @NonNull
    public String getWeather_state_name() {
        return weather_state_name;
    }

    @NonNull
    public String getWeather_state_abbr() {
        return weather_state_abbr;
    }

    @NonNull
    public String getWind_direction_compass() {
        return wind_direction_compass;
    }

    @NonNull
    public String getCreated() {
        return created;
    }

    @NonNull
    public String getApplicable_date() {
        return applicable_date;
    }

    public float getMin_temp() {
        return min_temp;
    }

    public float getMax_temp() {
        return max_temp;
    }

    public float getThe_temp() {
        return the_temp;
    }

    public float getWind_speed() {
        return wind_speed;
    }

    public float getWind_direction() {
        return wind_direction;
    }

    public int getAir_pressure() {
        return air_pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public float getVisibility() {
        return visibility;
    }

    public int getPredictability() {
        return predictability;
    }
}
