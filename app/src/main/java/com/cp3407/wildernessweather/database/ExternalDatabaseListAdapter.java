package com.cp3407.wildernessweather.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cp3407.wildernessweather.R;
import com.cp3407.wildernessweather.SingleWeatherReportActivity;
import com.cp3407.wildernessweather.WeatherReportModel;

import org.parceler.Parcels;

import java.util.List;

public class ExternalDatabaseListAdapter extends RecyclerView.Adapter<ExternalDatabaseListAdapter.ExternalWeatherReportViewHolder> {

    private final LayoutInflater layoutInflater;
    private Context context;
    private List<WeatherReportModel> weatherReports;

    public ExternalDatabaseListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
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

    public class ExternalWeatherReportViewHolder extends RecyclerView.ViewHolder {
        private TextView listItemCityName;
        private TextView listItemDate;
        private LinearLayout listItem;
        private int position;

        public ExternalWeatherReportViewHolder(@NonNull View itemView) {
            super(itemView);
            listItem = itemView.findViewById(R.id.ll_itemRowExternal);
            listItemCityName = itemView.findViewById(R.id.tv_itemCityNameExternal);
            listItemDate = itemView.findViewById(R.id.tv_itemDateExternal);
            listItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("recyclerView", "Item " + position + " pressed");
                    // Opens a new SingleWeatherReportActivity - does not populate fields yet.
                    Intent intent = new Intent(context, SingleWeatherReportActivity.class);
                    intent.putExtra("report", Parcels.wrap(weatherReports.get(position)));
                    context.startActivity(intent);
                }
            });
        }

        // This method is setting the weather report item into the recyclerView
        public void setData(long id, String cityName, String date, int position) {
            listItemCityName.setText(cityName);
            listItemDate.setText(date);
            this.position = position;
        }
    }
}
