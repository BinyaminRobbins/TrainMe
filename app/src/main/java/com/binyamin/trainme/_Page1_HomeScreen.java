package com.binyamin.trainme;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;

public class _Page1_HomeScreen extends AppCompatActivity implements View.OnClickListener {
    View myView;
    int arrayPos = 0;
    static Integer[] bgImgs = {R.drawable.homescreen_brady, R.drawable.homescreen_lebron, R.drawable.homescreen_conormcgregor, R.drawable.homescreen_zlatan, R.drawable.homescreen_aaronjudge,R.drawable.ronaldo_homescreen};
    static ProgressButton progressButton;
    boolean buttonPressed;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._1_homescreen_layout);

        /*deleteDatabase("Workouts");
        getSharedPreferences("com.binyamin.trainme", Context.MODE_PRIVATE).edit().clear().commit();*/
        sharedPreferences = getSharedPreferences("com.binyamin.trainme",Context.MODE_PRIVATE);

        myView = findViewById(R.id.include);
        myView.setOnClickListener(this);

        final Handler handler = new Handler();
        final int delay = 500; //milliseconds
        final ImageView imageView = findViewById(R.id.imageView);

        handler.postDelayed(new Runnable() {
            public void run() {
                //do something
                if (!buttonPressed) {
                    if (arrayPos < (bgImgs.length - 1)) {
                        arrayPos++;
                    } else {
                        arrayPos = 0;
                    }
                    imageView.setImageResource(bgImgs[arrayPos]);
                    handler.postDelayed(this, delay);
                }
            }
        }, delay);



        progressButton = new ProgressButton(_Page1_HomeScreen.this,myView);

    }

    @Override
    public void onClick(View v) {
        buttonPressed = true;
        progressButton.buttonActivated();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean hasOpenedBefore = sharedPreferences.getBoolean("hasOpenedBefore",false);
                Log.i("Has Opened Before",Boolean.toString(hasOpenedBefore));
                Intent intent;
                if(hasOpenedBefore){
                    intent = new Intent(getApplicationContext(), _Page3_SelectWorkout.class);
                }
                else {
                    intent = new Intent(getApplicationContext(), _Page2_UserDetails.class);
                }
                startActivity(intent);
                finish();
            }
        }, 100);
    }
}
