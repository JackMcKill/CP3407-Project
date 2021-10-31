package com.cp3407.wildernessweather.helperClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cp3407.wildernessweather.R;
import com.cp3407.wildernessweather.WeatherReportModel;

import java.util.ArrayList;

public class ForecastListAdapter extends ArrayAdapter<WeatherReportModel> {

    private static final String TAG = "WeatherReportModelListAdapter";

    private Context mContext;
    int mResource;
    boolean isMetric;

    public ForecastListAdapter(Context context, int resource, ArrayList<WeatherReportModel> objects, boolean isMetric) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        this.isMetric = isMetric;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        String cityName = getItem(position).getCityName();
        String applicableDate = convertDateString(getItem(position).getApplicableDate());
        String weatherStateAbbr = getItem(position).getWeatherStateAbbr();
        float minTemp;
        float maxTemp;

        if (isMetric) {
            minTemp = getItem(position).getMinTemp();
            maxTemp = getItem(position).getMaxTemp();

        } else {
            minTemp = getItem(position).getMinTempImperial();
            maxTemp = getItem(position).getMaxTempImperial();
        }

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView cityNameView = convertView.findViewById(R.id.tv_cityName);
        TextView applicableDateView = convertView.findViewById(R.id.tv_applicableDate);
        TextView minTempView = convertView.findViewById(R.id.tv_minTemp);
        TextView maxTempView = convertView.findViewById(R.id.tv_maxTemp);
        ImageView weatherStateImage = convertView.findViewById(R.id.iv_stateImage);

        cityNameView.setText(String.valueOf(cityName));
        applicableDateView.setText(String.valueOf(applicableDate));
        minTempView.setText(Math.round(minTemp) + "°");
        maxTempView.setText(Math.round(maxTemp) + "°");

        int weatherStateImageResID = mContext.getResources().getIdentifier("ic_" + weatherStateAbbr, "drawable", mContext.getPackageName());
        weatherStateImage.setImageResource(weatherStateImageResID);

        return convertView;
    }

    /**
     * Converts date string in format YYYY-MM-DD to the format DD/MM/YYYY.
     *
     * @param dateString date string in old format.
     * @return date string in new format.
     */
    public String convertDateString(String dateString) {
        String[] parts = dateString.split("-");
        return parts[2] + "/" + parts[1] + "/" + parts[0];
    }
}
