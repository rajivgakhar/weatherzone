package com.example.a300269668.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import Model.Weather;


public class WeeklyDetailAdapter extends RecyclerView.Adapter<WeeklyDetailAdapter.ViewHolder> {

    private Context context; //current state of the class
    private List<Weather> listItems;//create custom ListItem class

    public WeeklyDetailAdapter(Context context, List<Weather> listItem) {
        this.context = context;
        listItems = listItem;
    }

    @Override
    public WeeklyDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_data, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(WeeklyDetailAdapter.ViewHolder holder, int position) {
        Weather listItem = listItems.get(position);
        holder.temp.setText(listItem.getTemp()+ "\u2103");
        holder.description.setText(listItem.getDescription());
        holder.date.setText(listItem.getTime()+" "+listItem.getDate());
        holder.txtHumidity.setText(listItem.getHumidity()+"%");

        holder.txtPressure.setText(listItem.getPressure()+"hPa");
        holder.txtWindSpeed.setText(listItem.getWindSpeed()+"km/h");
        Picasso.get()
                .load("http://openweathermap.org/img/w/"+listItem.getIcon()+".png")
                .into(holder.weatherIcon);

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView description,date,temp,txtHumidity,txtWindSpeed,txtPressure;
        public ImageView weatherIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            description = (TextView) itemView.findViewById(R.id.txtDescription);
            date = (TextView) itemView.findViewById(R.id.txtTime);
            temp = (TextView) itemView.findViewById(R.id.txtTemp);
            weatherIcon = (ImageView) itemView.findViewById(R.id.weatherImg);
            txtHumidity = (TextView) itemView.findViewById(R.id.txtHumidity);
            txtWindSpeed= (TextView) itemView.findViewById(R.id.txtWindSpeed);
            txtPressure= (TextView) itemView.findViewById(R.id.txtPressure);

        }

        @Override
        public void onClick(final View view) {


        }
    }

}
