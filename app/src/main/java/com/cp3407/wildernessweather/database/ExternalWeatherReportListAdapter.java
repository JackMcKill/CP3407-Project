package com.cp3407.wildernessweather.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cp3407.wildernessweather.R;
import com.cp3407.wildernessweather.WeatherReportModel;

import java.util.List;

public class ExternalWeatherReportListAdapter extends RecyclerView.Adapter<ExternalWeatherReportListAdapter.ExternalWeatherReportViewHolder> {

    private final LayoutInflater layoutInflater;
    private List<WeatherReportModel> weatherReports;

    public ExternalWeatherReportListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ExternalWeatherReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.external_recycler_list_item, parent, false);
        return new ExternalWeatherReportViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExternalWeatherReportViewHolder holder, int position) {
        if (weatherReports != null) {
            WeatherReportModel weatherReport = weatherReports.get(position);
            holder.setData(weatherReport.getId(), weatherReport.getCityName(), weatherReport.getApplicableDate(), position);
        } else {
            // This is only run if the database is empty
            holder.listItemCityName.setText(R.string.nothing_in_database);
        }
    }

    @Override
    public int getItemCount() {
        if (weatherReports != null) {
            return weatherReports.size();
        } else return 0;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setWeatherReports(List<WeatherReportModel> weatherReports) {
        this.weatherReports = weatherReports;
        notifyDataSetChanged();
    }

    public static class ExternalWeatherReportViewHolder extends RecyclerView.ViewHolder {
        private final TextView listItemCityName;
        private final TextView listItemDate;

        public ExternalWeatherReportViewHolder(@NonNull View itemView) {
            super(itemView);
            listItemCityName = itemView.findViewById(R.id.tv_itemCityNameExternal);
            listItemDate = itemView.findViewById(R.id.tv_itemDateExternal);
        }

        // This method is setting the weather report item into the recyclerView
        public void setData(long id, String cityName, String date, int position) {
            listItemCityName.setText(cityName);
            listItemDate.setText(date);
        }
    }
}
