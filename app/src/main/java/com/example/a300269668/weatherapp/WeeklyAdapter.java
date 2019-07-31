package com.example.a300269668.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import Model.Weather;


public class WeeklyAdapter extends RecyclerView.Adapter<WeeklyAdapter.ViewHolder> {

    private Context context; //current state of the class
    private List<Weather> listItems;//create custom ListItem class
    private String userId = "";

    public WeeklyAdapter(Context context, List<Weather> listItem) {
        this.context = context;
        listItems = listItem;
    }

    @Override
    public WeeklyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.weekly_data, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(WeeklyAdapter.ViewHolder holder, int position) {
        Weather listItem = listItems.get(position);
        holder.temp.setText(listItem.getTemp()+ (char) 0x00B0);
        holder.day.setText(listItem.getDay());
        holder.date.setText(listItem.getDateV2());
        Picasso.get()
                .load("http://openweathermap.org/img/w/"+listItem.getIcon()+".png")
                .into(holder.weatherIcon);

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView day,date,temp;
        public ImageView weatherIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            day = (TextView) itemView.findViewById(R.id.txtDay);
            date = (TextView) itemView.findViewById(R.id.txtDate);
            temp = (TextView) itemView.findViewById(R.id.txtTemp);
            weatherIcon = (ImageView) itemView.findViewById(R.id.weatherImg);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(final View view) {

            int position = getAdapterPosition();
            Weather listItem = listItems.get(position);
            Intent intent= new Intent(context,WeeklyDetailActivity.class);
            intent.putExtra("day",day.getText().toString());
            intent.putExtra("lat",listItem.getLat());
            intent.putExtra("lng",listItem.getLng());
            context.startActivity(intent);
        }
    }

}
