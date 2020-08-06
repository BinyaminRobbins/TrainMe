package com.binyamin.trainme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Objects;

public class _Page3_SelectWorkout extends AppCompatActivity implements Runnable, View.OnClickListener {
    int backButtonCount;
    static Context context;
    static ArrayList<AllWorkouts> allAthleteWorkouts = new ArrayList<>();
    static ArrayList<AllWorkouts> allAthleteDiets = new ArrayList<>();
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    SharedPreferences sharedPreferences;
    Handler sliderHandler;
    static NavController navController;
    int j;
    private ProgressBar lagProBar;
    String TAG;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._3_activity_select_workout);

        TAG = "Page 3";
        context = getApplicationContext();
        sharedPreferences = this.getSharedPreferences("com.binyamin.trainme", Context.MODE_PRIVATE);

        run();

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        backButtonCount = 0;

        navigationView = findViewById(R.id.navigationView);
        navigationView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        navController = Navigation.findNavController(this, R.id.navHostFragment);
        NavigationUI.setupWithNavController(navigationView, navController);

        drawerLayout = findViewById(R.id.drawerLayout);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                if(j == 1){
                    navController.navigate(R.id.menuNavigation_workout_and_diets);
                    j=0;
                }

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        final PurchaseProduct product = new PurchaseProduct(context, _Page3_SelectWorkout.this, getResources().getString(R.string.productId), sharedPreferences);
        product.setUp();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
                if(item.getItemId() == R.id.menuNavigation_workout_and_diets && Objects.requireNonNull(navController.getCurrentDestination()).getId() != R.id.menuNavigation_workout_and_diets){
                    j=1;
                    drawerLayout.closeDrawer(GravityCompat.START);
                    lagProBar.setElevation(50);
                    lagProBar.setVisibility(View.VISIBLE);
                    return true;
                }else if(item.getItemId() == R.id.menuNavigation_workout_and_diets && Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.menuNavigation_workout_and_diets){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }else{
                    navController.navigate(item.getItemId());
                    j=0;
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                return false;
            }
        });
            navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
                @Override
                public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                    if (destination.getId() == R.id.menuNavigation_youtube && !product.checkIfOwned()) {
                        AlertDialog dialog;
                        AlertDialog.Builder builder = new AlertDialog.Builder(_Page3_SelectWorkout.this);
                        builder.setTitle("Upgrade to Premium");
                        builder.setIcon(R.drawable.ic_action_premium);
                        builder.setMessage("You have discovered a premium feature.");
                        builder.setPositiveButton("Check It Out", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                navController.navigate(R.id.menuNavigation_premium);
                            }
                        });
                        builder.setNegativeButton("Not now", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                navController.navigate(R.id.menuNavigation_workout_and_diets);
                            }
                        });
                        dialog = builder.create();
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.show();
                    }else if(destination.getId() == R.id.menuNavigation_workout_and_diets){
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                lagProBar.setVisibility(View.GONE);
                            }
                        },3000);
                    }
                }
            });
            sliderHandler = new Handler();

            lagProBar = findViewById(R.id.lagProBar);
        LinearLayout footerLayout = findViewById(R.id.footerLayout);
        footerLayout.setOnClickListener(this);
        }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        backButtonCount = 0;
        PurchaseProduct product = new PurchaseProduct(this, _Page3_SelectWorkout.this, getResources().getString(R.string.productId), sharedPreferences);
        product.setUp();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if (backButtonCount >= 1) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Press Again to Close Application", Toast.LENGTH_SHORT).show();
            final Handler handler = new Handler();
            final int delay = 3000; //milliseconds (x1000 for seconds)
            handler.postDelayed(new Runnable() {
                public void run() {
                    backButtonCount = 0;
                }
            }, delay);
            backButtonCount++;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.search_menu, menu);

        final MenuItem drawerItem = menu.findItem(R.id.settings_icon);

        drawerItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
        });

        final MenuItem searchItem = menu.findItem(R.id.search_icon);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Athlete Name");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                closeKeyboard();
                searchItem.collapseActionView();
                SliderList sliderList = new SliderList(context, sharedPreferences);
                ArrayList<_3_SliderItem> sliderItems = sliderList.getWorkoutList();
                for (_3_SliderItem item : sliderItems) {
                    String athleteName = item.getAthleteName().toLowerCase();
                    if (athleteName.matches(query.toLowerCase()) || athleteName.contains(query.toLowerCase())) {
                        AllWorkoutsFragment.viewPager2.setCurrentItem(item.getTagNum(), true);
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
                if (!navigationView.getMenu().findItem(R.id.menuNavigation_workout_and_diets).isChecked()) {
                    Toast.makeText(context, "Can't Search Here", Toast.LENGTH_SHORT).show();
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

    public void closeKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm).hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void run() {

    //Workouts
            int i = 0;
            allAthleteWorkouts.clear();

            Log.i(TAG,"Declaring Workouts");

            final ArrayList<AthleteWorkouts> bradyWorkouts = new ArrayList<>();

            bradyWorkouts.add(new AthleteWorkouts("Full Body", "Standing Band Rows", R.drawable.workout_pushup, "10 Reps", "3 Sets (30s Rest)")); //Need GIF for "Standing Resistance Band Row";
            bradyWorkouts.add(new AthleteWorkouts("Full Body", "Banded Push-Up", R.drawable.workout_pullup, "7 Reps", "3 Sets (30s Rest)"));//Gif for "BANDED push-up"
            bradyWorkouts.add(new AthleteWorkouts("Full Body", "Banded Core Rotations", R.drawable.workout_pullup, "7 Reps (Each Side)", "3 Sets (10s Rest)"));//"Banded core rotations" gif
            bradyWorkouts.add(new AthleteWorkouts("Full Body", "Banded Deadlifts", R.drawable.workout_pushup, "8 Reps", "3 Sets (15s Rest)"));//Banded DeadLift Gif
            bradyWorkouts.add(new AthleteWorkouts("Full Body", "Banded Bicep Curl", R.drawable.workout_bandedbicepcurls, "12 Reps", "3 Sets (10s Rest)"));
            bradyWorkouts.add(new AthleteWorkouts("Full Body", "Banded Tricep Extension", R.drawable.workout_bandedbicepcurls, "10 Reps", "3 Sets (15s Rest)"));//need gif
            bradyWorkouts.add(new AthleteWorkouts("Full Body", "Deceleration Lunges", R.drawable.workout_pullup, "10 Reps", "3 Sets (30s Rest)")); //need gif
            bradyWorkouts.add(new AthleteWorkouts("Full Body", "Banded Shoulder Press", R.drawable.workout_pullup, "10 Reps", "3 Sets (10s Rest)")); //need Gif
            bradyWorkouts.add(new AthleteWorkouts("Full Body", "X Band Squat", R.drawable.workout_pullup, "10 Reps", "3 Sets (10s Rest)")); //Need Gif

            allAthleteWorkouts.add(i, new AllWorkouts("Tom Brady", bradyWorkouts));

            final ArrayList<AthleteWorkouts> lebronWorkouts = new ArrayList<>();

            lebronWorkouts.add(new AthleteWorkouts("Mixed", "Push-Ups", R.drawable.workout_pushup, "Till-Failure", "3 Sets (45s Rest)"));
            lebronWorkouts.add(new AthleteWorkouts("Mixed", "Pull-Ups", R.drawable.workout_pullup, "10 Reps", "3 Sets (45s Rest)"));
            lebronWorkouts.add(new AthleteWorkouts("Mixed", "KettleBell Snatches", R.drawable.dumbbell_snatch, "5 Reps (Per Arm)", "3 Sets (45s Rest)")); //need to get gif resource for "Dumbbell Snatches"
            lebronWorkouts.add(new AthleteWorkouts("Mixed", "Single-Arm Cable Rows", R.drawable.workout_pullup, "10 Reps (Per Arm)", "3 Sets (45s Rest)")); //need gif resource for "single arm cable rows"
            lebronWorkouts.add(new AthleteWorkouts("Lower Body", "Dumbbell Squats", R.drawable.workout_squat, "8-14 Reps", "3 Sets (40s Rest)")); //need gid for "DUMBBELL squats"
            lebronWorkouts.add(new AthleteWorkouts("Lower Body", "Swiss Ball Hip Raises", R.drawable.workout_pullup, "10-12 Reps", "3 Sets (40s Rest)")); //need gif for "Swiss Ball Hip Raises"
            lebronWorkouts.add(new AthleteWorkouts("Lower Body", "Leg Curls", R.drawable.workout_pullup, "10 - 12 Reps", "3 Sets (40s Rest)")); //need gif for "Leg Curls"
            lebronWorkouts.add(new AthleteWorkouts("Lower Body", "Dumbbell Step-Ups", R.drawable.workout_pullup, "10 Reps", "3 Sets (40s Rest)")); //need gif for "Dumbell StepUps"
            lebronWorkouts.add(new AthleteWorkouts("Lower Body", "Dumbbell Calf Raises", R.drawable.workout_pullup, "12 Reps (Per Leg)", "3 Sets (40s Rest)")); //need gif for Dumbell Calf Raises
            lebronWorkouts.add(new AthleteWorkouts("Cardio", "Versa-Climber/Jump Rope", R.drawable.workout_pullup, "30 min", "Once")); //need gif for "Versaclimber Machine OR Jump Rope"
            lebronWorkouts.add(new AthleteWorkouts("Upper Body", "Bench Press", R.drawable.workout_benchpress, "10 Reps", "3 Sets (1m Rest)"));
            lebronWorkouts.add(new AthleteWorkouts("Upper Body", "Lat Pull-Down", R.drawable.workout_latpulldown, "10 Reps", "3 Sets (1m Rest)"));
            lebronWorkouts.add(new AthleteWorkouts("Upper Body", "Shoulder Press", R.drawable.workout_pullup, "6-10 Reps (Each Side)", "3 Sets (45s Rest)")); //Shoulder Press gif
            lebronWorkouts.add(new AthleteWorkouts("Upper Body", "Dumbbell-Rows", R.drawable.workout_dubbell_row, "10 Reps (Each Side)", "3 Sets (40s Rest)"));

            allAthleteWorkouts.add(i+1, new AllWorkouts("Lebron James", lebronWorkouts));

            final ArrayList<AthleteWorkouts> mcGregorWorkouts = new ArrayList<>();

            mcGregorWorkouts.add(new AthleteWorkouts("Body-Weight", "Chin-Ups", R.drawable.chin_ups, "1 - minute", "5 Sets (2 - 3 min Rest)")); //Gif
            mcGregorWorkouts.add(new AthleteWorkouts("Body-Weight", "Push-Ups", R.drawable.workout_pushup, "1 - minute", "5 Sets (2 - 3 min Rest)"));
            mcGregorWorkouts.add(new AthleteWorkouts("Body-Weight", "Pull-Ups", R.drawable.workout_pullup, "1 - minute", "5 Sets (2 - 3 min Rest)"));
            mcGregorWorkouts.add(new AthleteWorkouts("Body-Weight", "Squats", R.drawable.workout_squat, "1 - minute", "5 Sets (2 - 3 min Rest)")); //Gif (Air Squat)
            mcGregorWorkouts.add(new AthleteWorkouts("Stretches", "Back Roll", R.drawable.workout_squat, "1 - minute", "5 Sets (2 - 3 min Rest)"));
            mcGregorWorkouts.add(new AthleteWorkouts("Stretches", "Sitting Abdominal Stretch", R.drawable.workout_squat, "1", "Perform for 30s"));//Gif
            mcGregorWorkouts.add(new AthleteWorkouts("Stretches", "Sit-Back Shoulder Stretch", R.drawable.workout_squat, "1", "Perform for 30s"));//Gif
            mcGregorWorkouts.add(new AthleteWorkouts("Stretches", "Lying Leg Stretch", R.drawable.workout_squat, "1", "Perform for 30s"));//Gif
            mcGregorWorkouts.add(new AthleteWorkouts("Stretches", "Seated Butterfly", R.drawable.workout_squat, "1", "Perform for 30s"));//Gif
            mcGregorWorkouts.add(new AthleteWorkouts("Stretches", "Duck Walk", R.drawable.workout_squat, "1", "Perform till loose"));//Gif
            mcGregorWorkouts.add(new AthleteWorkouts("Stretches", "Horse Walk", R.drawable.workout_squat, "1", "Perform till loose"));//Gif
            mcGregorWorkouts.add(new AthleteWorkouts("Stretches", "Lizard Walk", R.drawable.workout_squat, "1", "Perform till loose"));//Gif
            mcGregorWorkouts.add(new AthleteWorkouts("Stretches", "Ostrich Walk", R.drawable.workout_squat, "1", "Perform till loose"));//Gif

            allAthleteWorkouts.add(i+1, new AllWorkouts("Connor McGregor", mcGregorWorkouts));

            final ArrayList<AthleteWorkouts> zlatanWorkouts = new ArrayList<>();

            zlatanWorkouts.add(new AthleteWorkouts("Daily Workout", "Dead-lift", R.drawable.workout_squat, "5 Reps", "4 Sets (2 min rest)")); //Gif
            zlatanWorkouts.add(new AthleteWorkouts("Daily Workout", "Barbell Squats", R.drawable.workout_squat, "3 Reps", "2 Sets (2 min rest)")); //Gif
            zlatanWorkouts.add(new AthleteWorkouts("Daily Workout", "Push Press", R.drawable.workout_squat, "3 Reps", "3 Sets (1 min rest)")); //Gif
            zlatanWorkouts.add(new AthleteWorkouts("Daily Workout", "Bench Press", R.drawable.workout_squat, "5 Reps", "4 Sets (2 min rest)")); //Gif
            zlatanWorkouts.add(new AthleteWorkouts("Daily Workout", "Pull-Ups", R.drawable.workout_squat, "15 Reps", "2 Sets (90s rest)")); //Gif
            zlatanWorkouts.add(new AthleteWorkouts("Daily Workout", "Weighted Floor Wipers", R.drawable.workout_squat, "15 Reps (each side)", "2 Sets (90s rest)")); //Gif

            allAthleteWorkouts.add(i+1, new AllWorkouts("Zlatan Ibrah.", zlatanWorkouts));


            final ArrayList<AthleteWorkouts> judgeWorkouts = new ArrayList<>();

            judgeWorkouts.add(new AthleteWorkouts("Daily Workout", "Tire Jumps", R.drawable.workout_squat, "Till Fatigued", "Till Fatigued")); //Gif
            judgeWorkouts.add(new AthleteWorkouts("Daily Workout", "Bench Press", R.drawable.workout_squat, "Limit", "3-4 Sets")); //Gif
            judgeWorkouts.add(new AthleteWorkouts("Daily Workout", "Sled Pushes", R.drawable.workout_squat, "Limit", "Limit")); //Gif
            judgeWorkouts.add(new AthleteWorkouts("Daily Workout", "Barbell Squats", R.drawable.workout_squat, "Limit", "3-4 Sets")); //Gif
            judgeWorkouts.add(new AthleteWorkouts("Daily Workout", "Box Jumps", R.drawable.workout_squat, "Till Fatigued", "Till Fatigued")); //Gif
            judgeWorkouts.add(new AthleteWorkouts("Daily Workout", "Yoga / Pilates", R.drawable.workout_squat, "30 min", "Every Day")); //Gif

            allAthleteWorkouts.add(i+1, new AllWorkouts("Aaron Judge", judgeWorkouts));

            final ArrayList<AthleteWorkouts> ronaldoWorkouts = new ArrayList<>();

            ronaldoWorkouts.add(new AthleteWorkouts("Legs", "Drop Squats", R.drawable.workout_squat, "40 seconds", "1 Set (20s rest)")); //Gif
            ronaldoWorkouts.add(new AthleteWorkouts("Legs", "Dumbbell Reverse Lunges", R.drawable.workout_squat, "30 seconds (per side)", "1 Set (20s rest)")); //Gif
            ronaldoWorkouts.add(new AthleteWorkouts("Legs", "Single-Leg Glute Bridge", R.drawable.workout_squat, "40 seconds (per side)", "1 Set (20s rest)")); //Gif
            ronaldoWorkouts.add(new AthleteWorkouts("Legs", "Jump Squats", R.drawable.workout_squat, "30 seconds", "1 Set (20s rest)")); //Gif
            ronaldoWorkouts.add(new AthleteWorkouts("Legs", "Side-lying Leg Raises", R.drawable.workout_squat, "50 seconds (per side)", "1 Set (20s rest)")); //Gif
            ronaldoWorkouts.add(new AthleteWorkouts("Legs", "Bodyweight Squats", R.drawable.workout_squat, "30 seconds", "1 Set (20s rest)")); //Gif
            ronaldoWorkouts.add(new AthleteWorkouts("Circuit Training #1", "Barbell Squat", R.drawable.workout_squat, "30 seconds", "1 Set (20s rest)")); //Gif
            ronaldoWorkouts.add(new AthleteWorkouts("Circuit Training #1", "Box Jump", R.drawable.workout_squat, "8 Reps", "3 Sets (30s rest)")); //Gif
            ronaldoWorkouts.add(new AthleteWorkouts("Circuit Training #1", "Broad Jump", R.drawable.workout_squat, "10 Reps", "3 Sets (30s rest)")); //Gif
            ronaldoWorkouts.add(new AthleteWorkouts("Circuit Training #1", "Broad Jump", R.drawable.workout_squat, "8 Reps", "3 Sets (30s rest)")); //Gif
            ronaldoWorkouts.add(new AthleteWorkouts("Circuit Training #1", "Lateral Bound", R.drawable.workout_squat, "10 Reps", "3 Sets (30s rest)")); //Gif
            ronaldoWorkouts.add(new AthleteWorkouts("Circuit Training #2", "Burpee Pullups", R.drawable.workout_squat, "10-15 Reps", "3 Sets (30s rest)")); //Gif
            ronaldoWorkouts.add(new AthleteWorkouts("Circuit Training #2", "Bench Dips", R.drawable.workout_squat, "20 Reps", "3 Sets (30s rest)")); //Gif
            ronaldoWorkouts.add(new AthleteWorkouts("Circuit Training #2", "Push-Ups", R.drawable.workout_squat, "20-30 Reps", "3 Sets (30s rest)")); //Gif
            ronaldoWorkouts.add(new AthleteWorkouts("Circuit Training #2", "Medicine Ball Toss", R.drawable.workout_squat, "50 Reps", "3 Sets (30s rest)")); //Gif
            ronaldoWorkouts.add(new AthleteWorkouts("Circuit Training #2", "Push-Press", R.drawable.workout_squat, "10 Reps", "3 Sets (30s rest)")); //Gif
            ronaldoWorkouts.add(new AthleteWorkouts("Quads + Cardio", "Jump Rope", R.drawable.workout_squat, "1 min", "10 Sets (1m rest)")); //Gif
            ronaldoWorkouts.add(new AthleteWorkouts("Quads + Cardio", "Power Cleans", R.drawable.workout_squat, "5 Reps", "5 Sets (20s rest)")); //Gif
            ronaldoWorkouts.add(new AthleteWorkouts("Quads + Cardio", "Sprints", R.drawable.workout_squat, "200 meters", "8 Sets (1m rest)")); //Gif
            ronaldoWorkouts.add(new AthleteWorkouts("Abs + Core", "One-Arm Side Lifts", R.drawable.workout_squat, "5 Reps", "3 Sets (15s rest)")); //Gif
            ronaldoWorkouts.add(new AthleteWorkouts("Abs + Core", "Overhead Slams", R.drawable.workout_squat, "10-12 Reps", "3 Sets (15s rest)")); //Gif
            ronaldoWorkouts.add(new AthleteWorkouts("Abs + Core", "Knee Tuck Jump", R.drawable.workout_squat, "10-12 Reps", "3 Sets (15s rest)")); //Gif
            ronaldoWorkouts.add(new AthleteWorkouts("Abs + Core", "Hanging Leg Raises", R.drawable.workout_squat, "10-15 Reps", "3 Sets (25s rest)")); //Gif
            ronaldoWorkouts.add(new AthleteWorkouts("Abs + Core", "Barbell Squats", R.drawable.workout_squat, "10-15 Reps", "3 Sets (25s rest)")); //Gif

            allAthleteWorkouts.add(i+1, new AllWorkouts("Cristiano Ronaldo", ronaldoWorkouts));

            final ArrayList<AthleteWorkouts> jamesHarrisonWorkouts = new ArrayList<>();

            jamesHarrisonWorkouts.add(new AthleteWorkouts("Chest", "Bench Press", R.drawable.workout_benchpress, "10 Reps", "10 Sets"));
            jamesHarrisonWorkouts.add(new AthleteWorkouts("Chest", "Incline Bench Press", R.drawable.workout_benchpress, "10 Reps", "10 Sets"));
            jamesHarrisonWorkouts.add(new AthleteWorkouts("Chest", "No-Legs Bench Press", R.drawable.workout_benchpress, "10 Reps", "10 Sets"));
            jamesHarrisonWorkouts.add(new AthleteWorkouts("Legs", "Barbell Back Squats", R.drawable.workout_benchpress, "10 Reps", "10 Sets"));
            jamesHarrisonWorkouts.add(new AthleteWorkouts("Legs", "Barbell Deadlifts", R.drawable.workout_pullup, "10 Reps", "10 Sets"));
            jamesHarrisonWorkouts.add(new AthleteWorkouts("Legs", "Hip Thrusts", R.drawable.workout_benchpress, "10 Reps", "5 Sets"));
            jamesHarrisonWorkouts.add(new AthleteWorkouts("Legs", "Sled Push", R.drawable.workout_benchpress, "100 meters", "5-10 Sets"));
            jamesHarrisonWorkouts.add(new AthleteWorkouts("Legs", "Sumo Belt Squats", R.drawable.workout_benchpress, "10 Reps", "10 Sets"));
            jamesHarrisonWorkouts.add(new AthleteWorkouts("Core", "Hanging Windshield Wipers", R.drawable.workout_benchpress, "30 Reps", "2 Sets"));
            jamesHarrisonWorkouts.add(new AthleteWorkouts("Core", "Dragon Flags", R.drawable.workout_benchpress, "3-5 Reps", "5 Sets"));
            jamesHarrisonWorkouts.add(new AthleteWorkouts("Core", "Ab Rollouts", R.drawable.workout_benchpress, "8-12 Reps", "3 Sets"));

            allAthleteWorkouts.add(i+1, new AllWorkouts("James Harrison", jamesHarrisonWorkouts));

            final ArrayList<AthleteWorkouts> paulGeorgeWorkouts = new ArrayList<>();

            paulGeorgeWorkouts.add(new AthleteWorkouts("Upper Body", "Shoulder Shrug", R.drawable.workout_benchpress, "6-10 Reps", "1 Set"));//Gif
            paulGeorgeWorkouts.add(new AthleteWorkouts("Upper Body", "Incline Bench Press", R.drawable.workout_benchpress, "6-8 Reps", "3 Sets"));//Gif
            paulGeorgeWorkouts.add(new AthleteWorkouts("Upper Body", "Flat Bench Press", R.drawable.workout_benchpress, "8-10 Reps", "3 Sets"));//Gif
            paulGeorgeWorkouts.add(new AthleteWorkouts("Upper Body", "Pull-Ups", R.drawable.workout_benchpress, "8-10 Reps", "1 Set"));//Gif
            paulGeorgeWorkouts.add(new AthleteWorkouts("Upper Body", "Machine Pullover", R.drawable.workout_benchpress, "6-10 Reps", "1 Set"));//Gif
            paulGeorgeWorkouts.add(new AthleteWorkouts("Upper Body", "Dumbbell Lateral Raises", R.drawable.workout_benchpress, "6-10 Reps", "2 Sets"));//Gif
            paulGeorgeWorkouts.add(new AthleteWorkouts("Upper Body", "Dumbbell Front Raises", R.drawable.workout_benchpress, "6-10 Reps", "2 Sets"));//Gif
            paulGeorgeWorkouts.add(new AthleteWorkouts("Upper Body", "Seated Cable Row", R.drawable.workout_benchpress, "6-10 Reps", "1 Set"));//Gif
            paulGeorgeWorkouts.add(new AthleteWorkouts("Upper Body", "Machine Chest Press", R.drawable.workout_benchpress, "6-10 Reps", "1 Set"));//Gif
            paulGeorgeWorkouts.add(new AthleteWorkouts("Upper Body", "Lat Pull-Down", R.drawable.workout_benchpress, "6-10 Reps", "1 Set"));//Gif
            paulGeorgeWorkouts.add(new AthleteWorkouts("Upper Body", "Machine Reverse Fly", R.drawable.workout_benchpress, "8-10 Reps", "1 Set"));//Gif
            paulGeorgeWorkouts.add(new AthleteWorkouts("Upper Body", "Triceps Cable Press Downs", R.drawable.workout_benchpress, "6-8 Reps", "1 Set"));//Gif
            paulGeorgeWorkouts.add(new AthleteWorkouts("Upper Body", "Cable Curls", R.drawable.workout_benchpress, "3-4 Reps", "2 Sets"));//Gif

            paulGeorgeWorkouts.add(new AthleteWorkouts("Lower Body", "Dead Lifts", R.drawable.workout_benchpress, "6-8 Reps", "2 Sets"));//Gif
            paulGeorgeWorkouts.add(new AthleteWorkouts("Lower Body", "Bar Squats", R.drawable.workout_benchpress, "6-8 Reps", "2 Sets"));//Gif
            paulGeorgeWorkouts.add(new AthleteWorkouts("Lower Body", "Leg Press", R.drawable.workout_benchpress, "6-8 Reps", "2 Sets"));//Gif
            paulGeorgeWorkouts.add(new AthleteWorkouts("Lower Body", "Leg Curls", R.drawable.workout_benchpress, "6-10 Reps", "1 Set"));//Gif
            paulGeorgeWorkouts.add(new AthleteWorkouts("Lower Body", "Dumbbell Lunges", R.drawable.workout_benchpress, "6-8 Reps", "2 Sets"));//Gif
            paulGeorgeWorkouts.add(new AthleteWorkouts("Lower Body", "Romanian Dead-Lifts", R.drawable.workout_benchpress, "8 Reps", "2 Sets"));//Gif
            paulGeorgeWorkouts.add(new AthleteWorkouts("Lower Body", "Reverse Crunches", R.drawable.workout_benchpress, "12 Reps", "2 Sets"));//Gif

            allAthleteWorkouts.add(i+1, new AllWorkouts("Paul George", paulGeorgeWorkouts));

            final ArrayList<AthleteWorkouts> jjWattWorkouts = new ArrayList<>();

            jjWattWorkouts.add(new AthleteWorkouts("Lower Body", "Romanian Deadlift", R.drawable.workout_benchpress, "6 Reps", "5 Sets"));//Gif
            jjWattWorkouts.add(new AthleteWorkouts("Lower Body", "Dumbbell Lunge", R.drawable.workout_benchpress, "12 Reps", "3 Sets"));//Gif
            jjWattWorkouts.add(new AthleteWorkouts("Lower Body", "Weighted Planks", R.drawable.workout_benchpress, "45 seconds", "3 Sets"));//Gif
            jjWattWorkouts.add(new AthleteWorkouts("Lower Body", "Box Squats", R.drawable.workout_benchpress, "3 Reps", "6 Sets"));//Gif
            jjWattWorkouts.add(new AthleteWorkouts("Upper Body", "Incline Bench Press", R.drawable.workout_benchpress, "10,5,3,5 Reps", "4 Sets (Super)"));//Gif
            jjWattWorkouts.add(new AthleteWorkouts("Upper Body", "Dumbbell Floor Press", R.drawable.workout_benchpress, "8 Reps", "8 Sets"));//Gif
            jjWattWorkouts.add(new AthleteWorkouts("Upper Body", "Y-bar Rows", R.drawable.workout_benchpress, "12 Reps", "3 Sets"));//Gif
            jjWattWorkouts.add(new AthleteWorkouts("Upper Body", "T-bar Rows", R.drawable.workout_benchpress, "12 Reps", "3 Sets"));//Gif
            jjWattWorkouts.add(new AthleteWorkouts("Upper Body", "Lat Pulldown", R.drawable.workout_benchpress, "10 Reps", "3 Sets (Super)"));//Gif
            jjWattWorkouts.add(new AthleteWorkouts("Upper Body", "Fat-bar Pull-up", R.drawable.workout_benchpress, "10 Reps", "3 Sets"));//Gif
            jjWattWorkouts.add(new AthleteWorkouts("Upper Body", "Shoulder Pack Roll", R.drawable.workout_benchpress, "10 Reps", "2 Sets"));//Gif

            allAthleteWorkouts.add(i+1, new AllWorkouts("J.J. Watt", jjWattWorkouts));

            final ArrayList<AthleteWorkouts> dkMetcalfWorkouts = new ArrayList<>();

            dkMetcalfWorkouts.add(new AthleteWorkouts("Chest", "Incline Bench Press", R.drawable.workout_benchpress, "6,8 Reps", "2 Sets (Reverse Pyramid)"));//Gif
            dkMetcalfWorkouts.add(new AthleteWorkouts("Chest", "Flat Bench Press", R.drawable.workout_benchpress, "6,8 Reps", "2 Sets (Reverse Pyramid)"));//Gif
            dkMetcalfWorkouts.add(new AthleteWorkouts("Chest", "Incline Dumbbell Curls", R.drawable.workout_benchpress, "5,8,10 Reps", "3 Sets (Reverse Pyramid)"));//Gif
            dkMetcalfWorkouts.add(new AthleteWorkouts("Chest", "Cable Rope Pushdowns", R.drawable.workout_benchpress, "6,10,12 Reps", "3 Sets (Reverse Pyramid)"));//Gif
            dkMetcalfWorkouts.add(new AthleteWorkouts("Chest", "Wide-Grip Rows", R.drawable.workout_benchpress, "12,10,8,6 Reps", "4 Sets (Standard Pyramid)"));//Gif
            dkMetcalfWorkouts.add(new AthleteWorkouts("Chest", "Face Pulls", R.drawable.workout_benchpress, "12-15,5,5,5 Reps", "4 Sets (Standard Pyramid)"));//Gif
            dkMetcalfWorkouts.add(new AthleteWorkouts("Lower Body + Abs", "Split Squats", R.drawable.workout_benchpress, "6,8,10 Reps", "3 Sets (Reverse Pyramid)"));//Gif
            dkMetcalfWorkouts.add(new AthleteWorkouts("Lower Body + Abs", "Romainian Deadlifts", R.drawable.workout_benchpress, "6,8,10 Reps", "3 Sets (Reverse Pyramid)"));//Gif
            dkMetcalfWorkouts.add(new AthleteWorkouts("Lower Body + Abs", "Leg Extensions", R.drawable.workout_benchpress, "6,8,10 Reps", "3 Sets (Reverse Pyramid)"));//Gif
            dkMetcalfWorkouts.add(new AthleteWorkouts("Lower Body + Abs", "Calf Raises", R.drawable.workout_benchpress, "10-15 Reps", "3 Sets"));//Gif
            dkMetcalfWorkouts.add(new AthleteWorkouts("Lower Body + Abs", "Hanging Leg Raises", R.drawable.workout_benchpress, "15 Reps", "3 Sets"));//Gif
            dkMetcalfWorkouts.add(new AthleteWorkouts("Lower Body + Abs", "Ab Wheel Rollouts", R.drawable.workout_benchpress, "15 Reps", "3 Sets"));//Gif
            dkMetcalfWorkouts.add(new AthleteWorkouts("Shoulder/Back", "Standing Weight Press", R.drawable.workout_benchpress, "6,8,10 Reps", "3 Sets"));//Gif
            dkMetcalfWorkouts.add(new AthleteWorkouts("Shoulder/Back", "Weighted Pullups", R.drawable.workout_benchpress, "6,8,10 Reps", "3 Sets"));//Gif
            dkMetcalfWorkouts.add(new AthleteWorkouts("Shoulder/Back", "Weighted Dips", R.drawable.workout_benchpress, "8,10 Reps", "2 Sets"));//Gif
            dkMetcalfWorkouts.add(new AthleteWorkouts("Shoulder/Back", "Cable Rows", R.drawable.workout_benchpress, "8,10 Reps", "2 Sets"));//Gif
            dkMetcalfWorkouts.add(new AthleteWorkouts("Shoulder/Back", "Lateral Raises", R.drawable.workout_benchpress, "15,5,5,8 Reps", "4 Sets"));//Gif

            allAthleteWorkouts.add(i+1, new AllWorkouts("DK Metcalf", dkMetcalfWorkouts));


    //Diets
            allAthleteDiets.clear();
            i = 0;
            Log.i(TAG,"Declaring Diets");

            final ArrayList<AthleteWorkouts> ronaldoDiet = new ArrayList<>();
            ronaldoDiet.clear();
            ronaldoDiet.add(new AthleteWorkouts("null", "Breakfast", R.drawable.ham_and_cheese, "Cereal or Ham & Cheese + Low-Fat Yogurt", "* protein and fat"));
            ronaldoDiet.add(new AthleteWorkouts("null", "Snack #1", R.drawable.seared_tuna, "Tuna & Fruit/Greens", "* protein + antioxidants"));
            ronaldoDiet.add(new AthleteWorkouts("null", "Lunch", R.drawable.chicken_salad, "Chicken & Salad or Pasta & Veggies ", "* lean protein and greens"));
            ronaldoDiet.add(new AthleteWorkouts("null", "Snack #2", R.drawable.avocado_toast, "Avocado Toast & or Protein Shake", "* Fat, Fiber, Protein, Essential Vitamins"));
            ronaldoDiet.add(new AthleteWorkouts("null", "Dinner #1", R.drawable.steak, "Fish/Chicken/Steak w/ salad", "* lean protein + greens"));

            allAthleteDiets.add(i, new AllWorkouts("C. Ronaldo", ronaldoDiet));
        }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.footerLayout){
            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
            try {
                Intent launchIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName));
                launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(launchIntent);

            } catch (android.content.ActivityNotFoundException e) {
                Intent launchIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName));
                launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(launchIntent);

            }
        }
    }
}