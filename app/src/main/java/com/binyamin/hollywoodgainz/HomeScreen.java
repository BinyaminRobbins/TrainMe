package com.binyamin.hollywoodgainz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

public class HomeScreen extends AppCompatActivity {
    View myView;
    SharedPreferences sharedPreferences;
    int arrayPos = 0;
    int[] bgImgs = { R.drawable.starlord, R.drawable.tom_brady, R.drawable.zlatan, R.drawable.aaron_judge, R.drawable.lebron, R.drawable.conor_mcgregor };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Handler handler = new Handler();
        final int delay = 1000; //milliseconds
        final ImageView imageView = findViewById(R.id.imageView);

        handler.postDelayed(new Runnable(){
            public void run(){
                //do something
                if(arrayPos < (bgImgs.length - 1)){
                    arrayPos ++;
                }else{
                    arrayPos = 0;
                }
                imageView.setImageResource(bgImgs[arrayPos]);
                handler.postDelayed(this, delay);
            }
        }, delay);

        sharedPreferences = this.getSharedPreferences("com.binyamin.hollywoodgainz",Context.MODE_PRIVATE);

        myView = findViewById(R.id.include);
        myView.setOnClickListener(new View.OnClickListener() {
            final ProgressButton progressButton = new ProgressButton(HomeScreen.this,myView);
            @Override
            public void onClick(View v) {
                progressButton.buttonActivated();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), Select_A_Workout.class);
                        startActivity(intent);
                    }
                },300);

            }
        });



    }
}
