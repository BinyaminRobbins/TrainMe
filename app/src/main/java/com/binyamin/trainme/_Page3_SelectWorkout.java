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

public class _Page3_SelectWorkout extends AppCompatActivity implements Runnable {
    int backButtonCount;
    static Context context;
    private Thread t;
    private Thread t2;
    static ArrayList<AllWorkouts> allAthleteWorkouts = new ArrayList<>();
    static ArrayList<AllWorkouts> allAthleteDiets = new ArrayList<>();
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
        Toast.makeText(context, "You Can Disable Auto-Scroll in Settings", Toast.LENGTH_LONG).show();
        sharedPreferences = this.getSharedPreferences("com.binyamin.trainme", Context.MODE_PRIVATE);

        t = new Thread(this, "DeclareWorkouts");
        t.run();
        t2 = new Thread(this, "DeclareDiets");
        t2.run();

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        backButtonCount = 0;

        navigationView = findViewById(R.id.navigationView);
        navigationView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        navController = Navigation.findNavController(this, R.id.navHostFragment);
        NavigationUI.setupWithNavController(navigationView, navController);

        drawerLayout = findViewById(R.id.drawerLayout);

        final PurchaseProduct product = new PurchaseProduct(context, _Page3_SelectWorkout.this, getResources().getString(R.string.productId), sharedPreferences);
        product.setUp();
        if(!product.checkIfOwned()) {

            navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
                @Override
                public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                    if (destination.getId() == R.id.menuNavigation_youtube) {
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
                    }
                }
            });
            sliderHandler = new Handler();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        backButtonCount = 0;
        t = new Thread(this, "DeclareWorkouts");
        t.run();
        t2 = new Thread(this, "DeclareDiets");
        t2.run();
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
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
    //Daily Workout
    @Override
    public void run() {
        if (t.getName().equals("DeclareWorkouts")) {
            int i = 0;
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

            allAthleteWorkouts.add(i, new AllWorkouts("Tom Brady", bradyWorkouts));

            final ArrayList<AthleteWorkouts> lebronWorkouts = new ArrayList<>();
            lebronWorkouts.clear();

            lebronWorkouts.add(new AthleteWorkouts("Mixed", "Push-Ups", R.drawable.workout_pushup, "Till-Failure", "3 Sets (45s Rest)"));
            lebronWorkouts.add(new AthleteWorkouts("Mixed", "Pull-Ups", R.drawable.workout_pullup, "10 Reps", "3 Sets (45s Rest)"));
            lebronWorkouts.add(new AthleteWorkouts("Mixed", "KettleBell Snatches", R.drawable.dumbbell_snatch, "5 Reps (Per Arm)", "3 Sets (45s Rest)")); //need to get gif resource for "Dumbbell Snatches"
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

            allAthleteWorkouts.add(i++, new AllWorkouts("Lebron James", lebronWorkouts));

            final ArrayList<AthleteWorkouts> mcGregorWorkouts = new ArrayList<>();
            mcGregorWorkouts.clear();

            mcGregorWorkouts.add(new AthleteWorkouts("BodyWeight", "Chin-Ups", R.drawable.chin_ups, "1 - minute", "5 Sets (2 - 3 min Rest)")); //Gif
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

            allAthleteWorkouts.add(i++, new AllWorkouts("Connor McGregor", mcGregorWorkouts));

            final ArrayList<AthleteWorkouts> zlatanWorkouts = new ArrayList<>();
            zlatanWorkouts.clear();

            zlatanWorkouts.add(new AthleteWorkouts("Daily Workout", "Dead-lift", R.drawable.workout_squat, "5 Reps", "4 Sets (2 min rest)")); //Gif
            zlatanWorkouts.add(new AthleteWorkouts("Daily Workout", "Barbell Squats", R.drawable.workout_squat, "3 Reps", "2 Sets (2 min rest)")); //Gif
            zlatanWorkouts.add(new AthleteWorkouts("Daily Workout", "Push Press", R.drawable.workout_squat, "3 Reps", "3 Sets (1 min rest)")); //Gif
            zlatanWorkouts.add(new AthleteWorkouts("Daily Workout", "Bench Press", R.drawable.workout_squat, "5 Reps", "4 Sets (2 min rest)")); //Gif
            zlatanWorkouts.add(new AthleteWorkouts("Daily Workout", "Pull-Ups", R.drawable.workout_squat, "15 Reps", "2 Sets (90s rest)")); //Gif
            zlatanWorkouts.add(new AthleteWorkouts("Daily Workout", "Weighted Floor Wipers", R.drawable.workout_squat, "15 Reps (each side)", "2 Sets (90s rest)")); //Gif

            allAthleteWorkouts.add(i++, new AllWorkouts("Zlatan Ibrah.", zlatanWorkouts));


            final ArrayList<AthleteWorkouts> judgeWorkouts = new ArrayList<>();
            judgeWorkouts.clear();

            judgeWorkouts.add(new AthleteWorkouts("Daily Workout", "Tire Jumps", R.drawable.workout_squat, "Till Fatigued", "Till Fatigued")); //Gif
            judgeWorkouts.add(new AthleteWorkouts("Daily Workout", "Bench Press", R.drawable.workout_squat, "Limit", "3-4 Sets")); //Gif
            judgeWorkouts.add(new AthleteWorkouts("Daily Workout", "Sled Pushes", R.drawable.workout_squat, "Limit", "Limit")); //Gif
            judgeWorkouts.add(new AthleteWorkouts("Daily Workout", "Barbell Squats", R.drawable.workout_squat, "Limit", "3-4 Sets")); //Gif
            judgeWorkouts.add(new AthleteWorkouts("Daily Workout", "Box Jumps", R.drawable.workout_squat, "Till Fatigued", "Till Fatigued")); //Gif
            judgeWorkouts.add(new AthleteWorkouts("Daily Workout", "Yoga / Pilates", R.drawable.workout_squat, "30 min", "Every Day")); //Gif

            allAthleteWorkouts.add(i++, new AllWorkouts("Aaron Judge", judgeWorkouts));

            final ArrayList<AthleteWorkouts> ronaldoWorkouts = new ArrayList<>();
            ronaldoWorkouts.clear();

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

            allAthleteWorkouts.add(i++, new AllWorkouts("Cristiano Ronaldo", ronaldoWorkouts));

            final ArrayList<AthleteWorkouts> jamesHarrisonWorkouts = new ArrayList<>();
            jamesHarrisonWorkouts.clear();

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
            paulGeorgeWorkouts.clear();

            paulGeorgeWorkouts.add(new AthleteWorkouts("Chest", "Bench Press", R.drawable.workout_benchpress, "10 Reps", "10 Sets"));

            allAthleteWorkouts.add(i+1, new AllWorkouts("Paul George", paulGeorgeWorkouts));


        } else if (t.getName().equals("DeclareDiets")) {
            //Setup Diets
            allAthleteDiets.clear();

            final ArrayList<AthleteWorkouts> ronaldoDiet = new ArrayList<>();
            ronaldoDiet.clear();
            ronaldoDiet.add(new AthleteWorkouts(null, "Breakfast", R.drawable.ham_and_cheese, "Cereal or Ham & Cheese + Low-Fat Yogurt", "* protein and fat"));
            ronaldoDiet.add(new AthleteWorkouts(null, "Snack #1", R.drawable.seared_tuna, "Tuna & Fruit/Greens", "* protein + antioxidants"));
            ronaldoDiet.add(new AthleteWorkouts(null, "Lunch", R.drawable.chicken_salad, "Chicken & Salad or Pasta & Veggies ", "* lean protein and greens"));
            ronaldoDiet.add(new AthleteWorkouts(null, "Snack #2", R.drawable.avocado_toast, "Avocado Toast & or Protein Shake", "* Fat, Fiber, Protein, Essential Vitamins"));
            ronaldoDiet.add(new AthleteWorkouts(null, "Dinner #1", R.drawable.steak, "Fish/Chicken/Steak w/ salad", "* lean protein + greens"));

            allAthleteDiets.add(0, new AllWorkouts("C. Ronaldo", ronaldoDiet));

        }
    }
}