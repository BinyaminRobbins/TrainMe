package com.binyamin.trainme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
public class _Page3_SelectWorkout extends AppCompatActivity implements Runnable{
    int backButtonCount;
    static Context context;
    private Thread t;
    static ArrayList<AllWorkouts> allAthleteWorkouts = new ArrayList<>();
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._3_activity_select_workout);

        context = getApplicationContext();

        Toast.makeText(this,"Click the \"Star\" to add a workout to your \"Favorites\"",Toast.LENGTH_SHORT);

        t = new Thread(this,"DefineAthleteWorkouts");
        t.run();

       Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);


        backButtonCount = 0;

        drawerLayout = findViewById(R.id.drawerLayout);


        navigationView = findViewById(R.id.navigationView);

        NavController navController = Navigation.findNavController(this, R.id.navHostFragment);
        NavigationUI.setupWithNavController(navigationView,navController);


    }

    //Automate Scrolling:
    /*private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
               // viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };*/

    @Override
    protected void onPause() {
        super.onPause();
        //sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //sliderHandler.postDelayed(sliderRunnable,2000);
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
            final int delay = 5000; //milliseconds
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
    @Override
    public void run() {
        allAthleteWorkouts.clear();

        final ArrayList<AthleteWorkouts> bradyWorkouts = new ArrayList<>();
        bradyWorkouts.clear();

        bradyWorkouts.add(new AthleteWorkouts("FullBody","Standing Band Rows",R.drawable.workout_pushup,"10 Reps","3 Sets (30s Rest)")); //Need GIF for "Standing Resistance Band Row";
        bradyWorkouts.add(new AthleteWorkouts("FullBody","Banded Push-Up",R.drawable.workout_pullup,"7 Reps","3 Sets (30s Rest)"));//Gif for "BANDED push-up"
        bradyWorkouts.add(new AthleteWorkouts("FullBody","Banded Core Rotations",R.drawable.workout_pullup,"7 Reps (Each Side)","3 Sets (10s Rest)"));//"Banded core rotations" gif
        bradyWorkouts.add(new AthleteWorkouts("FullBody","Banded Deadlifts",R.drawable.workout_pushup,"8 Reps","3 Sets (15s Rest)"));//Banded DeadLift Gif
        bradyWorkouts.add(new AthleteWorkouts("FullBody","Banded Bicep Curl",R.drawable.workout_bandedbicepcurls,"12 Reps","3 Sets (10s Rest)"));
        bradyWorkouts.add(new AthleteWorkouts("FullBody","Banded Tricep Extension",R.drawable.workout_bandedbicepcurls,"10 Reps","3 Sets (15s Rest)"));//need gif
        bradyWorkouts.add(new AthleteWorkouts("FullBody","Deceleration Lunges",R.drawable.workout_pullup,"10 Reps","3 Sets (30s Rest)")); //need gif
        bradyWorkouts.add(new AthleteWorkouts("FullBody","Banded Shoulder Press",R.drawable.workout_pullup,"10 Reps","3 Sets (10s Rest)")); //need Gif
        bradyWorkouts.add(new AthleteWorkouts("FullBody","X Band Squat",R.drawable.workout_pullup,"10 Reps","3 Sets (10s Rest)")); //Need Gif
        bradyWorkouts.add(new AthleteWorkouts("Mixed","X Band Squat",R.drawable.workout_pullup,"10 Reps","3 Sets (10s Rest)")); //Need Gif

        allAthleteWorkouts.add(0,new AllWorkouts("Tom Brady",bradyWorkouts));

        final ArrayList<AthleteWorkouts> lebronWorkouts = new ArrayList<>();
        lebronWorkouts.clear();

        lebronWorkouts.add(new AthleteWorkouts("Mixed","Push-Ups",R.drawable.workout_pushup,"Till-Failure","3 Sets (45s Rest)"));
        lebronWorkouts.add(new AthleteWorkouts("Mixed","Pull-Ups",R.drawable.workout_pullup,"10 Reps","3 Sets (45s Rest)"));
        lebronWorkouts.add(new AthleteWorkouts("Mixed","Dumbbell Snatches",R.drawable.workout_pullup,"5 Reps (Per Arm)","3 Sets (45s Rest)")); //need to get gif resource for "Dumbbell Snatches"
        lebronWorkouts.add(new AthleteWorkouts("Mixed","Single-Arm Cable Rows",R.drawable.workout_pullup,"10 Reps (Per Arm)","3 Sets (45s Rest)")); //need gif resource for "single arm cable rows"
        lebronWorkouts.add(new AthleteWorkouts("LowerBody","Dumbbell Squats",R.drawable.workout_squat,"8-14 Reps","3 Sets (40s Rest)")); //need gid for "DUMBBELL squats"
        lebronWorkouts.add(new AthleteWorkouts("LowerBody","Swiss Ball Hip Raises",R.drawable.workout_pullup,"10-12 Reps","3 Sets (40s Rest)")); //need gif for "Swiss Ball Hip Raises"
        lebronWorkouts.add(new AthleteWorkouts("LowerBody","Leg Curls",R.drawable.workout_pullup,"10 - 12 Reps","3 Sets (40s Rest)")); //need gif for "Leg Curls"
        lebronWorkouts.add(new AthleteWorkouts("LowerBody","Dumbbell Step-Ups",R.drawable.workout_pullup,"10 Reps","3 Sets (40s Rest)")); //need gif for "Dumbell StepUps"
        lebronWorkouts.add(new AthleteWorkouts("LowerBody","Dumbbell Calf Raises",R.drawable.workout_pullup,"12 Reps (Per Leg)","3 Sets (40s Rest)")); //need gif for Dumbell Calf Raises
        lebronWorkouts.add(new AthleteWorkouts("Cardio","Versa-Climber/Jump Rope",R.drawable.workout_pullup,"30 min","Once")); //need gif for "Versaclimber Machine OR Jump Rope"
        lebronWorkouts.add(new AthleteWorkouts("UpperBody","Bench Press",R.drawable.workout_benchpress,"10 Reps","3 Sets (1m Rest)"));
        lebronWorkouts.add(new AthleteWorkouts("UpperBody","Lat Pull-Down",R.drawable.workout_latpulldown,"10 Reps","3 Sets (1m Rest)"));
        lebronWorkouts.add(new AthleteWorkouts("UpperBody","Shoulder Press",R.drawable.workout_pullup,"6-10 Reps (Each Side)","3 Sets (45s Rest)")); //Shoulder Press gif
        lebronWorkouts.add(new AthleteWorkouts("UpperBody","Dumbbell-Rows",R.drawable.workout_dubbell_row,"10 Reps (Each Side)","3 Sets (40s Rest)"));

        allAthleteWorkouts.add(1,new AllWorkouts("Lebron James",lebronWorkouts));


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
                    Intent intent = new Intent(context, _Page3_SelectWorkout.class);
                    startActivity(intent);
                    finish();
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
}
