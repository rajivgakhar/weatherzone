package com.example.a300269668.weatherapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import Model.Weather;

public class GridViewAdapter extends BaseAdapter {
    private Context context;
    private  Weather weatherData;
    private String[] params;

    public GridViewAdapter(Context context, Weather weatherData,String[] param) {
        this.context = context;
        this.weatherData = weatherData;
        this.params=param;
    }
    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (view == null) {

            gridView = new View(context);

            gridView = inflater.inflate(R.layout.gridview_layout, null);

            TextView title = (TextView) gridView
                    .findViewById(R.id.txtTitle);
            TextView value = (TextView) gridView
                    .findViewById(R.id.txtValue);
            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.paramImg);

            String param = params[i];
            if (param.equals("Humidity")) {

                imageView.setImageResource(R.drawable.humidity);
                title.setText("Humidity");
                value.setText(weatherData.getHumidity()+"%");
            } else if (param.equals("Visibility")) {
                imageView.setImageResource(R.drawable.eye);
                title.setText("Visibility");
                value.setText(weatherData.getVisibility()+"km");
            } else if (param.equals("Pressure")) {
                imageView.setImageResource(R.drawable.pressure);
                title.setText("Pressure");
                value.setText(weatherData.getPressure()+"hPa");
            } else if (param.equals("Wind")) {
                imageView.setImageResource(R.drawable.wind);
                title.setText("Wind");
                value.setText(weatherData.getWindSpeed()+"km/h");
            }else if (param.equals("Sunrise")) {
                imageView.setImageResource(R.drawable.sunrise);
                title.setText("Sunrise");
                value.setText(weatherData.getSunrise());
            }else if (param.equals("Sunset")) {
                imageView.setImageResource(R.drawable.sunset);
                title.setText("Sunset");
                value.setText(weatherData.getSunset());
            }

        } else {
            gridView = (View) view;
        }

        return gridView;
    }
}
