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
    Integer[] bgImgs = {R.drawable.homescreen_brady, R.drawable.homescreen_lebron, R.drawable.homescreen_conormcgregor, R.drawable.homescreen_aaronjudge, R.drawable.homescreen_zlatan};
    static ProgressButton progressButton;
    static ArrayList<_3_SliderItem> sliderItems = new ArrayList<>();
    static SQLiteDatabase database;


    public void setUpBackend(){
        try{
            Integer bradyLocation = Integer.valueOf(R.drawable.homescreen_brady);
            Integer lebronLocation = Integer.valueOf(R.drawable.homescreen_lebron);
            Integer mcgregorLocation = Integer.valueOf(R.drawable.homescreen_conormcgregor);
            Integer zlatanLocation = Integer.valueOf(R.drawable.homescreen_zlatan);
            Integer judgeLocation = Integer.valueOf(R.drawable.homescreen_aaronjudge);

           // this.deleteDatabase("Workouts");

            File dbFile = this.getDatabasePath("Workouts");

            if(!dbFile.exists()) {
                database = this.openOrCreateDatabase("Workouts",MODE_PRIVATE,null);
                database.execSQL("CREATE TABLE IF NOT EXISTS workouts (image INTEGER, athleteName VARCHAR, requiresPremium VARCHAR, isFavorite VARCHAR, tagNum INT(2),id INTEGER PRIMARY KEY)");
                database.execSQL("INSERT INTO workouts (image, athleteName, requiresPremium, isFavorite, tagNum) VALUES ('" + bradyLocation + "','Tom Brady','true', 'true', 0)");
                database.execSQL("INSERT INTO workouts (image, athleteName, requiresPremium, isFavorite, tagNum) VALUES ('" + lebronLocation + "','Lebron James','true', 'true', 1)");
                database.execSQL("INSERT INTO workouts (image, athleteName, requiresPremium, isFavorite, tagNum) VALUES ('" + mcgregorLocation + "','Connor McGregor','false', 'false',2)");
                database.execSQL("INSERT INTO workouts (image, athleteName, requiresPremium, isFavorite, tagNum) VALUES ('" + zlatanLocation + "','Zlatan  Irahim.','false', 'false',3)");
                database.execSQL("INSERT INTO workouts (image, athleteName, requiresPremium, isFavorite, tagNum) VALUES ('" + judgeLocation + "','Aaron Judge','false', 'false', 4)");
                Log.i("DB","DOESN'T Exist");
            }else{
                Log.i("DB","Exists");
                database = this.openOrCreateDatabase("Workouts",MODE_PRIVATE,null);

            }

                Cursor c = database.rawQuery("SELECT * FROM workouts",null);

                int imageIndex = c.getColumnIndex("image");
                int athleteNameIndex = c.getColumnIndex("athleteName");
                int requiresPremiumIndex = c.getColumnIndex("requiresPremium");
                int isFavoriteIndex = c.getColumnIndex("isFavorite");
                int tagNumIndex = c.getColumnIndex("tagNum");
                c.moveToFirst();

                while (!c.isAfterLast()){
                    _3_SliderItem item = new _3_SliderItem (c.getInt(imageIndex),c.getString(athleteNameIndex),Boolean.parseBoolean(c.getString(requiresPremiumIndex)),Boolean.parseBoolean(c.getString(isFavoriteIndex)),c.getInt(tagNumIndex));
                    Log.i("DB ITEM",String.valueOf(item.getIfRequiresPremium()));
                    sliderItems.add(item);

                    c.moveToNext();
                }


        }catch(Exception e){
            e.printStackTrace();
        }

        /*sliderItems.add(new _3_SliderItem(R.drawable.homescreen_brady,"Tom Brady",true,true,0));
        sliderItems.add(new _3_SliderItem(R.drawable.homescreen_lebron,"Lebron James",true,true,1));
        sliderItems.add(new _3_SliderItem(R.drawable.homescreen_conormcgregor,"Connor McGregor",false,true,2));
        sliderItems.add(new _3_SliderItem(R.drawable.homescreen_aaronjudge,"Aaron Judge",false,false,3));
        sliderItems.add(new _3_SliderItem(R.drawable.homescreen_zlatan,"Zlatan Ibrah.",true,false,4));*/
    }


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

    }

    @Override
    public void onClick(View v) {
        progressButton.buttonActivated();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setUpBackend();
                Intent intent = new Intent(getApplicationContext(), _Page3_SelectWorkout.class);
                startActivity(intent);
                finish();
            }
        }, 10);
    }
}
