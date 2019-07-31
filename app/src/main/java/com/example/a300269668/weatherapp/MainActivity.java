package com.example.a300269668.weatherapp;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.SearchRecentSuggestions;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.Weather;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
    private TextView date, temp, env, maxMin, refresh, time, search;
    private ImageView ivWeather;
    private String forecastWeatherurl = "http://api.openweathermap.org/data/2.5/forecast?lat=49.2&lon=-122.91&appid=6504cae6812684c9cb83e32bb5c0b09c";
    List<Weather> hourlytData, weeklyData;
    Weather currentWeather;
    private Toolbar mToolbar;
    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    String lattitude, longitude;
    private RecyclerView recyclerView, recyclerView1;
    private RecyclerView.Adapter adapter;
    static final String[] paramWeather = new String[]{
            "Humidity", "Visibility", "Pressure", "Wind", "Sunrise", "Sunset"};
    private GridView gvParams;
    String latt, longg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbarMenu();
        date = (TextView) findViewById(R.id.txtDate);
        time = (TextView) findViewById(R.id.txtTime);
        temp = (TextView) findViewById(R.id.txtTemp);
        env = (TextView) findViewById(R.id.txtImgWeather);
        search = (TextView) findViewById(R.id.searchCity);
        maxMin = (TextView) findViewById(R.id.txtMaxMin);
        refresh = (TextView) findViewById(R.id.txtWind);
        gvParams = (GridView) findViewById(R.id.gvParameters);
        ivWeather = (ImageView) findViewById(R.id.ImgWeather);
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView1 = (RecyclerView) findViewById(R.id.recycleView1);
        recyclerView1.setHasFixedSize(true);

        ActivityCompat.requestPermissions(
                this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation();


        }

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);

                refresh.startAnimation(animation1);
                getLocation();
                search.setText("");
            }
        });
    }

    private void setupToolbarMenu() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.inflateMenu(R.menu.menu_main);

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.location:
                        getLocation();
                        break;
                }
                return true;
            }
        });

    }

    private void getSearchData(final String search) {
        final String currentWeatherurl = "http://api.openweathermap.org/data/2.5/weather?q=" + search + "&appid=6504cae6812684c9cb83e32bb5c0b09c";

        final Map<String, String> params = new HashMap<String, String>();
        params.put("weId", "1");
        CustomRequest user_request = new CustomRequest(Request.Method.GET, currentWeatherurl, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {

                    String status = response.getString("cod");
                  
                    if (status.equals("200")) {
                        currentWeather = new Weather(response.getString("name"),
                                response.getJSONObject("coord").getString("lon"),
                                response.getJSONObject("coord").getString("lat"),
                                response.getString("dt"),
                                response.getJSONObject("main").getString("temp_max"),
                                response.getJSONObject("main").getString("temp_min"),
                                response.getJSONObject("main").getString("temp"),
                                response.getJSONObject("main").getString("pressure"),
                                response.getJSONObject("main").getString("humidity"),
                                response.getJSONArray("weather").getJSONObject(0).getString("main"),
                                response.getJSONArray("weather").getJSONObject(0).getString("description"),
                                response.getJSONArray("weather").getJSONObject(0).getString("icon"),
                                response.getJSONObject("wind").getString("speed"),
                                response.getString("visibility"),
                                response.getJSONObject("sys").getString("country"),
                                response.getJSONObject("sys").getString("sunrise"),
                                response.getJSONObject("sys").getString("sunset")

                        );
                        latt = response.getJSONObject("coord").getString("lat");
                        longg = response.getJSONObject("coord").getString("lon");
                        date.setText(currentWeather.getDate());
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm");
                        String strDate = mdformat.format(calendar.getTime());
                        time.setText(strDate);

                        temp.setText(Html.fromHtml(currentWeather.getTemp() + "\u2103"));
                        env.setText(currentWeather.getDescription());
                        maxMin.setText("Max: " + currentWeather.getMaxTemp() + (char) 0x00B0 + " ~ Min: " + currentWeather.getMinTemp() + (char) 0x00B0);
                        //wind.setText(currentWeather.getWindSpeed());
                        Picasso.get()
                                .load("http://openweathermap.org/img/w/" + currentWeather.getIcon() + ".png")
                                .into(ivWeather);

                        mToolbar.setTitle(currentWeather.getCity() + ", " + currentWeather.getCountry());

                        gvParams.setAdapter(new GridViewAdapter(MainActivity.this, currentWeather, paramWeather));
                    } else {
                        Toast.makeText(MainActivity.this, "City not found", Toast.LENGTH_SHORT).show();
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

    private void getCurrentData(String lattitude, String longitude) {
        final String currentWeatherurl = "http://api.openweathermap.org/data/2.5/weather?lat=" + lattitude + "&lon=" + longitude + "&appid=6504cae6812684c9cb83e32bb5c0b09c";

        final Map<String, String> params = new HashMap<String, String>();
        params.put("weId", "1");
        CustomRequest user_request = new CustomRequest(Request.Method.GET, currentWeatherurl, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {

                    String status = response.getString("cod");
                    if (status.equals("200")) {
                        currentWeather = new Weather(response.getString("name"),
                                response.getJSONObject("coord").getString("lon"),
                                response.getJSONObject("coord").getString("lat"),
                                response.getString("dt"),
                                response.getJSONObject("main").getString("temp_max"),
                                response.getJSONObject("main").getString("temp_min"),
                                response.getJSONObject("main").getString("temp"),
                                response.getJSONObject("main").getString("pressure"),
                                response.getJSONObject("main").getString("humidity"),
                                response.getJSONArray("weather").getJSONObject(0).getString("main"),
                                response.getJSONArray("weather").getJSONObject(0).getString("description"),
                                response.getJSONArray("weather").getJSONObject(0).getString("icon"),
                                response.getJSONObject("wind").getString("speed"),
                                response.getString("visibility"),
                                response.getJSONObject("sys").getString("country"),
                                response.getJSONObject("sys").getString("sunrise"),
                                response.getJSONObject("sys").getString("sunset")

                        );

                        date.setText(currentWeather.getDate());
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm");
                        String strDate = mdformat.format(calendar.getTime());
                        time.setText(strDate);

                        temp.setText(Html.fromHtml(currentWeather.getTemp() + "\u2103"));
                        env.setText(currentWeather.getDescription());
                        maxMin.setText("Max: " + currentWeather.getMaxTemp() + (char) 0x00B0 + " ~ Min: " + currentWeather.getMinTemp() + (char) 0x00B0);
                        //wind.setText(currentWeather.getWindSpeed());
                        Picasso.get()
                                .load("http://openweathermap.org/img/w/" + currentWeather.getIcon() + ".png")
                                .into(ivWeather);

                        mToolbar.setTitle(currentWeather.getCity() + ", " + currentWeather.getCountry());

                        gvParams.setAdapter(new GridViewAdapter(MainActivity.this, currentWeather, paramWeather));
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


    private void getForecastHourlyData(String lattitude, String longitude) {
        String forecastWeatherurl = "http://api.openweathermap.org/data/2.5/forecast?lat=" + lattitude + "&lon=" + longitude + "&appid=6504cae6812684c9cb83e32bb5c0b09c";

        hourlytData = new ArrayList<>();
        hourlytData.clear();
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

                            );
                            hourlytData.add(listItem);
                            if (hourlytData.size() == 6) {
                                break;
                            }
                        }
                        adapter = new HourlyAdapter(MainActivity.this, hourlytData);
                        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 6));
                        recyclerView.setAdapter(adapter);

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

    private void getForecastWeeklyData(final String lattitude, final String longitude) {
        String forecastWeatherurl = "http://api.openweathermap.org/data/2.5/forecast?lat=" + lattitude + "&lon=" + longitude + "&appid=6504cae6812684c9cb83e32bb5c0b09c";

        weeklyData = new ArrayList<>();
        weeklyData.clear();
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
                                    , longitude
                                    , lattitude

                            );
                            String time = jobj.getString("dt_txt");
                            if (time.substring(11).equals("15:00:00")) {

                                weeklyData.add(listItem);
                            }

                        }
                        adapter = new WeeklyAdapter(MainActivity.this, weeklyData);
                        recyclerView1.setLayoutManager(new GridLayoutManager(MainActivity.this, 5));
                        recyclerView1.setAdapter(adapter);

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

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            Location location2 = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);
                getCurrentData(lattitude, longitude);
                getForecastHourlyData(lattitude, longitude);
                getForecastWeeklyData(lattitude, longitude);


            } else if (location1 != null) {
                double latti = location1.getLatitude();
                double longi = location1.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);
                getCurrentData(lattitude, longitude);
                getForecastHourlyData(lattitude, longitude);
                getForecastWeeklyData(lattitude, longitude);


            } else if (location2 != null) {
                double latti = location2.getLatitude();
                double longi = location2.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);
                getCurrentData(lattitude, longitude);
                getForecastHourlyData(lattitude, longitude);
                getForecastWeeklyData(lattitude, longitude);
            } else {

                Toast.makeText(this, "Unble to Trace your location", Toast.LENGTH_SHORT).show();

            }
        }
    }

    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, String[] permission,
            int[] grantResults) {

        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length > 0 &&
                    grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED) {


            }
        }
    }

    public void searchData(View view) {
        if (search.getText().toString().equals("")) {
            Toast.makeText(MainActivity.this, "Enter city name", Toast.LENGTH_SHORT).show();
        } else {
            getSearchData(search.getText().toString());
            getForecastHourlyData(latt, longg);
            getForecastWeeklyData(latt, longg);
        }
    }
}
