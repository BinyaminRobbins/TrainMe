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

        final PurchaseProduct product = new PurchaseProduct(context, _Page3_SelectWorkout.this, getResources().getString(R.string.inapp_productId), sharedPreferences);
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
        PurchaseProduct product = new PurchaseProduct(this, _Page3_SelectWorkout.this, getResources().getString(R.string.inapp_productId), sharedPreferences);
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
                ArrayList<_3_SliderItem> sliderItems;
                if(AthleteWorkoutsAndDietsFragment.tabPosition == 0){
                    sliderItems = sliderList.getWorkoutList();
                }else {
                    sliderItems = sliderList.getWorkoutList();
                }
                for (_3_SliderItem item : sliderItems) {
                    String athleteName = item.getAthleteName().toLowerCase();
                    if (athleteName.matches(query.toLowerCase()) || athleteName.contains(query.toLowerCase())) {
                        if(AthleteWorkoutsAndDietsFragment.tabPosition == 0){
                            AllWorkoutsFragment.viewPager2.setCurrentItem(item.getTagNum(), true);
                        }else if(AthleteWorkoutsAndDietsFragment.tabPosition == 1){
                            AllDietsFragment.viewPager2.setCurrentItem(item.getTagNum() - 1, true);
                        }
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
            allAthleteWorkouts.clear();
            final ArrayList<AthleteWorkouts> bradyWorkouts = new ArrayList<>();
            final ArrayList<AthleteWorkouts> lebronWorkouts = new ArrayList<>();
            final ArrayList<AthleteWorkouts> mcGregorWorkouts = new ArrayList<>();
            final ArrayList<AthleteWorkouts> paulGeorgeWorkouts = new ArrayList<>();
            final ArrayList<AthleteWorkouts> jimmyButlerWorkouts = new ArrayList<>();
            final ArrayList<AthleteWorkouts> ronaldoWorkouts = new ArrayList<>();
            final ArrayList<AthleteWorkouts> neymarWorkouts = new ArrayList<>();
            final ArrayList<AthleteWorkouts> zlatanWorkouts = new ArrayList<>();
            final ArrayList<AthleteWorkouts> antonioBrownWorkouts = new ArrayList<>();
            final ArrayList<AthleteWorkouts> jamesHarrisonWorkouts = new ArrayList<>();
            final ArrayList<AthleteWorkouts> judgeWorkouts = new ArrayList<>();
            final ArrayList<AthleteWorkouts> jjWattWorkouts = new ArrayList<>();
            final ArrayList<AthleteWorkouts> dkMetcalfWorkouts = new ArrayList<>();
            final ArrayList<AthleteWorkouts> julianEdelmanWorkouts = new ArrayList<>();

            allAthleteWorkouts.add(new AllWorkouts("Tom Brady", bradyWorkouts));
            allAthleteWorkouts.add(new AllWorkouts("Lebron James", lebronWorkouts));
            allAthleteWorkouts.add(new AllWorkouts("Connor McGregor", mcGregorWorkouts));
            allAthleteWorkouts.add(new AllWorkouts("Paul George", paulGeorgeWorkouts));
            allAthleteWorkouts.add(new AllWorkouts("Jimmy Butler", jimmyButlerWorkouts));
            allAthleteWorkouts.add(new AllWorkouts("Antonio Brown", antonioBrownWorkouts));
            allAthleteWorkouts.add(new AllWorkouts("DK Metcalf", dkMetcalfWorkouts));
            allAthleteWorkouts.add(new AllWorkouts("James Harrison", jamesHarrisonWorkouts));
            allAthleteWorkouts.add(new AllWorkouts("J.J. Watt", jjWattWorkouts));
            allAthleteWorkouts.add(new AllWorkouts("Julian Edelman", julianEdelmanWorkouts));
            allAthleteWorkouts.add(new AllWorkouts("Cristiano Ronaldo", ronaldoWorkouts));
            allAthleteWorkouts.add(new AllWorkouts("Neymar",neymarWorkouts));
            allAthleteWorkouts.add(new AllWorkouts("Zlatan Ibrah.", zlatanWorkouts));
            allAthleteWorkouts.add(new AllWorkouts("Aaron Judge", judgeWorkouts));


            bradyWorkouts.add(new AthleteWorkouts("Full Body", "Standing Band Rows", R.drawable.workout_pushup, "10 Reps", "3 Sets (30s Rest)")); //Need GIF for "Standing Resistance Band Row";
            bradyWorkouts.add(new AthleteWorkouts("Full Body", "Banded Push-Up", R.drawable.workout_pullup, "7 Reps", "3 Sets (30s Rest)"));//Gif for "BANDED push-up"
            bradyWorkouts.add(new AthleteWorkouts("Full Body", "Banded Core Rotations", R.drawable.workout_pullup, "7 Reps (Each Side)", "3 Sets (10s Rest)"));//"Banded core rotations" gif
            bradyWorkouts.add(new AthleteWorkouts("Full Body", "Banded Deadlifts", R.drawable.workout_pushup, "8 Reps", "3 Sets (15s Rest)"));//Banded DeadLift Gif
            bradyWorkouts.add(new AthleteWorkouts("Full Body", "Banded Bicep Curl", R.drawable.workout_bandedbicepcurls, "12 Reps", "3 Sets (10s Rest)"));
            bradyWorkouts.add(new AthleteWorkouts("Full Body", "Banded Tricep Extension", R.drawable.workout_bandedbicepcurls, "10 Reps", "3 Sets (15s Rest)"));//need gif
            bradyWorkouts.add(new AthleteWorkouts("Full Body", "Deceleration Lunges", R.drawable.workout_pullup, "10 Reps", "3 Sets (30s Rest)")); //need gif
            bradyWorkouts.add(new AthleteWorkouts("Full Body", "Banded Shoulder Press", R.drawable.workout_pullup, "10 Reps", "3 Sets (10s Rest)")); //need Gif
            bradyWorkouts.add(new AthleteWorkouts("Full Body", "X Band Squat", R.drawable.workout_pullup, "10 Reps", "3 Sets (10s Rest)")); //Need Gif

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

            zlatanWorkouts.add(new AthleteWorkouts("Daily Workout", "Dead-lift", R.drawable.workout_squat, "5 Reps", "4 Sets (2 min rest)")); //Gif
            zlatanWorkouts.add(new AthleteWorkouts("Daily Workout", "Barbell Squats", R.drawable.workout_squat, "3 Reps", "2 Sets (2 min rest)")); //Gif
            zlatanWorkouts.add(new AthleteWorkouts("Daily Workout", "Push Press", R.drawable.workout_squat, "3 Reps", "3 Sets (1 min rest)")); //Gif
            zlatanWorkouts.add(new AthleteWorkouts("Daily Workout", "Bench Press", R.drawable.workout_squat, "5 Reps", "4 Sets (2 min rest)")); //Gif
            zlatanWorkouts.add(new AthleteWorkouts("Daily Workout", "Pull-Ups", R.drawable.workout_squat, "15 Reps", "2 Sets (90s rest)")); //Gif
            zlatanWorkouts.add(new AthleteWorkouts("Daily Workout", "Weighted Floor Wipers", R.drawable.workout_squat, "15 Reps (each side)", "2 Sets (90s rest)")); //Gif

            judgeWorkouts.add(new AthleteWorkouts("Daily Workout", "Tire Jumps", R.drawable.workout_squat, "Till Fatigued", "Till Fatigued")); //Gif
            judgeWorkouts.add(new AthleteWorkouts("Daily Workout", "Bench Press", R.drawable.workout_squat, "Limit", "3-4 Sets")); //Gif
            judgeWorkouts.add(new AthleteWorkouts("Daily Workout", "Sled Pushes", R.drawable.workout_squat, "Limit", "Limit")); //Gif
            judgeWorkouts.add(new AthleteWorkouts("Daily Workout", "Barbell Squats", R.drawable.workout_squat, "Limit", "3-4 Sets")); //Gif
            judgeWorkouts.add(new AthleteWorkouts("Daily Workout", "Box Jumps", R.drawable.workout_squat, "Till Fatigued", "Till Fatigued")); //Gif
            judgeWorkouts.add(new AthleteWorkouts("Daily Workout", "Yoga / Pilates", R.drawable.workout_squat, "30 min", "Every Day")); //Gif

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

            neymarWorkouts.add(new AthleteWorkouts("Warm-Up", "Stretch", R.drawable.workout_squat, "10 mins", "")); //Gif
            neymarWorkouts.add(new AthleteWorkouts("Warm-Up", "Jog", R.drawable.workout_run, "800 meters", "")); //Gif
            neymarWorkouts.add(new AthleteWorkouts("Circuit (Perform x4)", "Jog", R.drawable.workout_run, "400 meters", "")); //Gif
            neymarWorkouts.add(new AthleteWorkouts("Circuit (Perform x4)", "Jump Squats", R.drawable.workout_squat, "15 Reps", "")); //Gif
            neymarWorkouts.add(new AthleteWorkouts("Circuit (Perform x4)", "Jump Rope", R.drawable.workout_squat, "100 Jumps", "")); //Gif
            neymarWorkouts.add(new AthleteWorkouts("Circuit (Perform x4)", "Sprint", R.drawable.workout_run, "100 meters", "")); //Gif
            neymarWorkouts.add(new AthleteWorkouts("Circuit (Perform x4)", "Box Jump", R.drawable.workout_boxjump, "15 Reps", ""));
            neymarWorkouts.add(new AthleteWorkouts("Day #1", "Back Squats", R.drawable.workout_boxjump, "12 Reps", "4 Sets")); //Gif
            neymarWorkouts.add(new AthleteWorkouts("Day #1", "Weighted Lunges", R.drawable.workout_boxjump, "12 Reps", "4 Sets")); //Gif
            neymarWorkouts.add(new AthleteWorkouts("Day #1", "Mountain Climbers", R.drawable.workout_mountainclimbers, "25 Reps", "4"));
            neymarWorkouts.add(new AthleteWorkouts("Day #1", "Plank Holds", R.drawable.workout_boxjump, "60s Each", "4 Sets")); //Gif
            neymarWorkouts.add(new AthleteWorkouts("Day #1", "Jog", R.drawable.workout_boxjump, "10 min", "1 (cool-down)")); //Gif
            neymarWorkouts.add(new AthleteWorkouts("Day #2", "Front Squats", R.drawable.workout_boxjump, "12 Reps", "4 Sets")); //Gif
            neymarWorkouts.add(new AthleteWorkouts("Day #2", "Leg Press", R.drawable.workout_boxjump, "12 Reps", "4 Sets")); //Gif
            neymarWorkouts.add(new AthleteWorkouts("Day #2", "Leg Raises", R.drawable.workout_boxjump, "25 Reps", "4 Sets")); //Gif
            neymarWorkouts.add(new AthleteWorkouts("Day #2", "Plank Holds", R.drawable.workout_boxjump, "60s Each", "4 Sets")); //Gif
            neymarWorkouts.add(new AthleteWorkouts("Day #2", "Jog", R.drawable.workout_boxjump, "10 min", "1 (cool-down)")); //Gif
            neymarWorkouts.add(new AthleteWorkouts("Day #3 (Cardio)", "Run", R.drawable.running_gif, "3 miles", ""));
            neymarWorkouts.add(new AthleteWorkouts("Day #3 (Cardio)", "60min On/Off Cardio:", 0, "", "")); //Gif
            neymarWorkouts.add(new AthleteWorkouts("Day #3 (Cardio)", "ON - Run 6-9mph", R.drawable.running_gif, "1 Min", "")); //Gif
            neymarWorkouts.add(new AthleteWorkouts("Day #3 (Cardio)", "OFF - Run 2.5-4mph", R.drawable.workout_run, "1 Min", "")); //Gif

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

            jimmyButlerWorkouts.add(new AthleteWorkouts("Day #1", "Neck Press", R.drawable.workout_benchpress, "10 Reps", "5 Sets"));//Gif
            jimmyButlerWorkouts.add(new AthleteWorkouts("Day #1", "Incline Dumbbell Press", R.drawable.workout_benchpress, "12 Reps", "3 Sets"));//Gif
            jimmyButlerWorkouts.add(new AthleteWorkouts("Day #1", "Narrow Grip Bench Press", R.drawable.workout_benchpress, "15 Reps", "3 Sets"));//Gif
            jimmyButlerWorkouts.add(new AthleteWorkouts("Day #1", "Cable Fly (low pulley)", R.drawable.workout_benchpress, "10 Reps", "3 Sets"));//Gif
            jimmyButlerWorkouts.add(new AthleteWorkouts("Day #1", "Cable Fly (high pulley)", R.drawable.workout_benchpress, "10 Reps", "3 Sets"));//Gif
            jimmyButlerWorkouts.add(new AthleteWorkouts("Day #1", "Seated Calf Raise", R.drawable.workout_benchpress, "10 Reps", "3 Sets"));//Gif
            jimmyButlerWorkouts.add(new AthleteWorkouts("Day #2", "Wide Grip Pullups", R.drawable.workout_benchpress, "15 Reps", "3 Sets"));//Gif
            jimmyButlerWorkouts.add(new AthleteWorkouts("Day #2", "Cable Pull Down", R.drawable.workout_benchpress, "12 Reps", "3 Sets"));//Gif
            jimmyButlerWorkouts.add(new AthleteWorkouts("Day #2", "Seated Cable Rows", R.drawable.workout_benchpress, "10 Reps", "3 Sets"));//Gif
            jimmyButlerWorkouts.add(new AthleteWorkouts("Day #2", "Chin Ups", R.drawable.chin_ups, "15 Reps", "3 Sets"));//Gif
            jimmyButlerWorkouts.add(new AthleteWorkouts("Day #2", "Incline Sit-Ups", R.drawable.workout_benchpress, "10 Reps", "4 Sets"));//Gif
            jimmyButlerWorkouts.add(new AthleteWorkouts("Day #2", "Leg Curl", R.drawable.workout_benchpress, "10 Reps", "3 Sets"));//Gif
            jimmyButlerWorkouts.add(new AthleteWorkouts("Day #3", "Treadmill Running", R.drawable.workout_benchpress, "20-30 minutes", ""));//Gif
            jimmyButlerWorkouts.add(new AthleteWorkouts("Day #3", "Dumbbell Curls", R.drawable.workout_benchpress, "8 Reps", "3 Sets"));//Gif
            jimmyButlerWorkouts.add(new AthleteWorkouts("Day #3", "Hammer Curls", R.drawable.workout_benchpress, "8 Reps", "3 Sets"));//Gif
            jimmyButlerWorkouts.add(new AthleteWorkouts("Day #3", "Body Squats", R.drawable.workout_benchpress, "10 Reps", "5 Sets"));//Gif
            jimmyButlerWorkouts.add(new AthleteWorkouts("Day #3", "Weighted Dips", R.drawable.workout_benchpress, "10 Reps", "3 Sets"));//Gif
            jimmyButlerWorkouts.add(new AthleteWorkouts("Day #4", "Incline Bench Press", R.drawable.workout_benchpress, "12 Reps", "3 Sets"));//Gif
            jimmyButlerWorkouts.add(new AthleteWorkouts("Day #4", "Narrow Grip Bench Press", R.drawable.workout_benchpress, "15 Reps", "3 Sets"));//Gif
            jimmyButlerWorkouts.add(new AthleteWorkouts("Day #4", "Bar Squats", R.drawable.workout_benchpress, "10 Reps", "5 Sets"));//Gif
            jimmyButlerWorkouts.add(new AthleteWorkouts("Day #4", "Cable Row", R.drawable.workout_benchpress, "10 Reps", "3 Sets"));//Gif
            jimmyButlerWorkouts.add(new AthleteWorkouts("Day #4", "Dumbbell Bench Press", R.drawable.workout_benchpress, "10-12 Reps", "3 Sets"));//Gif
            jimmyButlerWorkouts.add(new AthleteWorkouts("Day #4", "Barbell Curls", R.drawable.workout_benchpress, "8-10 Reps", "4 Sets"));//Gif

            antonioBrownWorkouts.add(new AthleteWorkouts("Cardio","Run",R.drawable.wide_benchpress,"5 Miles","* 3 Days/Week"));
            antonioBrownWorkouts.add(new AthleteWorkouts("Cardio","Swim",R.drawable.wide_benchpress,"Hold Breath For Lung Strength","1-2 days/week (when not running)"));
            antonioBrownWorkouts.add(new AthleteWorkouts("Abs","Ab Exersises",R.drawable.workout_situps,"1000 per day","(v-ups, crunches, leg raises, etc.)"));
            antonioBrownWorkouts.add(new AthleteWorkouts("Shoulders","Lateral Raises",R.drawable.wide_benchpress,"5 Reps","3 Sets"));
            antonioBrownWorkouts.add(new AthleteWorkouts("Shoulders","Y-Raises",R.drawable.wide_benchpress,"5 Reps","3 Sets"));
            antonioBrownWorkouts.add(new AthleteWorkouts("Shoulders","T-Raises",R.drawable.wide_benchpress,"5 Reps","3 Sets"));
            antonioBrownWorkouts.add(new AthleteWorkouts("Lower Body","Box Squats",R.drawable.wide_benchpress,"20 Reps","2-3 Sets"));
            antonioBrownWorkouts.add(new AthleteWorkouts("Lower Body","Barbell Lunges",R.drawable.wide_benchpress,"20 Reps","2-3 Sets"));
            antonioBrownWorkouts.add(new AthleteWorkouts("Lower Body","Lateral Step-ups",R.drawable.wide_benchpress,"20 Reps","2-3 Sets"));

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

            julianEdelmanWorkouts.add(new AthleteWorkouts("Strength", "Bench Press", R.drawable.workout_benchpress, "15,5,5,8 Reps", "4 Sets"));//Gif
            julianEdelmanWorkouts.add(new AthleteWorkouts("Strength", "Deadlift", R.drawable.workout_benchpress, "15,5,5,8 Reps", "4 Sets"));//Gif
            julianEdelmanWorkouts.add(new AthleteWorkouts("Strength", "Bulgarian Single Leg Squat", R.drawable.workout_benchpress, "15,5,5,8 Reps", "4 Sets"));//Gif
            julianEdelmanWorkouts.add(new AthleteWorkouts("Strength", "Romanian Deadlift", R.drawable.workout_benchpress, "15,5,5,8 Reps", "4 Sets"));//Gif
            julianEdelmanWorkouts.add(new AthleteWorkouts("Strength", "Russian Twists", R.drawable.workout_benchpress, "15,5,5,8 Reps", "4 Sets"));//Gif
            julianEdelmanWorkouts.add(new AthleteWorkouts("Strength", "Plyo Push-Ups", R.drawable.workout_benchpress, "15,5,5,8 Reps", "4 Sets"));//Gif
            julianEdelmanWorkouts.add(new AthleteWorkouts("Strength", "Medicine Ball Sit-Ups", R.drawable.workout_benchpress, "15,5,5,8 Reps", "4 Sets"));//Gif
            julianEdelmanWorkouts.add(new AthleteWorkouts("Conditioning", "Medicine Ball Sit-Ups", R.drawable.workout_benchpress, "15,5,5,8 Reps", "4 Sets"));//Gif
            julianEdelmanWorkouts.add(new AthleteWorkouts("Conditioning", "Medicine Ball Sit-Ups", R.drawable.workout_benchpress, "15,5,5,8 Reps", "4 Sets"));//Gif
            julianEdelmanWorkouts.add(new AthleteWorkouts("Conditioning", "Medicine Ball Sit-Ups", R.drawable.workout_benchpress, "15,5,5,8 Reps", "4 Sets"));//Gif
            julianEdelmanWorkouts.add(new AthleteWorkouts("Conditioning", "Medicine Ball Sit-Ups", R.drawable.workout_benchpress, "15,5,5,8 Reps", "4 Sets"));//Gif
            julianEdelmanWorkouts.add(new AthleteWorkouts("Conditioning", "Medicine Ball Sit-Ups", R.drawable.workout_benchpress, "15,5,5,8 Reps", "4 Sets"));//Gif
            julianEdelmanWorkouts.add(new AthleteWorkouts("Conditioning", "Medicine Ball Sit-Ups", R.drawable.workout_benchpress, "15,5,5,8 Reps", "4 Sets"));//Gif
            julianEdelmanWorkouts.add(new AthleteWorkouts("Conditioning", "Medicine Ball Sit-Ups", R.drawable.workout_benchpress, "15,5,5,8 Reps", "4 Sets"));//Gif

        //Diets
            allAthleteDiets.clear();

            final ArrayList<AthleteWorkouts> connorMcGregorDiet = new ArrayList<>();
            final ArrayList<AthleteWorkouts> jjWattDiet = new ArrayList<>();
            final ArrayList<AthleteWorkouts> neymarDiet = new ArrayList<>();
            final ArrayList<AthleteWorkouts> ronaldoDiet = new ArrayList<>();
            final ArrayList<AthleteWorkouts> jimmyBulterDiet = new ArrayList<>();
            final ArrayList<AthleteWorkouts> paulGeorgeDiet = new ArrayList<>();
            final ArrayList<AthleteWorkouts> antonioBrownDiet = new ArrayList<>();
    /*0:*/
            allAthleteDiets.add(new AllWorkouts("C. McGregor", connorMcGregorDiet));
            allAthleteDiets.add(new AllWorkouts("JJ Watt", jjWattDiet));
            allAthleteDiets.add(new AllWorkouts("Neymar", neymarDiet));
            allAthleteDiets.add(new AllWorkouts("C. Ronaldo", ronaldoDiet));
            allAthleteDiets.add(new AllWorkouts("Jimmy Butler", jimmyBulterDiet));
            allAthleteDiets.add(new AllWorkouts("Paul George", paulGeorgeDiet));
            allAthleteDiets.add(new AllWorkouts("Antonio Brown", antonioBrownDiet));

            connorMcGregorDiet.add(new AthleteWorkouts("null", "Breakfast", R.drawable.egg_breakfast, "Coffee, Eggs, Smoked Salmon, Veggies", "* protein, fiber"));
            connorMcGregorDiet.add(new AthleteWorkouts("null", "Snack #1", R.drawable.greek_yogurt, "Greek Yogurt", "* protein, carbs"));
            connorMcGregorDiet.add(new AthleteWorkouts("null", "Lunch", R.drawable.fish_sidesalad, "Red Meat/Fish/Chicken + side salad", "* protein, greens, vitamins"));
            connorMcGregorDiet.add(new AthleteWorkouts("null", "Snack #2 (Pre Workout)", R.drawable.trail_mix, "Nuts, Fruit", "* protein, fiber, fats"));
            connorMcGregorDiet.add(new AthleteWorkouts("null", "Snack #3 (Post Workout)", R.drawable.protein_shake, "Protein Shake", "* protein"));
            connorMcGregorDiet.add(new AthleteWorkouts("null", "Dinner", R.drawable.steak, "Chicken/Steak/Fish, Rice, Greens", "* protein, fiber, vitamins"));
            connorMcGregorDiet.add(new AthleteWorkouts("null", "Late Night", R.drawable.tea, "Green Tea", ""));

            jjWattDiet.add(new AthleteWorkouts("null", "Big Breakfast", R.drawable.eggs_oatmeal, "4-6 eggs & oatmeal", "* protein, fiber"));
            jjWattDiet.add(new AthleteWorkouts("null", "Breakfast 2.0", R.drawable.scrambled_eggs_breakfast, "4-5 eggs", "* protein"));
            jjWattDiet.add(new AthleteWorkouts("null", "Lunch", R.drawable.chicken, "Chicken, Pasta, Veggies", "protein, carbs, nutrients"));
            jjWattDiet.add(new AthleteWorkouts("null", "Pre-Dinner", R.drawable.fish_mp, "Chicken/Fish, mashed potatoes, salad", "protein, carbs, nutrients"));
            jjWattDiet.add(new AthleteWorkouts("null", "Dinner", R.drawable.lamb_chops_veggies, "Lamb Chops, Pasta, Broccoli", "* protein, carbs, nutrients"));

            neymarDiet.add(new AthleteWorkouts("null", "Supplements", R.drawable.whey_protein, "Whey Protein in water", "* protein"));
            neymarDiet.add(new AthleteWorkouts("null", "Breakfast", R.drawable.egg_breakfast, "3 eggs, turkey, spinach", "* protein, greens"));
            neymarDiet.add(new AthleteWorkouts("null", "Mid-Morning", R.drawable.protein_shake, "Protein Shake + Nut Mix", "* protein"));
            neymarDiet.add(new AthleteWorkouts("null", "Lunch", R.drawable.turkey, "Turkey, Sweet Potato, Spinach", "* protein, anti-inflamatories, greens"));
            neymarDiet.add(new AthleteWorkouts("null", "Evening Snack", R.drawable.cold_cuts, "Turkey, Peanut Butter, Sunflower Seeds", "* protein, anti-inflamatories, greens"));
            neymarDiet.add(new AthleteWorkouts("null", "Dinner", R.drawable.fish, "White fish, smoothie, cabbage", "* protein, nutrients, greens"));

            ronaldoDiet.add(new AthleteWorkouts("null", "Breakfast", R.drawable.ham_and_cheese, "Cereal or Ham & Cheese + Low-Fat Yogurt", "* protein and fat"));
            ronaldoDiet.add(new AthleteWorkouts("null", "Snack #1", R.drawable.seared_tuna, "Tuna & Fruit/Greens", "* protein + antioxidants"));
            ronaldoDiet.add(new AthleteWorkouts("null", "Lunch", R.drawable.chicken_salad, "Chicken & Salad or Pasta & Veggies ", "* lean protein and greens"));
            ronaldoDiet.add(new AthleteWorkouts("null", "Snack #2", R.drawable.avocado_toast, "Avocado Toast & or Protein Shake", "* Fat, Fiber, Protein, Essential Vitamins"));
            ronaldoDiet.add(new AthleteWorkouts("null", "Dinner #1", R.drawable.steak, "Fish/Chicken/Steak w/ salad", "* lean protein + greens"));
            ronaldoDiet.add(new AthleteWorkouts("null", "Dessert", R.drawable.tea, "Green/Black Tea", ""));

            jimmyBulterDiet.add(new AthleteWorkouts("null", "Morning Meal", R.drawable.coffee, "Coffee + Banana", "* caffeine + potassium"));
            jimmyBulterDiet.add(new AthleteWorkouts("null", "Breakfast", R.drawable.eggs_oatmeal, "3 eggs, fruit, oatmeal", "* protein + fat/fiber"));
            jimmyBulterDiet.add(new AthleteWorkouts("null", "Lunch", R.drawable.chicken_avocado, "Chicken, Rice, Avocado", "* protein, fat, carbs"));
            jimmyBulterDiet.add(new AthleteWorkouts("null", "Snack", R.drawable.protein_shake, "Protein Bar OR Shake", "* protein"));
            jimmyBulterDiet.add(new AthleteWorkouts("null", "Evening Meal", R.drawable.fish, "Fish + Veggies", "* protein, nutrients"));
            jimmyBulterDiet.add(new AthleteWorkouts("null", "Dinner", R.drawable.steak, "Fish or Beef + Veggies", "* protein, nutrients"));
            jimmyBulterDiet.add(new AthleteWorkouts("null", "Dessert/Pre-Sleep", R.drawable.tea, "Tea + Chocolate", ""));

            paulGeorgeDiet.add(new AthleteWorkouts("null", "Breakfast", R.drawable.egg_breakfast, "Eggs, Avocado, Bacon, Toast", "* fat, carbs, protein"));
            paulGeorgeDiet.add(new AthleteWorkouts("null", "Lunch", R.drawable.steak_sidesalad, "Steak, side salad", "* protein, greens"));
            paulGeorgeDiet.add(new AthleteWorkouts("null", "Dinner", R.drawable.beef_burrito, "Burritos/Chopped Chicken, Beef, cooked veggies", "* MORE protein"));
            paulGeorgeDiet.add(new AthleteWorkouts("null", "Snack", R.drawable.trail_mix, "Trail Mix", "* protein, fiber, fat"));

            antonioBrownDiet.add(new AthleteWorkouts("null", "Breakfast", R.drawable.scrambled_eggs_breakfast, "Six Eggs (2 whole + 4 whites) + Oatmeal", "* protein, fiber"));
            antonioBrownDiet.add(new AthleteWorkouts("null", "Snack #1", R.drawable.greek_yogurt, "Yogurt Parfait", "* protein, fruit"));
            antonioBrownDiet.add(new AthleteWorkouts("null", "Snack #2", R.drawable.guacamole, "Guacamole w/crackers or tortilla chips", "* fat"));
            antonioBrownDiet.add(new AthleteWorkouts("null", "Lunch", R.drawable.chicken_sweetpotato, "Chicken/Pork Chops + Sweet Potato & Greens", "* protein, anti-inflammatories"));
            antonioBrownDiet.add(new AthleteWorkouts("null", "Snack #3", R.drawable.whey_protein, "Protein Shake", "* protein"));
            antonioBrownDiet.add(new AthleteWorkouts("null", "Dinner", R.drawable.steak_sidesalad, "Steak/Bovid, Greens, Sprouted Bread", "* protein, fiber, vitamins"));

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