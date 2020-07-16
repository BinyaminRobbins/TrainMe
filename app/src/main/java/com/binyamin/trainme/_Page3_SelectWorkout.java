package com.binyamin.trainme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class _Page3_SelectWorkout extends AppCompatActivity implements Runnable{
    int backButtonCount;
    static Context context;
    private Thread t;
    static ArrayList<AllWorkouts> allAthleteWorkouts = new ArrayList<>();
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    SharedPreferences sharedPreferences;
    Handler sliderHandler;
    static NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._3_activity_select_workout);

        context = getApplicationContext();
        sharedPreferences = getSharedPreferences("com.binyamin.trainme",Context.MODE_PRIVATE);

        t = new Thread(this,"DefineAthleteWorkouts");
        t.run();

       Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        backButtonCount = 0;

        navigationView = findViewById(R.id.navigationView);
        navigationView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        navController = Navigation.findNavController(this, R.id.navHostFragment);
        NavigationUI.setupWithNavController(navigationView,navController);

        drawerLayout = findViewById(R.id.drawerLayout);

        sliderHandler = new Handler();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        backButtonCount = 0;
        t = new Thread(this,"DeclareWorkouts");
        t.run();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if(backButtonCount >= 1)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Press Again to Close Application", Toast.LENGTH_SHORT).show();
            final Handler handler = new Handler();
            final int delay = 3000; //milliseconds (x1000 for seconds)
            handler.postDelayed(new Runnable(){
                public void run(){
                    backButtonCount = 0;
                }
            }, delay);
            backButtonCount++;
        }
    }
//Categories include:
    //Mixed
    //FullBody
    //BodyWeight
    //UpperBody
        //Chest
        //Arms
        //Back
    //LowerBody
        //Legs
        //Glutes
    //Cardio
    //Stretching
    @Override
    public void run() {
        if (t.getName() == "DeclareWorkouts") {
            allAthleteWorkouts.clear();

            final ArrayList<AthleteWorkouts> bradyWorkouts = new ArrayList<>();
            bradyWorkouts.clear();

            bradyWorkouts.add(new AthleteWorkouts("FullBody", "Standing Band Rows", R.drawable.workout_pushup, "10 Reps", "3 Sets (30s Rest)")); //Need GIF for "Standing Resistance Band Row";
            bradyWorkouts.add(new AthleteWorkouts("FullBody", "Banded Push-Up", R.drawable.workout_pullup, "7 Reps", "3 Sets (30s Rest)"));//Gif for "BANDED push-up"
            bradyWorkouts.add(new AthleteWorkouts("FullBody", "Banded Core Rotations", R.drawable.workout_pullup, "7 Reps (Each Side)", "3 Sets (10s Rest)"));//"Banded core rotations" gif
            bradyWorkouts.add(new AthleteWorkouts("FullBody", "Banded Deadlifts", R.drawable.workout_pushup, "8 Reps", "3 Sets (15s Rest)"));//Banded DeadLift Gif
            bradyWorkouts.add(new AthleteWorkouts("FullBody", "Banded Bicep Curl", R.drawable.workout_bandedbicepcurls, "12 Reps", "3 Sets (10s Rest)"));
            bradyWorkouts.add(new AthleteWorkouts("FullBody", "Banded Tricep Extension", R.drawable.workout_bandedbicepcurls, "10 Reps", "3 Sets (15s Rest)"));//need gif
            bradyWorkouts.add(new AthleteWorkouts("FullBody", "Deceleration Lunges", R.drawable.workout_pullup, "10 Reps", "3 Sets (30s Rest)")); //need gif
            bradyWorkouts.add(new AthleteWorkouts("FullBody", "Banded Shoulder Press", R.drawable.workout_pullup, "10 Reps", "3 Sets (10s Rest)")); //need Gif
            bradyWorkouts.add(new AthleteWorkouts("FullBody", "X Band Squat", R.drawable.workout_pullup, "10 Reps", "3 Sets (10s Rest)")); //Need Gif

            allAthleteWorkouts.add(0, new AllWorkouts("Tom Brady", bradyWorkouts));

            final ArrayList<AthleteWorkouts> lebronWorkouts = new ArrayList<>();
            lebronWorkouts.clear();

            lebronWorkouts.add(new AthleteWorkouts("Mixed", "Push-Ups", R.drawable.workout_pushup, "Till-Failure", "3 Sets (45s Rest)"));
            lebronWorkouts.add(new AthleteWorkouts("Mixed", "Pull-Ups", R.drawable.workout_pullup, "10 Reps", "3 Sets (45s Rest)"));
            lebronWorkouts.add(new AthleteWorkouts("Mixed", "Dumbbell Snatches", R.drawable.workout_pullup, "5 Reps (Per Arm)", "3 Sets (45s Rest)")); //need to get gif resource for "Dumbbell Snatches"
            lebronWorkouts.add(new AthleteWorkouts("Mixed", "Single-Arm Cable Rows", R.drawable.workout_pullup, "10 Reps (Per Arm)", "3 Sets (45s Rest)")); //need gif resource for "single arm cable rows"
            lebronWorkouts.add(new AthleteWorkouts("LowerBody", "Dumbbell Squats", R.drawable.workout_squat, "8-14 Reps", "3 Sets (40s Rest)")); //need gid for "DUMBBELL squats"
            lebronWorkouts.add(new AthleteWorkouts("LowerBody", "Swiss Ball Hip Raises", R.drawable.workout_pullup, "10-12 Reps", "3 Sets (40s Rest)")); //need gif for "Swiss Ball Hip Raises"
            lebronWorkouts.add(new AthleteWorkouts("LowerBody", "Leg Curls", R.drawable.workout_pullup, "10 - 12 Reps", "3 Sets (40s Rest)")); //need gif for "Leg Curls"
            lebronWorkouts.add(new AthleteWorkouts("LowerBody", "Dumbbell Step-Ups", R.drawable.workout_pullup, "10 Reps", "3 Sets (40s Rest)")); //need gif for "Dumbell StepUps"
            lebronWorkouts.add(new AthleteWorkouts("LowerBody", "Dumbbell Calf Raises", R.drawable.workout_pullup, "12 Reps (Per Leg)", "3 Sets (40s Rest)")); //need gif for Dumbell Calf Raises
            lebronWorkouts.add(new AthleteWorkouts("Cardio", "Versa-Climber/Jump Rope", R.drawable.workout_pullup, "30 min", "Once")); //need gif for "Versaclimber Machine OR Jump Rope"
            lebronWorkouts.add(new AthleteWorkouts("UpperBody", "Bench Press", R.drawable.workout_benchpress, "10 Reps", "3 Sets (1m Rest)"));
            lebronWorkouts.add(new AthleteWorkouts("UpperBody", "Lat Pull-Down", R.drawable.workout_latpulldown, "10 Reps", "3 Sets (1m Rest)"));
            lebronWorkouts.add(new AthleteWorkouts("UpperBody", "Shoulder Press", R.drawable.workout_pullup, "6-10 Reps (Each Side)", "3 Sets (45s Rest)")); //Shoulder Press gif
            lebronWorkouts.add(new AthleteWorkouts("UpperBody", "Dumbbell-Rows", R.drawable.workout_dubbell_row, "10 Reps (Each Side)", "3 Sets (40s Rest)"));

            allAthleteWorkouts.add(1, new AllWorkouts("Lebron James", lebronWorkouts));

            final ArrayList<AthleteWorkouts> mcGregorWorkouts = new ArrayList<>();
            mcGregorWorkouts.clear();

            mcGregorWorkouts.add(new AthleteWorkouts("BodyWeight", "Muscle-Ups", R.drawable.workout_pushup, "1 - minute", "5 Sets (2 - 3 min Rest)")); //Gif
            mcGregorWorkouts.add(new AthleteWorkouts("BodyWeight", "Push-Ups", R.drawable.workout_pushup, "1 - minute", "5 Sets (2 - 3 min Rest)"));
            mcGregorWorkouts.add(new AthleteWorkouts("BodyWeight", "Pull-Ups", R.drawable.workout_pullup, "1 - minute", "5 Sets (2 - 3 min Rest)"));
            mcGregorWorkouts.add(new AthleteWorkouts("BodyWeight", "Squats", R.drawable.workout_squat, "1 - minute", "5 Sets (2 - 3 min Rest)")); //Gif (Air Squat)
            mcGregorWorkouts.add(new AthleteWorkouts("Stretches", "Back Roll", R.drawable.workout_squat, "1 - minute", "5 Sets (2 - 3 min Rest)"));
            mcGregorWorkouts.add(new AthleteWorkouts("Stretches", "Sitting Abdominal Stretch", R.drawable.workout_squat, "1", "Perform for 30s"));//Gif
            mcGregorWorkouts.add(new AthleteWorkouts("Stretches", "Sit-Back Shoulder Stretch", R.drawable.workout_squat, "1", "Perform for 30s"));//Gif
            mcGregorWorkouts.add(new AthleteWorkouts("Stretches", "Lying Leg Stretch", R.drawable.workout_squat, "1", "Perform for 30s"));//Gif
            mcGregorWorkouts.add(new AthleteWorkouts("Stretches", "Seated Butterfly", R.drawable.workout_squat, "1", "Perform for 30s"));//Gif
            mcGregorWorkouts.add(new AthleteWorkouts("Stretches", "Duck Walk", R.drawable.workout_squat, "1", "Perform till loose"));//Gif
            mcGregorWorkouts.add(new AthleteWorkouts("Stretches", "Horse Walk", R.drawable.workout_squat, "1", "Perform till loose"));//Gif
            mcGregorWorkouts.add(new AthleteWorkouts("Stretches", "Lizard Walk", R.drawable.workout_squat, "1", "Perform till loose"));//Gif
            mcGregorWorkouts.add(new AthleteWorkouts("Stretches", "Ostrich Walk", R.drawable.workout_squat, "1", "Perform till loose"));//Gif

            allAthleteWorkouts.add(2, new AllWorkouts("Connor McGregor", mcGregorWorkouts));

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.search_menu,menu);

        final MenuItem drawerItem = menu.findItem(R.id.settings_icon);

        drawerItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                drawerLayout.openDrawer(GravityCompat.START);
               // getUsername();
                return true;
            }
        });

        MenuItem searchItem = menu.findItem(R.id.search_icon);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Athlete Name");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                closeKeyboard();
                SQLiteDatabase database = _Page3_SelectWorkout.context.openOrCreateDatabase("Workouts", Context.MODE_PRIVATE,null);
                SliderList sliderList = new SliderList(database);
                ArrayList<_3_SliderItem> sliderItems = sliderList.getSliderList();
                for(_3_SliderItem item : sliderItems){
                    String athleteName = item.getAthleteName().toLowerCase();
                    if(athleteName.matches(query.toLowerCase()) || athleteName.contains(query.toLowerCase()) ){
                        AllWorkoutsFragment.viewPager2.setCurrentItem(item.getTagNum(),true);
                        return true;
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                if(!navigationView.getMenu().findItem(R.id.menuNavigation_workouts).isChecked()){
                    Toast.makeText(context,"Can't Search Here",Toast.LENGTH_SHORT).show();
                    return false;
                }

                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                closeKeyboard();
                return true;
            }
        });
        return true;
    }

    public void closeKeyboard(){
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
