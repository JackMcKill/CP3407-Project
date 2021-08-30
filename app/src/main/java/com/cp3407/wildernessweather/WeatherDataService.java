package com.cp3407.wildernessweather;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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

    public interface GetCityIDResponseListener {
        void onError(String message);

        void onResponse(String cityID);
    }

    public void getCityID(String cityName, GetCityIDResponseListener getCityIDResponseListener) {
        String url = QUERY_FOR_CITY_ID + cityName;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                cityID = "";
                try {
                    JSONObject cityInfo = response.getJSONObject(0);
                    cityID = cityInfo.getString("woeid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getCityIDResponseListener.onResponse(cityID);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getCityIDResponseListener.onError("Something went wrong");
            }
        });
        Singleton.getInstance(context).addToRequestQueue(request);
    }

    public interface ForecastByIDResponseListener {
        void onError(String message);

        void onResponse(WeatherReportModel weatherReportModel);
    }

    public void getCityForecastByID(String cityID, ForecastByIDResponseListener forecastByIDResponseListener) {
        List<WeatherReportModel> report = new ArrayList<>();
        String url = QUERY_FOR_WEATHER_REPORT + cityID + "/";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray consolidated_weather_list = response.getJSONArray("consolidated_weather");

                    // Get first object from from the array
                    WeatherReportModel firstDay = new WeatherReportModel();
                    JSONObject firstDay_fromAPI = (JSONObject) consolidated_weather_list.get(0);

                    // Get properties from the object and assign to WeatherReportModel properties
                    firstDay.setId(firstDay_fromAPI.getInt("id"));
                    firstDay.setWeather_state_name(firstDay_fromAPI.getString("weather_state_name"));
                    firstDay.setWeather_state_abbr(firstDay_fromAPI.getString("weather_state_abbr"));
                    firstDay.setWind_direction_compass(firstDay_fromAPI.getString("wind_direction_compass"));
                    firstDay.setCreated(firstDay_fromAPI.getString("created"));
                    firstDay.setApplicable_date(firstDay_fromAPI.getString("applicable_date"));
                    firstDay.setMin_temp(firstDay_fromAPI.getLong("min_temp"));
                    firstDay.setMax_temp(firstDay_fromAPI.getLong("max_temp"));
                    firstDay.setThe_temp(firstDay_fromAPI.getLong("the_temp"));
                    firstDay.setWind_speed(firstDay_fromAPI.getLong("wind_speed"));
                    firstDay.setWind_direction(firstDay_fromAPI.getLong("wind_direction"));
                    firstDay.setAir_pressure(firstDay_fromAPI.getInt("air_pressure"));
                    firstDay.setHumidity(firstDay_fromAPI.getInt("humidity"));
                    firstDay.setVisibility(firstDay_fromAPI.getLong("visibility"));
                    firstDay.setPredictability(firstDay_fromAPI.getInt("predictability"));

                    forecastByIDResponseListener.onResponse(firstDay);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
            }
        });

        Singleton.getInstance(context).addToRequestQueue(request);
    }
//
//    public List<WeatherReportModel> getCityForecastByName(String cityName) {
//
//    }

}
