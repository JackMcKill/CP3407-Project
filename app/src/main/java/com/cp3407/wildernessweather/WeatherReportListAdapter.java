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
    private final LayoutInflater layoutInflater;
    private Context context;
    private List<WeatherReportModel> weatherReports;

    public WeatherReportListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;

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
            holder.setData(weatherReport.getId(), position);
            holder.setListeners();
        } else {
            // This is only run if the database is empty
            holder.weatherReportItemView.setText(R.string.nothing_in_database);
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

        private TextView weatherReportItemView;
        private int position;
        private ImageView deleteButton;


        public WeatherReportViewHolder(@NonNull View itemView) {
            super(itemView);
            weatherReportItemView = itemView.findViewById(R.id.tv_listItemTitle);
            deleteButton = itemView.findViewById(R.id.iv_RowDelete);
        }

        // This method is setting the weather report item into the recyclerView
        public void setData(long id, int position) {
            // TODO change this so that instead of displaying the ID, we are displaying the date. ID is irrelevant to the user
            weatherReportItemView.setText(String.valueOf(id)); // This is what will be displayed on the recyclerview
            this.position = position;
        }

        // Sets onClickListeners
        public void setListeners() {

            // Code here runs whenever an item in the recyclerView is pressed
            weatherReportItemView.setOnClickListener(new View.OnClickListener() {
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
                }
            });
        }
    }
}
