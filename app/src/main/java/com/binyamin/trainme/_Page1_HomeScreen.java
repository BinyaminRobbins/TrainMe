package com.binyamin.trainme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class _Page1_HomeScreen extends AppCompatActivity implements View.OnClickListener {
    View myView;
    int arrayPos = 0;
    static Integer[] bgImgs = {R.drawable.homescreen_brady,R.drawable.homescreen_lebron, R.drawable.homescreen_conormcgregor, R.drawable.homescreen_zlatan, R.drawable.homepage_paulgeorge,R.drawable.ronaldo_homescreen};
    static ProgressButton progressButton;
    boolean buttonPressed;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._1_homescreen_layout);

        sharedPreferences = this.getSharedPreferences("com.binyamin.trainme",Context.MODE_PRIVATE);
        if(!sharedPreferences.getBoolean("hasOpenedBefore",false)){
            try {
                deleteDatabase("TrainMeDatabase");
            }catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

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
        progressButton.buttonActivated();
        buttonPressed = true;
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean hasOpenedBefore = sharedPreferences.getBoolean("hasOpenedBefore",false);
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
