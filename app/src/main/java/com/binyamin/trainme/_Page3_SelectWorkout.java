package com.binyamin.trainme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;

import java.util.ArrayList;
import java.util.List;

public class _Page3_SelectWorkout extends AppCompatActivity implements Runnable {
    private ViewPager2 viewPager2;
    private Handler sliderHandler = new Handler();
    int backButtonCount;
    static Context context;
    public static ArrayList<_3_SliderItem> list = new ArrayList<>();
    private Thread t;
    static ArrayList<AllWorkouts> allAthleteWorkouts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._3_activity_select_workout);

        t = new Thread(this,"DefineAthleteWorkouts");
        t.run();
        viewPager2 = findViewById(R.id.ImageSlider);

        //preparing list of images from drawable folder
        final ArrayList<_3_SliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new _3_SliderItem(R.drawable.homescreen_brady,"Tom Brady",true));
        sliderItems.add(new _3_SliderItem(R.drawable.homescreen_lebron,"Lebron James",true));
        sliderItems.add(new _3_SliderItem(R.drawable.homescreen_conormcgregor,"Connor McGregor",false));
        sliderItems.add(new _3_SliderItem(R.drawable.homescreen_aaronjudge,"Aaron Judge",false));
        sliderItems.add(new _3_SliderItem(R.drawable.homescreen_zlatan,"Zlatan Ibrah.",true));

        for(_3_SliderItem item : sliderItems){
            list.add(item);
        }
        viewPager2.setAdapter(new _3_SliderAdapter(sliderItems, viewPager2));
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(70));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable,3000);
            }
        });
        backButtonCount = 0;

        BottomAppBar bottomAppBar = findViewById(R.id.bar);
        bottomAppBar.replaceMenu(R.menu.bottom_navbar_menu);
        setSupportActionBar(bottomAppBar);
        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return true;
            }
        });

        context = getApplicationContext();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_navbar_menu, menu);
        return true;
    }
    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable,2000);
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
        final ArrayList<AthleteWorkouts> bradyWorkouts = new ArrayList<>();
        bradyWorkouts.add(new AthleteWorkouts("FullBody","Standing Band Rows",R.drawable.workout_pushup,"10 Reps","3 Sets (30s Rest)")); //Need GIF for "Standing Resistance Band Row";
        bradyWorkouts.add(new AthleteWorkouts("FullBody","Banded Push-Up",R.drawable.workout_pullup,"7 Reps","3 Sets (30s Rest)"));//Gif for "BANDED push-up"
        bradyWorkouts.add(new AthleteWorkouts("FullBody","Banded Core Rotations",R.drawable.workout_pullup,"7 Reps (Each Side)","3 Sets (10s Rest)"));//"Banded core rotations" gif
        bradyWorkouts.add(new AthleteWorkouts("FullBody","Banded Deadlifts",R.drawable.workout_pushup,"8 Reps","3 Sets (15s Rest)"));//Banded DeadLift Gif
        bradyWorkouts.add(new AthleteWorkouts("FullBody","Banded Bicep Curl",R.drawable.workout_bandedbicepcurls,"12 Reps","3 Sets (10s Rest)"));
        bradyWorkouts.add(new AthleteWorkouts("FullBody","Banded Tricep Extension",R.drawable.workout_bandedbicepcurls,"10 Reps","3 Sets (15s Rest)"));//need gif
        bradyWorkouts.add(new AthleteWorkouts("FullBody","Deceleration Lunges",R.drawable.workout_pullup,"10 Reps","3 Sets (30s Rest)")); //need gif
        bradyWorkouts.add(new AthleteWorkouts("FullBody","Banded Shoulder Press",R.drawable.workout_pullup,"10 Reps","3 Sets (10s Rest)")); //need Gif
        bradyWorkouts.add(new AthleteWorkouts("FullBody","X Band Squat",R.drawable.workout_pullup,"10 Reps","3 Sets (10s Rest)")); //Need Gif

        allAthleteWorkouts.add(new AllWorkouts("Tom Brady",bradyWorkouts));

        final ArrayList<AthleteWorkouts> lebronWorkouts = new ArrayList<>();
        lebronWorkouts.add(new AthleteWorkouts("Mixed","Push-Ups",R.drawable.workout_pushup,"Till-Failure","3 Sets (45s Rest)"));
        lebronWorkouts.add(new AthleteWorkouts("Mixed","Pull-Ups",R.drawable.workout_pullup,"10 Reps","3 Sets (45s Rest)"));
        lebronWorkouts.add(new AthleteWorkouts("Mixed","Dumbbell Snatches",R.drawable.workout_pullup,"5 Reps (Per Arm)","3 Sets (45s Rest)")); //need to get gif resource for "Dumbbell Snatches"
        lebronWorkouts.add(new AthleteWorkouts("Mixed","Single-Arm Cable Rows",R.drawable.workout_pullup,"10 Reps (Per Arm)","3 Sets (45s Rest)")); //need gif resource for "single arm cable rows"
        lebronWorkouts.add(new AthleteWorkouts("LowerBody","Dumbbell Squats",R.drawable.workout_squat,"8-14 Reps","3 Sets (40s Rest)")); //need gid for "DUMBBELL squats"
        lebronWorkouts.add(new AthleteWorkouts("LowerBody","Swiss Ball Hip Raises",R.drawable.workout_pullup,"10-12 Reps","3 Sets (40s Rest)")); //need gif for "Swiss Ball Hip Raises"
        lebronWorkouts.add(new AthleteWorkouts("LowerBody","Leg Curls",R.drawable.workout_pullup,"10 - 12 Reps","3 Sets (40s Rest)")); //need gif for "Leg Curls"
        lebronWorkouts.add(new AthleteWorkouts("LowerBody","Dumbbell Step-Ups",R.drawable.workout_pullup,"10 Reps","3 Sets (40s Rest)")); //need gif for "Dumbell StepUps"
        lebronWorkouts.add(new AthleteWorkouts("LowerBody","Dumbbell Calf Raises",R.drawable.workout_pullup,"12 Reps (Per Leg)","3 Sets (40s Rest)")); //need gif for Dumbell Calf Raises
        lebronWorkouts.add(new AthleteWorkouts("Cardio","Versaclimber Machine or Jump Rope",R.drawable.workout_pullup,"30 min","Once")); //need gif for "Versaclimber Machine OR Jump Rope"
        lebronWorkouts.add(new AthleteWorkouts("UpperBody","Bench Press",R.drawable.workout_benchpress,"10 Reps","3 Sets (1m Rest)"));
        lebronWorkouts.add(new AthleteWorkouts("UpperBody","Lat Pull-Down",R.drawable.workout_latpulldown,"10 Reps","3 Sets (1m Rest)"));
        lebronWorkouts.add(new AthleteWorkouts("UpperBody","Shoulder Press",R.drawable.workout_pullup,"6-10 Reps (Each Side)","3 Sets (45s Rest)")); //Shoulder Press gif
        lebronWorkouts.add(new AthleteWorkouts("UpperBody","Dumbbell-Rows",R.drawable.workout_dubbell_row,"10 Reps (Each Side)","3 Sets (40s Rest)"));

        allAthleteWorkouts.add(new AllWorkouts("Lebron James",lebronWorkouts));



    }

}
