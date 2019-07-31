package com.example.a300269668.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    TextView splashProgreeBar, weather, zone;
    ImageView cloudImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashProgreeBar = (TextView) findViewById(R.id.txtProgress);
        weather = (TextView) findViewById(R.id.weather);
        zone = (TextView) findViewById(R.id.zone);
        cloudImg = (ImageView) findViewById(R.id.imgCloud);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide);

        weather.startAnimation(animation);
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide1);

        zone.startAnimation(animation1);
        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_cloud);

        cloudImg.startAnimation(animation2);

        new Thread(new Runnable() {

            public void run() {
                int i = 0;
                while (i < 100) {
                    SystemClock.sleep(18);
                    i++;
                    final int curCount = i;
                    if (curCount % 5 == 0) {
                        //update UI with progress every 5%
                        splashProgreeBar.post(new Runnable() {
                            public void run() {
                                //splashProgreeBar.setText(curCount + "%");
                            }
                        });
                    }
                }
                splashProgreeBar.post(new Runnable() {
                    public void run() {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    }
                });
            }
        }).start();
    }
}