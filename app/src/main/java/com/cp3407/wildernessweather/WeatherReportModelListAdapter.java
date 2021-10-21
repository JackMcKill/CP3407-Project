package com.cp3407.wildernessweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class WeatherReportModelListAdapter extends ArrayAdapter<WeatherReportModel> {

    private static final String TAG = "WeatherReportModelListAdapter";

    private Context mContext;
    int mResource;

    public WeatherReportModelListAdapter(Context context, int resource, ArrayList<WeatherReportModel> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        String cityName = getItem(position).getCityName();
        String weatherStateAbbr = getItem(position).getWeatherStateAbbr();
        float minTemp = getItem(position).getMinTemp();
        float maxTemp = getItem(position).getMaxTemp();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView cityNameView = convertView.findViewById(R.id.tv_cityName);
        TextView minTempView = convertView.findViewById(R.id.tv_minTemp);
        TextView maxTempView = convertView.findViewById(R.id.tv_maxTemp);
        ImageView weatherStateImage = convertView.findViewById(R.id.iv_stateImage);

        cityNameView.setText(String.valueOf(cityName));
        minTempView.setText(Math.round(minTemp) + "°");
        maxTempView.setText(Math.round(maxTemp) + "°");

        int weatherStateImageResID = mContext.getResources().getIdentifier("ic_" + weatherStateAbbr, "drawable", mContext.getPackageName());
        System.out.printf("ID: %d", weatherStateImageResID);
        weatherStateImage.setImageResource(weatherStateImageResID);

        return convertView;
    }
}
