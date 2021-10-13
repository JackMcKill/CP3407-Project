package com.cp3407.wildernessweather;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WeatherDataService {

    public static final String QUERY_FOR_CITY_ID = "https://www.metaweather.com/api/location/search/?query=";
    public static final String QUERY_FOR_WEATHER_REPORT = "https://www.metaweather.com/api/location/";
    Context context;
    String cityID;

    public WeatherDataService(Context context) {
        this.context = context;
    }

    public interface GetCityIDCallback {
        void onError(String errorMessage);

        void onResponse(String cityID);
    }

    public void getCityID(String cityName, GetCityIDCallback getCityIDCallback) {
        String url = QUERY_FOR_CITY_ID + cityName;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            cityID = "";
            try {
                JSONObject cityInfo = response.getJSONObject(0);
                cityID = cityInfo.getString("woeid");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            getCityIDCallback.onResponse(cityID);
        }, error -> getCityIDCallback.onError("Something went wrong - invalid city name"));
        Singleton.getInstance(context).addToRequestQueue(request);
    }

    public interface ForecastByIDCallback {
        void onError(String errorMessage);

        void onResponse(List<WeatherReportModel> weatherReportModels);
    }

    public void getCityForecastByID(String cityID, ForecastByIDCallback forecastByIDCallback) {
        List<WeatherReportModel> weatherReportModels = new ArrayList<>();
        String url = QUERY_FOR_WEATHER_REPORT + cityID + "/";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray consolidatedWeatherList = response.getJSONArray("consolidated_weather");
                WeatherReportModel oneDayWeather;

                for (int i = 0; i < consolidatedWeatherList.length(); i++) {
                    // Get i'th object from from the array
                    JSONObject firstDay_fromAPI = (JSONObject) consolidatedWeatherList.get(i);
                    oneDayWeather = constructWeatherReportModel(firstDay_fromAPI);
                    oneDayWeather.setCityName(response.getString("title"));
                    oneDayWeather.setWoeid(cityID);

                    weatherReportModels.add(oneDayWeather);
                }
                forecastByIDCallback.onResponse(weatherReportModels);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> {
            forecastByIDCallback.onError("Something went wrong");
        });

        Singleton.getInstance(context).addToRequestQueue(request);
    }

    public interface ForecastByNameCallback {
        void onError(String errorMessage);

        void onResponse(List<WeatherReportModel> weatherReportModels);
    }

    public void getCityForecastByName(String cityName, ForecastByNameCallback forecastByNameCallback) {
        // Get the city ID number using the given city name
        getCityID(cityName, new GetCityIDCallback() {
            @Override
            public void onError(String errorMessage) {
                // Toast displaying error message if getCityID fails
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String cityID) {
                // Get the forecast for the city using the given city ID
                getCityForecastByID(cityID, new ForecastByIDCallback() {
                    @Override
                    public void onError(String errorMessage) {
                        // Toast displaying error message if getCityForecastByID fails
                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(List<WeatherReportModel> weatherReportModels) {
                        // Return the list of weather report models
                        forecastByNameCallback.onResponse(weatherReportModels);
                    }
                });
            }
        });
    }

    public interface ForecastByDateCallback {
        void onError(String errorMessage);

        void onResponse(WeatherReportModel weatherReportModel);
    }

    public void getCityForecastByDate(String cityID, String date, String cityName, ForecastByDateCallback forecastByDateCallback) {
        String url = QUERY_FOR_WEATHER_REPORT + cityID + "/" + date + "/";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONObject consolidatedWeather = response.getJSONObject(0);

                WeatherReportModel oneDayWeather = constructWeatherReportModel(consolidatedWeather);
                oneDayWeather.setCityName(cityName);
                oneDayWeather.setWoeid(cityID);

                forecastByDateCallback.onResponse(oneDayWeather);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> {
            forecastByDateCallback.onError("Something went wrong");
        });

        Singleton.getInstance(context).addToRequestQueue(request);

    }

    // Helper method that constructs a new WeatherReportModel
    private WeatherReportModel constructWeatherReportModel(JSONObject jsonObject) {
        WeatherReportModel weatherReport = new WeatherReportModel();

        try {
            // Get properties from the object and assign to WeatherReportModel properties
            weatherReport.setId(jsonObject.getLong("id"));
            weatherReport.setWeatherStateName(jsonObject.getString("weather_state_name"));
            weatherReport.setWeatherStateAbbr(jsonObject.getString("weather_state_abbr"));
            weatherReport.setWindDirectionCompass(jsonObject.getString("wind_direction_compass"));
            weatherReport.setCreated(jsonObject.getString("created"));
            weatherReport.setApplicableDate(jsonObject.getString("applicable_date"));
            weatherReport.setMinTemp((float) jsonObject.getDouble("min_temp"));
            weatherReport.setMaxTemp((float) jsonObject.getDouble("max_temp"));
            weatherReport.setTheTemp((float) jsonObject.getDouble("the_temp"));
            weatherReport.setWindSpeed((float) jsonObject.getDouble("wind_speed"));
            weatherReport.setWindDirection((float) jsonObject.getDouble("wind_direction"));
            weatherReport.setAirPressure(jsonObject.getInt("air_pressure"));
            weatherReport.setHumidity(jsonObject.getInt("humidity"));
            weatherReport.setVisibility((float) jsonObject.getDouble("visibility"));
            weatherReport.setPredictability(jsonObject.getInt("predictability"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weatherReport;
    }
}
