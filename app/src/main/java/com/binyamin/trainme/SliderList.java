package com.binyamin.trainme;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;

public class SliderList {
    private SharedPreferences sharedPreferences;
    Context context;
    private int bradyLocation,lebronLocation,mcgregorLocation,zlatanLocation,judgeLocation,ronaldoLocation, jamesHarrisonLocation, paulGeorgeLocation,jjwattLocation, dkmetcalfLocation, julianEdelmanLocation, jimmyButlerLocation, neymarLocation, antonioBrownLocation;
    private int bradyFavoriteLocation,lebronFavoriteLocation,mcgregorFavoriteLocation,zlatanFavoriteLocation,judgeFavoriteLocation,ronaldoFavoriteLocation, jamesHarrisonFavoriteLocation, paulGeorgeFavoritesLocation, jjwattFavoritesLocation, dkmetcalfFavoritesLocation,julianEdelmanFavoritesLocation, jimmyButlerFavoritesLocation, neymarFavoritesLocation,antonioBrownFavoritesLocation;

    public SliderList(Context context,SharedPreferences preferences){
        this.context = context;
        this.sharedPreferences = preferences;
    }

    void setUpDB(){
        SQLiteDatabase database = getDB();

        fillData();
        database.execSQL("CREATE TABLE IF NOT EXISTS workouts (image INTEGER,favoritesImage INTEGER, athleteName VARCHAR, requiresPremium VARCHAR, isFavorite VARCHAR, link VARCHAR, tagNum INT(2),id INTEGER PRIMARY KEY)");
    //Featured
        database.execSQL("INSERT INTO workouts (image,favoritesImage, athleteName, requiresPremium, isFavorite, link, tagNum) VALUES ('" + bradyLocation + "','"+bradyFavoriteLocation+"','Tom Brady','false', 'false','https://tb12sports.com/blog/tom-brady-workout', 0)");
        database.execSQL("INSERT INTO workouts (image,favoritesImage, athleteName, requiresPremium, isFavorite, link, tagNum) VALUES ('" + lebronLocation + "','"+lebronFavoriteLocation+"','Lebron James','false', 'false','https://www.jumpropedudes.com/workouts/lebron-james-body-breakdown', 1)");
        database.execSQL("INSERT INTO workouts (image,favoritesImage, athleteName, requiresPremium, isFavorite, link, tagNum) VALUES ('" + mcgregorLocation + "','"+mcgregorFavoriteLocation+"','Connor McGregor','false','false','https://manofmany.com/lifestyle/fitness/conor-mcgregor-diet-workout-plan',2)");
        //NBA
        database.execSQL("INSERT INTO workouts (image,favoritesImage, athleteName, requiresPremium, isFavorite, link, tagNum) VALUES ('" + paulGeorgeLocation + "','"+paulGeorgeFavoritesLocation+"','Paul George','true', 'false','https://www.health-yogi.com/paul-george-diet-plan-workout-routine/',3)");
        database.execSQL("INSERT INTO workouts (image,favoritesImage, athleteName, requiresPremium, isFavorite, link, tagNum) VALUES ('" + jimmyButlerLocation + "','"+jimmyButlerFavoritesLocation+"','Jimmy Butler','true', 'false','https://www.health-yogi.com/jimmy-butler-diet-plan-workout-routine/',4)");
        //NFL
        database.execSQL("INSERT INTO workouts (image,favoritesImage, athleteName, requiresPremium, isFavorite, link, tagNum) VALUES ('" + antonioBrownLocation + "','"+antonioBrownFavoritesLocation+"','Antonio Brown','true', 'false','https://fitnessclone.com/antonio-brown/',5)");
        database.execSQL("INSERT INTO workouts (image,favoritesImage, athleteName, requiresPremium, isFavorite, link, tagNum) VALUES ('" + dkmetcalfLocation + "','"+dkmetcalfFavoritesLocation+"','DK Metcalf','true', 'false','https://www.health-yogi.com/dk-metcalf/',6)");
        database.execSQL("INSERT INTO workouts (image,favoritesImage, athleteName, requiresPremium, isFavorite, link, tagNum) VALUES ('" + jamesHarrisonLocation + "','"+jamesHarrisonFavoriteLocation+"','James Harrison','true', 'false','https://www.borntoworkout.com/james-harrison-workout-routine-diet-plan-body-statistics/',7)");
        database.execSQL("INSERT INTO workouts (image,favoritesImage, athleteName, requiresPremium, isFavorite, link, tagNum) VALUES ('" + jjwattLocation + "','"+jjwattFavoritesLocation+"','J.J. Watt','true', 'false','https://fitnessclone.com/jj-watt/',8)");
        database.execSQL("INSERT INTO workouts (image,favoritesImage, athleteName, requiresPremium, isFavorite, link, tagNum) VALUES ('" + julianEdelmanLocation + "','"+julianEdelmanFavoritesLocation+"','Julian Edelman','true', 'false','https://s3.amazonaws.com/coachup_s3storage_production/app/public/landing_pages/downloads/Julian_Edelman_Receiver_Workout.pdf',9)");

        //Soccer
        database.execSQL("INSERT INTO workouts (image,favoritesImage, athleteName, requiresPremium, isFavorite, link, tagNum) VALUES ('" + ronaldoLocation + "','"+ronaldoFavoriteLocation+"','Cristiano Ronaldo','true', 'false','https://manofmany.com/lifestyle/fitness/cristiano-ronaldos-football-diet-workout-plan',10)");
        database.execSQL("INSERT INTO workouts (image,favoritesImage, athleteName, requiresPremium, isFavorite, link, tagNum) VALUES ('" + neymarLocation + "','"+neymarFavoritesLocation+"','Neymar','true', 'false','https://www.health-yogi.com/neymar/',11)");
        database.execSQL("INSERT INTO workouts (image,favoritesImage, athleteName, requiresPremium, isFavorite, link, tagNum) VALUES ('" + zlatanLocation + "','"+zlatanFavoriteLocation+"','Zlatan  Ibrahim.','true', 'false','https://www.fourfourtwo.com/performance/training/zlatan-workout',12)");
    //MLB
        database.execSQL("INSERT INTO workouts (image,favoritesImage, athleteName, requiresPremium, isFavorite, link, tagNum) VALUES ('" + judgeLocation + "','"+judgeFavoriteLocation+"','Aaron Judge','true', 'false','https://www.mensjournal.com/sports/8-ways-aaron-judge-trains-mlb-domination/6-yoga-and-pilates/',13)");
        //NHL
    //MMA

    //Diets Table
        database.execSQL("CREATE TABLE IF NOT EXISTS diets (image INTEGER, favoritesImage INTEGER,athleteName VARCHAR, requiresPremium VARCHAR, isFavorite VARCHAR, link VARCHAR, tagNum INT(2),id INTEGER PRIMARY KEY)");
        //featured
        database.execSQL("INSERT INTO diets (image,favoritesImage, athleteName, requiresPremium, isFavorite, link, tagNum) VALUES ('" + mcgregorLocation + "','"+ mcgregorFavoriteLocation +"','C. Mcgregor','false', 'false', 'https://manofmany.com/lifestyle/fitness/conor-mcgregor-diet-workout-plan',0)");
        database.execSQL("INSERT INTO diets (image,favoritesImage, athleteName, requiresPremium, isFavorite, link, tagNum) VALUES ('" + jjwattLocation + "','"+jjwattFavoritesLocation+"','J.J. Watt','false', 'false','https://fitnessclone.com/jj-watt/',1)");
        //Soccer
        database.execSQL("INSERT INTO diets (image,favoritesImage, athleteName, requiresPremium, isFavorite, link, tagNum) VALUES ('" + neymarLocation + "','"+neymarFavoritesLocation+"','Neymar','true', 'false','https://www.health-yogi.com/neymar/',2)");
        database.execSQL("INSERT INTO diets (image,favoritesImage, athleteName, requiresPremium, isFavorite, link, tagNum) VALUES ('" + ronaldoLocation + "','"+ronaldoFavoriteLocation+"','C. Ronaldo','true', 'false','https://manofmany.com/lifestyle/fitness/cristiano-ronaldos-football-diet-workout-plan', 3)");
        //Basketball
        database.execSQL("INSERT INTO diets (image,favoritesImage, athleteName, requiresPremium, isFavorite, link, tagNum) VALUES ('" + jimmyButlerLocation + "','"+jimmyButlerFavoritesLocation+"','Jimmy Butler','true', 'false','https://www.health-yogi.com/jimmy-butler-diet-plan-workout-routine/',4)");
        database.execSQL("INSERT INTO diets (image,favoritesImage, athleteName, requiresPremium, isFavorite, link, tagNum) VALUES ('" + paulGeorgeLocation + "','"+paulGeorgeFavoritesLocation+"','Paul George','true', 'false','https://www.health-yogi.com/paul-george-diet-plan-workout-routine/',5)");
        //UFC
        //Football
        database.execSQL("INSERT INTO diets (image,favoritesImage, athleteName, requiresPremium, isFavorite, link, tagNum) VALUES ('" + antonioBrownLocation + "','"+antonioBrownFavoritesLocation+"','Antonio Brown','true', 'false','https://fitnessclone.com/jj-watt/',6)");

        sharedPreferences.edit().putBoolean("hasOpenedBefore",true).apply();
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
        julianEdelmanLocation = R.drawable.homescreen_julianedelman;
        jimmyButlerLocation = R.drawable.homescreen_jimmybutler;
        neymarLocation = R.drawable.homescreen_neymar;
        antonioBrownLocation = R.drawable.homescreen_antoniobrown;
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
        julianEdelmanFavoritesLocation = R.drawable.edelman_favorites;
        jimmyButlerFavoritesLocation = R.drawable.jimmybutler_favorites;
        neymarFavoritesLocation = R.drawable.neymar_favorites;
        antonioBrownFavoritesLocation = R.drawable.antoniobrown_favorites;
    }


    ArrayList<_3_SliderItem> getWorkoutList(){

        ArrayList<_3_SliderItem> workoutItems = new ArrayList<>();
        workoutItems.clear();

            Cursor c = getDB().rawQuery("SELECT * FROM workouts",null);

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
            c.close();
        return workoutItems;
    }
    ArrayList<_3_SliderItem> getDietList(){
        Log.i("TAG","Diet Called");
        ArrayList<_3_SliderItem> dietItems = new ArrayList<>();
        dietItems.clear();
            Cursor c = getDB().rawQuery("SELECT * FROM diets",null);


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
        c.close();
        return dietItems;
    }
}

