package com.example.a300269668.weatherapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import Model.Weather;


public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.ViewHolder> {

    private Context context; //current state of the class
    private List<Weather> listItems;//create custom ListItem class
    private String userId = "";

    public HourlyAdapter(Context context, List<Weather> listItem) {
        this.context = context;
        listItems = listItem;
    }

    @Override
    public HourlyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_data, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(HourlyAdapter.ViewHolder holder, int position) {
        Weather listItem = listItems.get(position);
        holder.temp.setText(listItem.getTemp()+ (char) 0x00B0);
        holder.time.setText(listItem.getTime());
        Picasso.get()
                .load("http://openweathermap.org/img/w/"+listItem.getIcon()+".png")
                .into(holder.weatherIcon);

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView time,temp;
        public ImageView weatherIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.txtTime);
            temp = (TextView) itemView.findViewById(R.id.txtTemp);
            weatherIcon = (ImageView) itemView.findViewById(R.id.weatherImg);
        }

        @Override
        public void onClick(final View view) {
            int position = getAdapterPosition();

        }
    }

}
