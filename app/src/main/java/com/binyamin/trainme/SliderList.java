package com.binyamin.trainme;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;

public class SliderList {
    private SharedPreferences sharedPreferences;
    Context context;
    private int image;
    private int bradyLocation,lebronLocation,mcgregorLocation,zlatanLocation,judgeLocation,ronaldoLocation, jamesHarrisonLocation, paulGeorgeLocation,jjwattLocation, dkmetcalfLocation;
    private int bradyFavoriteLocation,lebronFavoriteLocation,mcgregorFavoriteLocation,zlatanFavoriteLocation,judgeFavoriteLocation,ronaldoFavoriteLocation, jamesHarrisonFavoriteLocation, paulGeorgeFavoritesLocation, jjwattFavoritesLocation, dkmetcalfFavoritesLocation;

    public SliderList(Context context,SharedPreferences preferences){
        this.context = context;
        this.sharedPreferences = preferences;
    }

    void setUpDB(){
        SQLiteDatabase database = getDB();

        fillData();
        int i = 0;
        database.execSQL("CREATE TABLE IF NOT EXISTS workouts (image INTEGER,favoritesImage INTEGER, athleteName VARCHAR, requiresPremium VARCHAR, isFavorite VARCHAR, link VARCHAR, tagNum INT(2),id INTEGER PRIMARY KEY)");
    //Featured
        database.execSQL("INSERT INTO workouts (image,favoritesImage, athleteName, requiresPremium, isFavorite, link, tagNum) VALUES ('" + bradyLocation + "','"+bradyFavoriteLocation+"','Tom Brady','false', 'false','https://tb12sports.com/blog/tom-brady-workout', '"+i+"')");
        database.execSQL("INSERT INTO workouts (image,favoritesImage, athleteName, requiresPremium, isFavorite, link, tagNum) VALUES ('" + lebronLocation + "','"+lebronFavoriteLocation+"','Lebron James','false', 'false','https://www.jumpropedudes.com/workouts/lebron-james-body-breakdown', '"+i+++"')");
        database.execSQL("INSERT INTO workouts (image,favoritesImage, athleteName, requiresPremium, isFavorite, link, tagNum) VALUES ('" + mcgregorLocation + "','"+mcgregorFavoriteLocation+"','Connor McGregor','false','https://manofmany.com/lifestyle/fitness/conor-mcgregor-diet-workout-plan', 'false','"+i+++"')");
    //NBA
        database.execSQL("INSERT INTO workouts (image,favoritesImage, athleteName, requiresPremium, isFavorite, link, tagNum) VALUES ('" + paulGeorgeLocation + "','"+paulGeorgeFavoritesLocation+"','Paul George','true', 'false','https://www.health-yogi.com/paul-george-diet-plan-workout-routine/','"+i+++"')");
    //NFL
        database.execSQL("INSERT INTO workouts (image,favoritesImage, athleteName, requiresPremium, isFavorite, link, tagNum) VALUES ('" + jamesHarrisonLocation + "','"+jamesHarrisonFavoriteLocation+"','James Harrison','true', 'false','https://www.borntoworkout.com/james-harrison-workout-routine-diet-plan-body-statistics/','"+i+++"')");
        database.execSQL("INSERT INTO workouts (image,favoritesImage, athleteName, requiresPremium, isFavorite, link, tagNum) VALUES ('" + jjwattLocation + "','"+jjwattFavoritesLocation+"','J.J. Watt','true', 'false','https://fitnessclone.com/jj-watt/','"+i+++"')");
        database.execSQL("INSERT INTO workouts (image,favoritesImage, athleteName, requiresPremium, isFavorite, link, tagNum) VALUES ('" + dkmetcalfLocation + "','"+dkmetcalfFavoritesLocation+"','DK Metcalf','true', 'false','https://www.health-yogi.com/dk-metcalf/','"+i+++"')");
    //Soccer
        database.execSQL("INSERT INTO workouts (image,favoritesImage, athleteName, requiresPremium, isFavorite, link, tagNum) VALUES ('" + zlatanLocation + "','"+zlatanFavoriteLocation+"','Zlatan  Ibrahim.','true', 'false','https://www.fourfourtwo.com/performance/training/zlatan-workout','"+i+++"')");
        database.execSQL("INSERT INTO workouts (image,favoritesImage, athleteName, requiresPremium, isFavorite, link, tagNum) VALUES ('" + ronaldoLocation + "','"+ronaldoFavoriteLocation+"','Cristiano Ronaldo','true', 'false','https://manofmany.com/lifestyle/fitness/cristiano-ronaldos-football-diet-workout-plan','"+i+++"')");
    //MLB
        database.execSQL("INSERT INTO workouts (image,favoritesImage, athleteName, requiresPremium, isFavorite, link, tagNum) VALUES ('" + judgeLocation + "','"+judgeFavoriteLocation+"','Aaron Judge','true', 'false','https://www.mensjournal.com/sports/8-ways-aaron-judge-trains-mlb-domination/6-yoga-and-pilates/','"+i+++"')");
        //NHL
    //MMA

    //Diets Table
        i = 0;
        database.execSQL("CREATE TABLE IF NOT EXISTS diets (image INTEGER, favoritesImage INTEGER,athleteName VARCHAR, requiresPremium VARCHAR, isFavorite VARCHAR, link VARCHAR, tagNum INT(2),id INTEGER PRIMARY KEY)");
        database.execSQL("INSERT INTO diets (image,favoritesImage, athleteName, requiresPremium, isFavorite, link, tagNum) VALUES ('" + ronaldoLocation + "','"+ronaldoFavoriteLocation+"','C. Ronaldo','false', 'false','https://manofmany.com/lifestyle/fitness/cristiano-ronaldos-football-diet-workout-plan', '"+i+"')");
        database.execSQL("INSERT INTO diets (image,favoritesImage, athleteName, requiresPremium, isFavorite, link, tagNum) VALUES ('" + mcgregorLocation + "','"+ mcgregorFavoriteLocation +"','C. Mcgregor','false', 'false', 'https://manofmany.com/lifestyle/fitness/conor-mcgregor-diet-workout-plan','"+i+++"')");
        database.execSQL("INSERT INTO diets (image,favoritesImage, athleteName, requiresPremium, isFavorite, link, tagNum) VALUES ('" + jjwattLocation + "','"+jjwattFavoritesLocation+"','J.J. Watt','false', 'false','https://fitnessclone.com/jj-watt/','"+i+++"')");
        database.execSQL("INSERT INTO diets (image,favoritesImage, athleteName, requiresPremium, isFavorite, link, tagNum) VALUES ('" + image + "','"+image+"','Diet Plan#1','true', 'false','DietLink','"+i+++"')");
        database.execSQL("INSERT INTO diets (image,favoritesImage, athleteName, requiresPremium, isFavorite, link, tagNum) VALUES ('" + image + "','"+image+"','Diet Plan#2','true', 'false','DietLink','"+i+++"')");
        database.execSQL("INSERT INTO diets (image,favoritesImage, athleteName, requiresPremium, isFavorite, link, tagNum) VALUES ('" + image + "','"+image+"','Diet Plan#3','true', 'false','DietLink','"+i+++"')");

        sharedPreferences.edit().putInt("openedBefore",1).apply();
    }

    SQLiteDatabase getDB() {
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
        jamesHarrisonLocation = R.drawable.homescreen_jamesharrison;
        paulGeorgeLocation = R.drawable.homepage_paulgeorge;
        jjwattLocation = R.drawable.jjwatt_homescreen;
        dkmetcalfLocation = R.drawable.homescreen_dkmetcalf;
        //Athlete Favorites Page Image Locations:
        bradyFavoriteLocation = R.drawable.brady_favorites;
        lebronFavoriteLocation = R.drawable.lebron_favorites;
        mcgregorFavoriteLocation = R.drawable.mcgregor_favorites;
        zlatanFavoriteLocation = R.drawable.zlatan_favorites;
        judgeFavoriteLocation = R.drawable.judge_favorites;
        ronaldoFavoriteLocation = R.drawable.ronaldo_favorites;
        jamesHarrisonFavoriteLocation = R.drawable.jamesharrison_favorites;
        paulGeorgeFavoritesLocation = R.drawable.paulgeorge_favorites;
        jjwattFavoritesLocation = R.drawable.jjwatt_favorites;
        dkmetcalfFavoritesLocation = R.drawable.dkmetcalf_favorites;

        //Diet Images
        image = R.drawable.healthy_diet;
    }


    ArrayList<_3_SliderItem> getWorkoutList(){

        ArrayList<_3_SliderItem> workoutItems = new ArrayList<>();
        workoutItems.clear();

            @SuppressLint("Recycle") Cursor c = getDB().rawQuery("SELECT * FROM workouts",null);

            int imageIndex = c.getColumnIndex("image");
            int favoritesImageIndex = c.getColumnIndex("favoritesImage");
            int athleteNameIndex = c.getColumnIndex("athleteName");
            int requiresPremiumIndex = c.getColumnIndex("requiresPremium");
            int isFavoriteIndex = c.getColumnIndex("isFavorite");
            int linkIndex = c.getColumnIndex("link");
            int tagNumIndex = c.getColumnIndex("tagNum");
            c.moveToFirst();

            while (!c.isAfterLast()){
                _3_SliderItem item = new _3_SliderItem (c.getInt(imageIndex),c.getInt(favoritesImageIndex),c.getString(athleteNameIndex),Boolean.parseBoolean(c.getString(requiresPremiumIndex)),Boolean.parseBoolean(c.getString(isFavoriteIndex)),c.getString(linkIndex),c.getInt(tagNumIndex));
                workoutItems.add(item);

                c.moveToNext();
            }
        return workoutItems;
    }
    ArrayList<_3_SliderItem> getDietList(){
        Log.i("TAG","Diet Called");
        ArrayList<_3_SliderItem> dietItems = new ArrayList<>();
        dietItems.clear();
            @SuppressLint("Recycle") Cursor c = getDB().rawQuery("SELECT * FROM diets",null);

            int imageIndex = c.getColumnIndex("image");
            int favoritesImageIndex = c.getColumnIndex("favoritesImage");
            int athleteNameIndex = c.getColumnIndex("athleteName");
            int requiresPremiumIndex = c.getColumnIndex("requiresPremium");
            int isFavoriteIndex = c.getColumnIndex("isFavorite");
            int linkIndex = c.getColumnIndex("link");
            int tagNumIndex = c.getColumnIndex("tagNum");
            c.moveToFirst();

            while (!c.isAfterLast()){
                _3_SliderItem item = new _3_SliderItem (c.getInt(imageIndex),c.getInt(favoritesImageIndex),c.getString(athleteNameIndex),Boolean.parseBoolean(c.getString(requiresPremiumIndex)),Boolean.parseBoolean(c.getString(isFavoriteIndex)),c.getString(linkIndex),c.getInt(tagNumIndex));
                dietItems.add(item);
                c.moveToNext();
            }
        return dietItems;
    }
}

