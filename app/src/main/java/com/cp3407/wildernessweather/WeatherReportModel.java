package com.cp3407.wildernessweather;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.parceler.Parcel;

@Parcel
@Entity(tableName = "weather")
public class WeatherReportModel {

    // Properties from metaweather REST api data
    // Properties must be public in order to use the Parceler dependency

    @PrimaryKey
    @NonNull
    long id;
    @NonNull
    String weather_state_name;
    @NonNull
    String weather_state_abbr;
    @NonNull
    String wind_direction_compass;
    @NonNull
    String created;
    @NonNull
    String applicable_date;
    @NonNull
    float min_temp;
    @NonNull
    float max_temp;
    @NonNull
    float the_temp;
    @NonNull
    float wind_speed;
    @NonNull
    float wind_direction;
    @NonNull
    int air_pressure;
    @NonNull
    int humidity;
    @NonNull
    float visibility;
    @NonNull
    int predictability;

    // Empty constructor
    public WeatherReportModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getWeather_state_name() {
        return weather_state_name;
    }

    public void setWeather_state_name(String weather_state_name) {
        this.weather_state_name = weather_state_name;
    }

    @NonNull
    public String getWeather_state_abbr() {
        return weather_state_abbr;
    }

    public void setWeather_state_abbr(String weather_state_abbr) {
        this.weather_state_abbr = weather_state_abbr;
    }

    @NonNull
    public String getWind_direction_compass() {
        return wind_direction_compass;
    }

    public void setWind_direction_compass(String wind_direction_compass) {
        this.wind_direction_compass = wind_direction_compass;
    }

    @NonNull
    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    @NonNull
    public String getApplicable_date() {
        return applicable_date;
    }

    public void setApplicable_date(String applicable_date) {
        this.applicable_date = applicable_date;
    }

    public float getMin_temp() {
        return min_temp;
    }

    public void setMin_temp(float min_temp) {
        this.min_temp = min_temp;
    }

    public float getMax_temp() {
        return max_temp;
    }

    public void setMax_temp(float max_temp) {
        this.max_temp = max_temp;
    }

    public float getThe_temp() {
        return the_temp;
    }

    public void setThe_temp(float the_temp) {
        this.the_temp = the_temp;
    }

    public float getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(float wind_speed) {
        this.wind_speed = wind_speed;
    }

    public float getWind_direction() {
        return wind_direction;
    }

    public void setWind_direction(float wind_direction) {
        this.wind_direction = wind_direction;
    }

    public int getAir_pressure() {
        return air_pressure;
    }

    public void setAir_pressure(int air_pressure) {
        this.air_pressure = air_pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public float getVisibility() {
        return visibility;
    }

    public void setVisibility(float visibility) {
        this.visibility = visibility;
    }

    public int getPredictability() {
        return predictability;
    }

    public void setPredictability(int predictability) {
        this.predictability = predictability;
    }

    @Override
    public String toString() {
        return "WeatherReportModel{" +
                "id=" + id +
                "weather_state_name='" + weather_state_name + "\n" +
                "weather_state_abbr='" + weather_state_abbr + "\n" +
                "wind_direction_compass='" + wind_direction_compass + "\n" +
                "created='" + created + "\n" +
                "applicable_date='" + applicable_date + "\n" +
                "min_temp=" + min_temp + "\n" +
                "max_temp=" + max_temp + "\n" +
                "the_temp=" + the_temp + "\n" +
                "wind_speed=" + wind_speed + "\n" +
                "wind_direction=" + wind_direction + "\n" +
                "air_pressure=" + air_pressure + "\n" +
                "humidity=" + humidity + "\n" +
                "visibility=" + visibility + "\n" +
                "predictability=" + predictability +
                '}';
    }
}
