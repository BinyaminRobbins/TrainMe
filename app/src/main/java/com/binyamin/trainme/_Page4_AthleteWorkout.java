package com.binyamin.trainme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static com.binyamin.trainme._Page2_UserDetails.recyclerView;

public class _Page4_AthleteWorkout extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    ArrayList<_3_SliderItem> myList = new ArrayList<>();
    ImageView backButton;
    static int tagNum;
    Spinner spinner;
    RecyclerView recyclerView;
    _4_RecyclerViewAdapter adapter;
    ArrayList<String> workoutCategories = new ArrayList<>();
    ArrayList<AthleteWorkouts> athleteWorkoutList;
    ArrayList<AthleteWorkouts> selectWorkoutList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4_athlete_workout);

        Intent intent = getIntent();
        tagNum = Integer.valueOf(intent.getStringExtra("tag"));
        Log.i("tagnum", String.valueOf(tagNum));

        athleteWorkoutList = _Page3_SelectWorkout.allAthleteWorkouts.get(tagNum).getAthleteWorkoutArrayList();
        recyclerView = findViewById(R.id.workoutRV);


        adapter = new _4_RecyclerViewAdapter(selectWorkoutList);
        LinearLayoutManager manager = new _2_RecyclerViewScrollSpeed(getApplicationContext(), _2_RecyclerViewScrollSpeed.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        //workoutCategories.clear();
        for (AthleteWorkouts workouts : _Page3_SelectWorkout.allAthleteWorkouts.get(tagNum).getAthleteWorkoutArrayList()) {
            String category = workouts.getCategory();
            Log.i("Category",category);
            if (!workoutCategories.contains(category)) {
                workoutCategories.add(category);
            }

            ImageView imageViewHeader = findViewById(R.id.imageViewHeader);

            SQLiteDatabase database = getApplicationContext().openOrCreateDatabase("Workouts",MODE_PRIVATE,null);
            SliderList sliderList = new SliderList(database);
            myList = sliderList.getSliderList();

            _3_SliderItem sliderItem = myList.get(tagNum);

            imageViewHeader.setImageResource(sliderItem.getImage());
            final Matrix matrix = imageViewHeader.getImageMatrix();
            final float imageWidth = imageViewHeader.getDrawable().getIntrinsicWidth();
            final int screenWidth = getResources().getDisplayMetrics().widthPixels;
            final float scaleRatio = screenWidth / imageWidth;
            matrix.postScale(scaleRatio, scaleRatio - 0.025f);
            imageViewHeader.setImageMatrix(matrix);

            TextView athleteName = findViewById(R.id.textViewAthleteName);
            athleteName.setText(sliderItem.getAthleteName());

            backButton = findViewById(R.id.backButton);
            backButton.setOnClickListener(this);

            spinner = findViewById(R.id.category_spinner);
            String[] myArray = new String[_Page3_SelectWorkout.allAthleteWorkouts.size()];
            MyAdapter spinnerAdapter = new MyAdapter(this, R.layout.custom_spinner_4_, workoutCategories.toArray(myArray));
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(spinnerAdapter);
            spinner.setOnItemSelectedListener(this);
            spinnerAdapter.notifyDataSetChanged();


        }
    }

        @Override
        public void onBackPressed () {
            super.onBackPressed();
            finish();
        }

        @Override
        public void onClick (View v){
            if (v.getId() == backButton.getId()) {
                finish();
            }
        }

        @Override
        public void onItemSelected (AdapterView <?> parent, View view,int position, long id){
        selectWorkoutList.clear();
            Log.i("Item Selected", parent.getItemAtPosition(position).toString());
            String currentItem = workoutCategories.get(position);
            Log.i("CurrentItem", currentItem);
            for (AthleteWorkouts workout : athleteWorkoutList) {
                if (workout.getCategory().equals(currentItem)) {
                    Log.i("CurrentWorkoutCategory", workout.getCategory());
                    selectWorkoutList.add(workout);
                    adapter.notifyDataSetChanged();
                }
            }
            adapter.notifyDataSetChanged();
        }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


        public class MyAdapter extends ArrayAdapter {

            public MyAdapter(Context context, int textViewResourceId,
                             String[] objects) {
                super(context, textViewResourceId, objects);

            }

            public View getCustomView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.custom_spinner_4_, parent, false);

                TextView categoryTXT = layout.findViewById(R.id.textViewCategoryName);


               if(workoutCategories.size() > 0) {
                   categoryTXT.setText(workoutCategories.get(position));
               }
               else return null;


                return layout;
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                return getCustomView(position, convertView, parent);
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return getCustomView(position, convertView, parent);
            }
        }
    }
