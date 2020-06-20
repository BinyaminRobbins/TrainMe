package com.binyamin.trainme;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.service.notification.StatusBarNotification;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class _Page1_HomeScreen extends AppCompatActivity implements View.OnClickListener {
    View myView;
    int arrayPos = 0;
    Integer[] bgImgs = {R.drawable.homescreen_brady, R.drawable.homescreen_lebron, R.drawable.homescreen_conormcgregor, R.drawable.homescreen_aaronjudge, R.drawable.homescreen_zlatan};
    static ProgressButton progressButton;
    static ArrayList<_3_SliderItem> sliderItems = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._1_homescreen_layout);

        final Handler handler = new Handler();
        final int delay = 1000; //milliseconds
        final ImageView imageView = findViewById(R.id.imageView);

        handler.postDelayed(new Runnable() {
            public void run() {
                //do something
                if (arrayPos < (bgImgs.length - 1)) {
                    arrayPos++;
                } else {
                    arrayPos = 0;
                }
                imageView.setImageResource(bgImgs[arrayPos]);
                handler.postDelayed(this, delay);
            }
        }, delay);


        myView = findViewById(R.id.include);
        myView.setOnClickListener(this);

        progressButton = new ProgressButton(_Page1_HomeScreen.this,myView);

        sliderItems.add(new _3_SliderItem(R.drawable.homescreen_brady,"Tom Brady",true,true));
        sliderItems.add(new _3_SliderItem(R.drawable.homescreen_lebron,"Lebron James",true,true));
        sliderItems.add(new _3_SliderItem(R.drawable.homescreen_conormcgregor,"Connor McGregor",false,true));
        sliderItems.add(new _3_SliderItem(R.drawable.homescreen_aaronjudge,"Aaron Judge",false,false));
        sliderItems.add(new _3_SliderItem(R.drawable.homescreen_zlatan,"Zlatan Ibrah.",true,false));


    }

    @Override
    public void onClick(View v) {
        progressButton.buttonActivated();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), _Page3_SelectWorkout.class);
                startActivity(intent);
                finish();
            }
        }, 300);

    }
}
