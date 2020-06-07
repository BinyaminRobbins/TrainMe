package com.binyamin.trainme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Select_A_Workout extends AppCompatActivity {
    /*View myView;
    String[] athleteNames = {"Lebron James","Tom Brady","Connor McGregor","Aaron Judge","Zlatan Ibrahimović"};
    private LinearLayout linearLayout;

    static String[][][] prattWorkout;
    static String[][][] lebronWorkout;
    int currentButton;
    int width;
    Button nextButton;
    Button previousButton;
    HorizontalScrollView scrollView;
    Boolean isScrollable = false;

    public void scrollScreen(final View v) {
        isScrollable = true;
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (v.getId() == R.id.nextButton) {
                        scrollView.smoothScrollBy(width + 100, 0);
                    } else if (v.getId() == R.id.previousButton) {
                        scrollView.smoothScrollBy(-width - 100, 0);
                    }
                } catch (Exception e) {

                }
            }
        });
    }


    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public void seeMore(View v) {
        currentButton = Integer.valueOf(v.getTag().toString());
        Log.i("currentButton", "" + currentButton);
        Intent intent = new Intent(getApplicationContext(), Athlete.class);
        intent.putExtra("currentButton", currentButton);
        startActivity(intent);

    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

      /*  GetValues task = new GetValues();
        task.execute();

        linearLayout = findViewById(R.id.linearLayout);
        width = getScreenWidth(Select_A_Workout.this);

        myView = findViewById(R.id.include);
        myView.setMinimumWidth(width);

        nextButton = findViewById(R.id.nextButton);
        previousButton = findViewById(R.id.previousButton);
        scrollView = findViewById(R.id.horizontalScrollView);

        for(int i = 0; i< athleteNames.length; i++){
            myView = new View(this);
            newView = setTheme(R.layout.custom_select_workout);
            new
            scrollView.addView(newView);
        }

        View newView = new View(this);
        newView.setTag(0);


        scrollView.setOnTouchListener( new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        int childCount = linearLayout.getChildCount();
        Log.i("childCount", Integer.toString(childCount));

        for (int i = 0; i < childCount; i++) {
            ConstraintLayout cl = (ConstraintLayout) linearLayout.getChildAt(i);
            cl.setMinWidth(width);
            cl.setMaxWidth(width);
            switch (i) {
                case 1:
                    TextView tv1 = (TextView) cLayout1.getChildAt(0);
                    tv1.setWidth(width);
                    break;
                case 2:
                    TextView tv2 = (TextView) cLayout2.getChildAt(0);
                    tv2.setWidth(width);
                    break;
                case 3:
                    TextView tv3 = (TextView) cLayout3.getChildAt(0);
                    tv3.setWidth(width);
                    break;
            }
        }

        theRockTitle = findViewById(R.id.titleTxt);
        theRockTitle.setText("The Rock");
        TextView rockDescriptionTxt = findViewById(R.id.descriptionTxt);
        rockDescriptionTxt.setText("(Full-Body Gym Workout)");
        TextView theRockBody = findViewById(R.id.bodyTxt);
        theRockBody.setText("* A compilation of \"The Rocks\" 6 day split workout routine - comprising of Legs, Back, Arms, Chest, Shoulders & Abs.\n\n * Level: Intense");


        prattTitle = findViewById(R.id.titleTextPratt);
        prattTitle.setText("Chris Pratt");
        TextView prattDescription = findViewById(R.id.prattDescription);
        prattDescription.setText("Full-Body Gym Workout");
        TextView prattBody = findViewById(R.id.prattBody);
        prattBody.setText("* Containing \"StarLord's\" 2 part gym workout, that he used to get in shape for Guardians of the Galaxy + warmup.\n\n * Level: Moderate");

        lebronTitle = findViewById(R.id.titleTextlebron);
        lebronTitle.setText("Zac homescreen_lebron");
        TextView lebronDescription = findViewById(R.id.lebronDescription);
        lebronDescription.setText("(Gym Workout)");
        TextView lebronBody = findViewById(R.id.lebronBody);
        lebronBody.setText("* An overview of movie star Zac homescreen_lebron's 3-day, gym, workout split, with a good amount of abs included every day.\n\n* Level: Challenging");

    }

    class WorkoutView extends View {

        public WorkoutView(Context context) {
            super(context);
        }
    }
    public class GetValues extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            //Warmup
            String[][] prattWarmup = {
                    {"Run", "10 min"},
                    {"Pull-Ups", "3 x 5"},
                    {"Push-Ups", "3 x 10"},
                    {"Squats", "3 x 15"}
            };
            //Back,Biceps
            String prattArms[][] = {
                    {"Lat Pull-Down", "6 x 12"},
                    {"Dumbbell Row", "5 x 12"},
                    {"Barbell Curls", "6 x 15"},
                    {"Push-Ups", "6 x 5"},
                    {"Concentration Curls", "3 x 10"}
            };
            //Abs & Cardio/Power Circuit
            String[][] prattCircuit = {
                    {"Run", "0.5 mile"},
                    {"Power Clean/Jerk", "15 reps"},
                    {"Bench Press", "10 Reps"},
                    {"Box Jump", "5 Reps"},
                    {"Sit-Ups", "20 Reps"},
                    {"Russian Twists", "20 Reps"},
                    {"Mountain Climbers", "20 Reps"}
            };
            prattWorkout = new String[][][]{prattWarmup, prattArms, prattCircuit};

            String[][] lebronArms = {
                    {"Straight Arm Pull-Down", "3 x 12"},
                    {"Ab Wheel Roll-Out", "3 x 10"},
                    {"Seated Cable Row", "3 x 10"},
                    {"Neutral-Grip Pull-Up", "3 x 8"},
                    {"Lat Pull-Down", "3 x 10"},
                    {"Chin Up", "3 x 8"},
                    {"Dumbbell Bicep Curls", "3 x 12"},
            };
            //Legs
            String[][] lebronLegs = {
                    {"Leg Press", "3 x 10"},
                    {"Jump Squat", "3 x 10"},
                    {"Reverse Walking Lunge", "3 x 10"},
                    {"Mountain Climbers", "3 x 20"},
                    {"Dumbbell Romanian Deadlifts", "3 x 10"},
                    {"Kickbutts", "3 x 20"},
            };
            //Shoulders, Chest, & Arms
            String[][] lebronChest = {
                    {"Push Ups", "3 x 12"},
                    {"Dumbbell Front Raises", "3 x 8"},
                    {"Cross Body Cable Raise", "3 x 10"},
                    {"Dumbbell Bench Press", "3 x 10"},
                    {"Incline Dumbbell Bench Press", "3 x 10"},
                    {"Dumbbell Overhead Press", "3 x 10"},
                    {"Cable Chest Press", "3 x 10"},
                    {"One-Arm Press-Down", "3 x 8"},
                    {"Bicep Curls", "3 x 12"},
            };
            lebronWorkout = new String[][][]{lebronArms, lebronLegs, lebronChest};
            return null;
        }*/
    }

}