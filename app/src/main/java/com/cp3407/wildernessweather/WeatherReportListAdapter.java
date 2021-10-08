package com.cp3407.wildernessweather;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.parceler.Parcels;

import java.util.List;

public class WeatherReportListAdapter extends RecyclerView.Adapter<WeatherReportListAdapter.WeatherReportViewHolder> {

    public interface OnDeleteClickListener {
        void OnDeleteClickListener(WeatherReportModel myModel);
    }

    private final LayoutInflater layoutInflater;
    private Context context;
    private List<WeatherReportModel> weatherReports;
    private OnDeleteClickListener onDeleteClickListener;

    public WeatherReportListAdapter(Context context, OnDeleteClickListener listener) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.onDeleteClickListener = listener;
    }

    @NonNull
    @Override
    public WeatherReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.recycler_list_item, parent, false);
        WeatherReportViewHolder viewHolder = new WeatherReportViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherReportViewHolder holder, int position) {
        // Check if the list of WeatherReportModels is empty
        if (weatherReports != null) {
            WeatherReportModel weatherReport = weatherReports.get(position);
            holder.setData(weatherReport.getId(), weatherReport.getCityName(), weatherReport.getApplicableDate(), position);
            holder.setListeners();
        } else {
            // This is only run if the database is empty
            holder.listItemID.setText(R.string.nothing_in_database);
        }
    }

    @Override
    public int getItemCount() {
        if (weatherReports != null) {
            return weatherReports.size();
        } else return 0;
    }

    public void setWeatherReports(List<WeatherReportModel> weatherReports) {
        this.weatherReports = weatherReports;
        notifyDataSetChanged();

    }

    public class WeatherReportViewHolder extends RecyclerView.ViewHolder {

        private TextView listItemID;
        private TextView listItemCityName;
        private TextView listItemDate;
        private int position;
        private ImageView deleteButton;


        public WeatherReportViewHolder(@NonNull View itemView) {
            super(itemView);
            listItemID = itemView.findViewById(R.id.tv_itemID);
            listItemCityName = itemView.findViewById(R.id.tv_itemCityName);
            listItemDate = itemView.findViewById(R.id.tv_itemDate);
            deleteButton = itemView.findViewById(R.id.iv_RowDelete);
        }

        // This method is setting the weather report item into the recyclerView
        public void setData(long id, String cityName, String date, int position) {
            listItemID.setText(String.valueOf(id)); // This is what will be displayed on the recyclerview
            listItemCityName.setText(cityName);
            listItemDate.setText(date);
            this.position = position;
        }

        // Sets onClickListeners
        public void setListeners() {
            // Code here runs whenever an item in the recyclerView is pressed
            // TODO change so user can click anywhere on the list item, not just the ID number
            listItemID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("recyclerView", "Item " + position + " pressed");
                    // Opens a new SingleWeatherReportActivity - does not populate fields yet.
                    Intent intent = new Intent(context, SingleWeatherReportActivity.class);
                    intent.putExtra("report", Parcels.wrap(weatherReports.get(position)));
                    context.startActivity(intent);
                }
            });

            // Code for the delete button
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO code to delete an item from the database here
                    if (onDeleteClickListener != null) {
                        onDeleteClickListener.OnDeleteClickListener(weatherReports.get(position));
                    }
                }
            });
        }
    }
}
