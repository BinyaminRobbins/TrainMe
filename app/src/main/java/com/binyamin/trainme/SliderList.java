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
    SharedPreferences sharedPreferences;
    Context context;
    int image;
    int bradyLocation,lebronLocation,mcgregorLocation,zlatanLocation,judgeLocation,ronaldoLocation;
    int bradyFavoriteLocation,lebronFavoriteLocation,mcgregorFavoriteLocation,zlatanFavoriteLocation,judgeFavoriteLocation,ronaldoFavoriteLocation;

    public SliderList(Context context,SharedPreferences preferences){
        this.context = context;
        this.sharedPreferences = preferences;
    }

    public void setUpDB(){
        SQLiteDatabase database = getDB();

        fillData();

        database.execSQL("CREATE TABLE IF NOT EXISTS workouts (image INTEGER,favoritesImage INTEGER, athleteName VARCHAR, requiresPremium VARCHAR, isFavorite VARCHAR, tagNum INT(2),id INTEGER PRIMARY KEY)");
        database.execSQL("INSERT INTO workouts (image,favoritesImage, athleteName, requiresPremium, isFavorite, tagNum) VALUES ('" + bradyLocation + "','"+bradyFavoriteLocation+"','Tom Brady','false', 'false', 0)");
        database.execSQL("INSERT INTO workouts (image,favoritesImage, athleteName, requiresPremium, isFavorite, tagNum) VALUES ('" + lebronLocation + "','"+lebronFavoriteLocation+"','Lebron James','false', 'false', 1)");
        database.execSQL("INSERT INTO workouts (image,favoritesImage, athleteName, requiresPremium, isFavorite, tagNum) VALUES ('" + mcgregorLocation + "','"+mcgregorFavoriteLocation+"','Connor McGregor','false', 'false',2)");
        database.execSQL("INSERT INTO workouts (image,favoritesImage, athleteName, requiresPremium, isFavorite, tagNum) VALUES ('" + zlatanLocation + "','"+zlatanFavoriteLocation+"','Zlatan  Ibrahim.','true', 'false',3)");
        database.execSQL("INSERT INTO workouts (image,favoritesImage, athleteName, requiresPremium, isFavorite, tagNum) VALUES ('" + judgeLocation + "','"+judgeFavoriteLocation+"','Aaron Judge','true', 'false',4)");
        database.execSQL("INSERT INTO workouts (image,favoritesImage, athleteName, requiresPremium, isFavorite, tagNum) VALUES ('" + ronaldoLocation + "','"+ronaldoFavoriteLocation+"','Cristiano Ronaldo','true', 'false',5)");

        database.execSQL("CREATE TABLE IF NOT EXISTS diets (image INTEGER, favoritesImage INTEGER,athleteName VARCHAR, requiresPremium VARCHAR, isFavorite VARCHAR, tagNum INT(2),id INTEGER PRIMARY KEY)");
        database.execSQL("INSERT INTO diets (image,favoritesImage, athleteName, requiresPremium, isFavorite, tagNum) VALUES ('" + ronaldoLocation + "','"+ronaldoFavoriteLocation+"','C. Ronaldo','false', 'false', 0)");
        database.execSQL("INSERT INTO diets (image,favoritesImage, athleteName, requiresPremium, isFavorite, tagNum) VALUES ('" + image + "','"+image+"','C. Mcgregor','false', 'false', 1)");
        database.execSQL("INSERT INTO diets (image,favoritesImage, athleteName, requiresPremium, isFavorite, tagNum) VALUES ('" + image + "','"+image+"','Diet Plan#0','false', 'false',2)");
        database.execSQL("INSERT INTO diets (image,favoritesImage, athleteName, requiresPremium, isFavorite, tagNum) VALUES ('" + image + "','"+image+"','Diet Plan#1','true', 'false',3)");
        database.execSQL("INSERT INTO diets (image,favoritesImage, athleteName, requiresPremium, isFavorite, tagNum) VALUES ('" + image + "','"+image+"','Diet Plan#2','true', 'false',4)");
        database.execSQL("INSERT INTO diets (image,favoritesImage, athleteName, requiresPremium, isFavorite, tagNum) VALUES ('" + image + "','"+image+"','Diet Plan#3','true', 'false',5)");

        sharedPreferences.edit().putInt("openedBefore",1).apply();
    }

    public SQLiteDatabase getDB() {
        return context.openOrCreateDatabase(context.getString(R.string.dbName),Context.MODE_PRIVATE,null);
    }

    private void fillData(){
        //Athlete Image Locations:
        bradyLocation = R.drawable.homescreen_brady;
        lebronLocation = R.drawable.homescreen_lebron;
        mcgregorLocation = R.drawable.homescreen_conormcgregor;
        zlatanLocation = R.drawable.homescreen_zlatan;
        judgeLocation = R.drawable.homescreen_aaronjudge;
        ronaldoLocation = R.drawable.ronaldo_homescreen;
        //Athlete Favorites Page Image Locations:
        bradyFavoriteLocation = R.drawable.brady_favorites;
        lebronFavoriteLocation = R.drawable.lebron_favorites;
        mcgregorFavoriteLocation = R.drawable.mcgregor_favorites;
        zlatanFavoriteLocation = R.drawable.zlatan_favorites;
        judgeFavoriteLocation = R.drawable.judge_favorites;
        ronaldoFavoriteLocation = R.drawable.ronaldo_favorites;
        //Diet Images
        image = R.drawable.healthy_diet;
    }


    public ArrayList<_3_SliderItem> getWorkoutList(){

        ArrayList<_3_SliderItem> workoutItems = new ArrayList<>();
        workoutItems.clear();

            Cursor c = getDB().rawQuery("SELECT * FROM workouts",null);

            int imageIndex = c.getColumnIndex("image");
            int favoritesImageIndex = c.getColumnIndex("favoritesImage");
            int athleteNameIndex = c.getColumnIndex("athleteName");
            int requiresPremiumIndex = c.getColumnIndex("requiresPremium");
            int isFavoriteIndex = c.getColumnIndex("isFavorite");
            int tagNumIndex = c.getColumnIndex("tagNum");
            c.moveToFirst();

            while (!c.isAfterLast()){
                _3_SliderItem item = new _3_SliderItem (c.getInt(imageIndex),c.getInt(favoritesImageIndex),c.getString(athleteNameIndex),Boolean.parseBoolean(c.getString(requiresPremiumIndex)),Boolean.parseBoolean(c.getString(isFavoriteIndex)),c.getInt(tagNumIndex));
                workoutItems.add(item);

                c.moveToNext();
            }
        return workoutItems;
    }
    public ArrayList<_3_SliderItem> getDietList(){
        Log.i("TAG","Diet Called");
        ArrayList<_3_SliderItem> dietItems = new ArrayList<>();
        dietItems.clear();
            Cursor c = getDB().rawQuery("SELECT * FROM diets",null);

            int imageIndex = c.getColumnIndex("image");
            int favoritesImageIndex = c.getColumnIndex("favoritesImage");
            int athleteNameIndex = c.getColumnIndex("athleteName");
            int requiresPremiumIndex = c.getColumnIndex("requiresPremium");
            int isFavoriteIndex = c.getColumnIndex("isFavorite");
            int tagNumIndex = c.getColumnIndex("tagNum");
            c.moveToFirst();

            while (!c.isAfterLast()){
                _3_SliderItem item = new _3_SliderItem (c.getInt(imageIndex),c.getInt(favoritesImageIndex),c.getString(athleteNameIndex),Boolean.parseBoolean(c.getString(requiresPremiumIndex)),Boolean.parseBoolean(c.getString(isFavoriteIndex)),c.getInt(tagNumIndex));
                dietItems.add(item);
                c.moveToNext();
            }
        return dietItems;
    }
}

