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
                JSONArray consolidated_weather_list = response.getJSONArray("consolidated_weather");

                WeatherReportModel oneDayWeather;

                for (int i = 0; i < consolidated_weather_list.length(); i++) {

                    // Get i'th object from from the array
                    oneDayWeather = new WeatherReportModel();

                    JSONObject firstDay_fromAPI = (JSONObject) consolidated_weather_list.get(i);

                    // Get properties from the object and assign to WeatherReportModel properties
                    oneDayWeather.setId(firstDay_fromAPI.getLong("id"));
                    oneDayWeather.setWeather_state_name(firstDay_fromAPI.getString("weather_state_name"));
                    oneDayWeather.setWeather_state_abbr(firstDay_fromAPI.getString("weather_state_abbr"));
                    oneDayWeather.setWind_direction_compass(firstDay_fromAPI.getString("wind_direction_compass"));
                    oneDayWeather.setCreated(firstDay_fromAPI.getString("created"));
                    oneDayWeather.setApplicable_date(firstDay_fromAPI.getString("applicable_date"));
                    oneDayWeather.setMin_temp((float) firstDay_fromAPI.getDouble("min_temp"));
                    oneDayWeather.setMax_temp((float) firstDay_fromAPI.getDouble("max_temp"));
                    oneDayWeather.setThe_temp((float) firstDay_fromAPI.getDouble("the_temp"));
                    oneDayWeather.setWind_speed((float) firstDay_fromAPI.getDouble("wind_speed"));
                    oneDayWeather.setWind_direction((float) firstDay_fromAPI.getDouble("wind_direction"));
                    oneDayWeather.setAir_pressure(firstDay_fromAPI.getInt("air_pressure"));
                    oneDayWeather.setHumidity(firstDay_fromAPI.getInt("humidity"));
                    oneDayWeather.setVisibility((float) firstDay_fromAPI.getDouble("visibility"));
                    oneDayWeather.setPredictability(firstDay_fromAPI.getInt("predictability"));

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
}
