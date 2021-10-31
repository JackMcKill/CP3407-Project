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
    long id; // this ID is auto-generated and is used to identify database entities. See database documentation
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

    @NonNull
    public String getCityName() {
        return cityName;
    }

    public void setCityName(@NonNull String cityName) {
        this.cityName = cityName;
    }

    public long getTrueID() {
        return trueID;
    }

    public void setTrueID(long trueID) {
        this.trueID = trueID;
    }

    @NonNull
    public String getWoeid() {
        return woeid;
    }

    public void setWoeid(@NonNull String woeid) {
        this.woeid = woeid;
    }


    @NonNull
    public String getWeatherStateName() {
        return weatherStateName;
    }

    public void setWeatherStateName(@NonNull String weatherStateName) {
        this.weatherStateName = weatherStateName;
    }

    @NonNull
    public String getWeatherStateAbbr() {
        return weatherStateAbbr;
    }

    public void setWeatherStateAbbr(@NonNull String weatherStateAbbr) {
        this.weatherStateAbbr = weatherStateAbbr;
    }

    @NonNull
    public String getWindDirectionCompass() {
        return windDirectionCompass;
    }

    public void setWindDirectionCompass(@NonNull String windDirectionCompass) {
        this.windDirectionCompass = windDirectionCompass;
    }

    @NonNull
    public String getCreated() {
        return created;
    }

    public void setCreated(@NonNull String created) {
        this.created = created;
    }

    @NonNull
    public String getApplicableDate() {
        return applicableDate;
    }

    public void setApplicableDate(@NonNull String applicableDate) {
        this.applicableDate = applicableDate;
    }

    public float getMinTemp() {
        return minTemp;
    }

    public float getMinTempImperial() {
        return (float) ((minTemp * 1.8) + 32);
    }

    public void setMinTemp(float minTemp) {
        this.minTemp = minTemp;
    }

    public float getMaxTemp() {
        return maxTemp;
    }

    public float getMaxTempImperial() {
        return (float) ((maxTemp * 1.8) + 32);
    }

    public void setMaxTemp(float maxTemp) {
        this.maxTemp = maxTemp;
    }

    public float getTheTemp() {
        return theTemp;
    }

    public float getTheTempImperial() {
        return (float) ((theTemp * 1.8) + 32);
    }

    public void setTheTemp(float theTemp) {
        this.theTemp = theTemp;
    }

    public float getWindSpeed() {
        return (float) (windSpeed * 1.60934);
    }

    public float getWindSpeedImperial() {
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
        return (float) (visibility * 1.60934);
    }

    public float getVisibilityImperial() {
        return (visibility);
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
    @NonNull
    @Override
    public String toString() {
        return "WeatherReportModel{" +
                "id=" + id +
                "trueID=" + trueID + "\n" +
                "city_name=" + cityName + "\n " +
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

    public String[] exportString() {
        return new String[]{String.valueOf(trueID), woeid, weatherStateName, weatherStateAbbr, windDirectionCompass, created,
                applicableDate, String.valueOf(minTemp), String.valueOf(maxTemp), String.valueOf(theTemp), String.valueOf(windSpeed),
                String.valueOf(windDirection), String.valueOf(airPressure), String.valueOf(humidity), String.valueOf(visibility), String.valueOf(predictability)};
    }

    public String exportToDatabaseString() {
        return "'" + trueID + "', " +
                "'" + weatherStateName + "', " +
                "'" + weatherStateAbbr + "', " +
                "'" + windDirectionCompass + "', " +
                "'" + created + "', " +
                "'" + applicableDate + "', " +
                "'" + minTemp + "', " +
                "'" + maxTemp + "', " +
                "'" + theTemp + "', " +
                "'" + windSpeed + "', " +
                "'" + windDirection + "', " +
                "'" + airPressure + "', " +
                "'" + humidity + "', " +
                "'" + visibility + "', " +
                "'" + predictability + "', " +
                "'" + cityName + "', " +
                "'" + woeid + "'";
    }

}
