package com.binyamin.trainme;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class SliderList {
    SQLiteDatabase database;
    SharedPreferences sharedPreferences;

    public SliderList(SQLiteDatabase db){
        this.database = db;
    }

    public ArrayList<_3_SliderItem> getSliderList(){
        ArrayList<_3_SliderItem> sliderItems = new ArrayList<>();
        sliderItems.clear();

        sharedPreferences = _Page3_SelectWorkout.context.getSharedPreferences("com.binyamin.trainme", MODE_PRIVATE);
        int openedBefore = sharedPreferences.getInt("openedBefore",-1);


        try{
            //_Page3_SelectWorkout.context.deleteDatabase("Workouts");
            if(openedBefore == -1) {
                Log.i("DB","DOESN'T Exist");
                Integer bradyLocation = Integer.valueOf(R.drawable.homescreen_brady);
                Integer lebronLocation = Integer.valueOf(R.drawable.homescreen_lebron);
                Integer mcgregorLocation = Integer.valueOf(R.drawable.homescreen_conormcgregor);
                Integer zlatanLocation = Integer.valueOf(R.drawable.homescreen_zlatan);
                Integer judgeLocation = Integer.valueOf(R.drawable.homescreen_aaronjudge);

                this.database.execSQL("CREATE TABLE IF NOT EXISTS workouts (image INTEGER, athleteName VARCHAR, requiresPremium VARCHAR, isFavorite VARCHAR, tagNum INT(2),id INTEGER PRIMARY KEY)");
                this.database.execSQL("INSERT INTO workouts (image, athleteName, requiresPremium, isFavorite, tagNum) VALUES ('" + bradyLocation + "','Tom Brady','false', 'false', 0)");
                this.database.execSQL("INSERT INTO workouts (image, athleteName, requiresPremium, isFavorite, tagNum) VALUES ('" + lebronLocation + "','Lebron James','true', 'false', 1)");
                this.database.execSQL("INSERT INTO workouts (image, athleteName, requiresPremium, isFavorite, tagNum) VALUES ('" + mcgregorLocation + "','Connor McGregor','false', 'false',2)");
                this.database.execSQL("INSERT INTO workouts (image, athleteName, requiresPremium, isFavorite, tagNum) VALUES ('" + zlatanLocation + "','Zlatan  Ibrahim.','false', 'false',3)");
                this.database.execSQL("INSERT INTO workouts (image, athleteName, requiresPremium, isFavorite, tagNum) VALUES ('" + judgeLocation + "','Aaron Judge','false', 'false',4)");
                sharedPreferences.edit().putInt("openedBefore",1).commit();
            }else{
                Log.i("DB","Exists");
            }

            Cursor c = this.database.rawQuery("SELECT * FROM workouts",null);

            int imageIndex = c.getColumnIndex("image");
            int athleteNameIndex = c.getColumnIndex("athleteName");
            int requiresPremiumIndex = c.getColumnIndex("requiresPremium");
            int isFavoriteIndex = c.getColumnIndex("isFavorite");
            int tagNumIndex = c.getColumnIndex("tagNum");
            c.moveToFirst();

            while (!c.isAfterLast()){
                _3_SliderItem item = new _3_SliderItem (c.getInt(imageIndex),c.getString(athleteNameIndex),Boolean.parseBoolean(c.getString(requiresPremiumIndex)),Boolean.parseBoolean(c.getString(isFavoriteIndex)),c.getInt(tagNumIndex));
                Log.i("DB ITEM",c.getString(isFavoriteIndex));
                sliderItems.add(item);

                c.moveToNext();
            }


        }catch(Exception e){
            e.printStackTrace();
            Log.i("ERROR","ERROR");
        }

        return sliderItems;

    }
}

