package com.binyamin.trainme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class _Page1_HomeScreen extends AppCompatActivity {
    View myView;
    SharedPreferences sharedPreferences;
    int arrayPos = 0;
    int[] bgImgs = {R.drawable.homescreen_brady, R.drawable.homescreen_zlatan, R.drawable.homescreen_aaronjudge, R.drawable.homescreen_lebron, R.drawable.homescreen_conormcgregor};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._1_homescreen_layout);

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


        myView = findViewById(R.id.include);
        myView.setOnClickListener(new View.OnClickListener() {
             final ProgressButton progressButton = new ProgressButton(_Page1_HomeScreen.this,myView);
            @Override
            public void onClick(View v) {
                progressButton.buttonActivated();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), _Page2_UserDetails.class);
                        startActivity(intent);
                        try {
                            Thread.sleep(500);
                            progressButton.endAnimation();
                        }catch(Exception e){
                            e.printStackTrace();

                        }
                    }
                },300);


            }
        });



    }
}
