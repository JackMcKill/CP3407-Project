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

    @PrimaryKey(autoGenerate = true)
    @NonNull
    long id; // this ID is auto-generated and is used to identify database entities. See database documentation
    @NonNull
    long trueID; // this ID is provided by metaweather. See database documentation
    @NonNull
    String cityName;
    @NonNull
    String woeid;
    @NonNull
    String weatherStateName;
    @NonNull
    String weatherStateAbbr;
    @NonNull
    String windDirectionCompass;
    @NonNull
    String created;
    @NonNull
    String applicableDate;
    @NonNull
    float minTemp;
    @NonNull
    float maxTemp;
    @NonNull
    float theTemp;
    @NonNull
    float windSpeed;
    @NonNull
    float windDirection;
    @NonNull
    int airPressure;
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

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public long getTrueID() {
        return trueID;
    }

    public void setTrueID(long trueID) {
        this.trueID = trueID;
    }

    public String getWoeid() {
        return woeid;
    }

    public void setWoeid(String woeid) {
        this.woeid = woeid;
    }


    @NonNull
    public String getWeatherStateName() {
        return weatherStateName;
    }

    public void setWeatherStateName(String weatherStateName) {
        this.weatherStateName = weatherStateName;
    }

    @NonNull
    public String getWeatherStateAbbr() {
        return weatherStateAbbr;
    }

    public void setWeatherStateAbbr(String weatherStateAbbr) {
        this.weatherStateAbbr = weatherStateAbbr;
    }

    @NonNull
    public String getWindDirectionCompass() {
        return windDirectionCompass;
    }

    public void setWindDirectionCompass(String windDirectionCompass) {
        this.windDirectionCompass = windDirectionCompass;
    }

    @NonNull
    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    @NonNull
    public String getApplicableDate() {
        return applicableDate;
    }

    public void setApplicableDate(String applicableDate) {
        this.applicableDate = applicableDate;
    }

    public float getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(float minTemp) {
        this.minTemp = minTemp;
    }

    public float getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(float maxTemp) {
        this.maxTemp = maxTemp;
    }

    public float getTheTemp() {
        return theTemp;
    }

    public void setTheTemp(float theTemp) {
        this.theTemp = theTemp;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public float getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(float windDirection) {
        this.windDirection = windDirection;
    }

    public int getAirPressure() {
        return airPressure;
    }

    public void setAirPressure(int airPressure) {
        this.airPressure = airPressure;
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

    // This method should only be used when prototyping - USER SHOULD NEVER SEE THIS OUTPUT
    @Override
    public String toString() {
        return "WeatherReportModel{" +
                "id=" + id +
                "trueID=" + trueID + "\n" +
                "weather_state_name='" + weatherStateName + "\n" +
                "weather_state_abbr='" + weatherStateAbbr + "\n" +
                "wind_direction_compass='" + windDirectionCompass + "\n" +
                "created='" + created + "\n" +
                "applicable_date='" + applicableDate + "\n" +
                "min_temp=" + minTemp + "\n" +
                "max_temp=" + maxTemp + "\n" +
                "the_temp=" + theTemp + "\n" +
                "wind_speed=" + windSpeed + "\n" +
                "wind_direction=" + windDirection + "\n" +
                "air_pressure=" + airPressure + "\n" +
                "humidity=" + humidity + "\n" +
                "visibility=" + visibility + "\n" +
                "predictability=" + predictability +
                '}';
    }
}