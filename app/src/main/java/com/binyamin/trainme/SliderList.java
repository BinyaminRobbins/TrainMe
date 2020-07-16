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
                int bradyLocation = R.drawable.homescreen_brady; int bradyFavoriteLocation = R.drawable.brady_favorites;
                int lebronLocation = R.drawable.homescreen_lebron; int lebronFavoriteLocation = R.drawable.lebron_favorites;
                int mcgregorLocation = R.drawable.homescreen_conormcgregor; int mcgregorFavoriteLocation = R.drawable.mcgregor_favorites;
                int zlatanLocation = R.drawable.homescreen_zlatan; int zlatanFavoriteLocation = R.drawable.zlatan_favorites;
                int judgeLocation = R.drawable.homescreen_aaronjudge; int judgeFavoriteLocation = R.drawable.judge_favorites;
                int ronaldoLocation = R.drawable.ronaldo_homescreen; int ronaldoFavoriteLocation = R.drawable.ronaldo_favorites;

                this.database.execSQL("CREATE TABLE IF NOT EXISTS workouts (image INTEGER,favoritesImage INTEGER, athleteName VARCHAR, requiresPremium VARCHAR, isFavorite VARCHAR, tagNum INT(2),id INTEGER PRIMARY KEY)");
                this.database.execSQL("INSERT INTO workouts (image,favoritesImage, athleteName, requiresPremium, isFavorite, tagNum) VALUES ('" + bradyLocation + "','"+bradyFavoriteLocation+"','Tom Brady','false', 'false', 0)");
                this.database.execSQL("INSERT INTO workouts (image,favoritesImage, athleteName, requiresPremium, isFavorite, tagNum) VALUES ('" + lebronLocation + "','"+lebronFavoriteLocation+"','Lebron James','false', 'false', 1)");
                this.database.execSQL("INSERT INTO workouts (image,favoritesImage, athleteName, requiresPremium, isFavorite, tagNum) VALUES ('" + mcgregorLocation + "','"+mcgregorFavoriteLocation+"','Connor McGregor','false', 'false',2)");
                this.database.execSQL("INSERT INTO workouts (image,favoritesImage, athleteName, requiresPremium, isFavorite, tagNum) VALUES ('" + zlatanLocation + "','"+zlatanFavoriteLocation+"','Zlatan  Ibrahim.','true', 'false',3)");
                this.database.execSQL("INSERT INTO workouts (image,favoritesImage, athleteName, requiresPremium, isFavorite, tagNum) VALUES ('" + judgeLocation + "','"+judgeFavoriteLocation+"','Aaron Judge','true', 'false',4)");
                this.database.execSQL("INSERT INTO workouts (image,favoritesImage, athleteName, requiresPremium, isFavorite, tagNum) VALUES ('" + ronaldoLocation + "','"+ronaldoFavoriteLocation+"','Cristiano Ronaldo','true', 'false',5)");
                sharedPreferences.edit().putInt("openedBefore",1).commit();
            }else{
                Log.i("DB","Exists");
            }

            Cursor c = this.database.rawQuery("SELECT * FROM workouts",null);

            int imageIndex = c.getColumnIndex("image");
            int favoritesImageIndex = c.getColumnIndex("favoritesImage");
            int athleteNameIndex = c.getColumnIndex("athleteName");
            int requiresPremiumIndex = c.getColumnIndex("requiresPremium");
            int isFavoriteIndex = c.getColumnIndex("isFavorite");
            int tagNumIndex = c.getColumnIndex("tagNum");
            c.moveToFirst();

            while (!c.isAfterLast()){
                _3_SliderItem item = new _3_SliderItem (c.getInt(imageIndex),c.getInt(favoritesImageIndex),c.getString(athleteNameIndex),Boolean.parseBoolean(c.getString(requiresPremiumIndex)),Boolean.parseBoolean(c.getString(isFavoriteIndex)),c.getInt(tagNumIndex));
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

