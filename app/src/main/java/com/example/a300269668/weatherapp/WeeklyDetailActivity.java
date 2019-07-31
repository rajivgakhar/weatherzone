package com.example.a300269668.weatherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.Weather;

public class WeeklyDetailActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    List<Weather> hourlytData;
    String day,lat,lng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_detail);
        setupToolbarMenu();
        Intent in=getIntent();
         day=in.getStringExtra("day");
        lat=in.getStringExtra("lat");
        lng=in.getStringExtra("lng");
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        getForecastHourlyData(lat,lng);

    }
    private void setupToolbarMenu() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        mToolbar.setNavigationIcon(R.drawable.back_arrow);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WeeklyDetailActivity.this, MainActivity.class));
                finish();
            }
        });

    }
    private void getForecastHourlyData(String lattitude, String longitude) {
        String forecastWeatherurl = "http://api.openweathermap.org/data/2.5/forecast?lat=" + lattitude + "&lon=" + longitude + "&appid=6504cae6812684c9cb83e32bb5c0b09c";
        hourlytData = new ArrayList<>();
        Map<String, String> params = new HashMap<String, String>();
        params.put("weId", "1");
        CustomRequest user_request = new CustomRequest(Request.Method.GET, forecastWeatherurl, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {

                    String status = response.getString("cod");
                    if (status.equals("200")) {
                        JSONArray ja = response.getJSONArray("list");
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jobj = ja.getJSONObject(i);
                            Weather listItem = new Weather(jobj.getString("dt")
                                    , jobj.getJSONObject("main").getString("temp")
                                    , jobj.getJSONArray("weather").getJSONObject(0).getString("icon")
                                    , jobj.getJSONArray("weather").getJSONObject(0).getString("description")
                                    , jobj.getJSONObject("main").getString("humidity")
                                    , jobj.getJSONObject("main").getString("pressure")
                                    , jobj.getJSONObject("wind").getString("speed")
                            );

                            if (listItem.getDay().equals(day)) {
                                hourlytData.add(listItem);
                            }
                        }
                        adapter = new WeeklyDetailAdapter(WeeklyDetailActivity.this, hourlytData);
                        recyclerView.setLayoutManager(new GridLayoutManager(WeeklyDetailActivity.this, 1));
                        recyclerView.setAdapter(adapter);
                        mToolbar.setTitle(hourlytData.get(0).getDate()+"");
                    }

                } catch (Exception e) {
                    Log.e("dataErr", e.getMessage());
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {

            }
        });

        // Adding request to request queue
        MyApplication.getInstance().addToReqQueue(user_request);
    }
}
