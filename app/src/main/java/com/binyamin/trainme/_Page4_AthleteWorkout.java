package com.binyamin.trainme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class _Page4_AthleteWorkout extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    ArrayList<_3_SliderItem> myList = new ArrayList<>();
    ImageView backButton;
    static int tagNum;
    Spinner spinner;
    RecyclerView recyclerView;
    _4_RecyclerViewAdapter adapter;
    ArrayList<String> workoutCategories = new ArrayList<>();
    ArrayList<AthleteWorkouts> athleteWorkoutList = new ArrayList<>();
    ArrayList<AthleteWorkouts> selectWorkoutList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4_athlete_workout);

        String TAG = "Activity 4";

        Intent intent = getIntent();
        tagNum = Integer.valueOf(Objects.requireNonNull(intent.getStringExtra("tag")));

        int tabPosition = AthleteWorkoutsAndDietsFragment.tabPosition;
        if(tabPosition == 0) {
            //Workout ArrayList
            try {
                athleteWorkoutList = _Page3_SelectWorkout.allAthleteWorkouts.get(tagNum).getAthleteWorkoutArrayList();

            }catch(NullPointerException e){
                e.printStackTrace();
            }
        }else {
            //Diet ArrayList
            try {
                athleteWorkoutList = _Page3_SelectWorkout.allAthleteDiets.get(tagNum).getAthleteWorkoutArrayList();
            }catch(NullPointerException e){
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

        adapter = new _4_RecyclerViewAdapter(athleteWorkoutList);
        recyclerView = findViewById(R.id.workoutRV);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        SharedPreferences prefs = getSharedPreferences("com.binyamin.trainme",Context.MODE_PRIVATE);
        SliderList sliderList = new SliderList(getApplicationContext(),prefs);
        ImageView imageViewHeader = findViewById(R.id.imageViewHeader);

        if(tabPosition == 0) {
            workoutCategories.clear();
            for (AthleteWorkouts workout : athleteWorkoutList) {
                String category = workout.getCategory();
                if (!workoutCategories.contains(category)) {
                    workoutCategories.add(category);
                }
            }
            myList = sliderList.getWorkoutList();
        }else{
            myList = sliderList.getDietList();
        }

        _3_SliderItem sliderItem = myList.get(tagNum);

        imageViewHeader.setImageResource(sliderItem.getImage());
            final Matrix matrix = imageViewHeader.getImageMatrix();
            final float imageWidth = imageViewHeader.getDrawable().getIntrinsicWidth();
            final int screenWidth = getResources().getDisplayMetrics().widthPixels;
            final float scaleRatio = screenWidth / imageWidth;
            matrix.postScale(scaleRatio, scaleRatio - 0.025f);
            imageViewHeader.setImageMatrix(matrix);

            TextView athleteName = findViewById(R.id.textViewAthleteName);
            String name = sliderItem.getAthleteName();
            athleteName.setText(name);

            backButton = findViewById(R.id.backButton);
            backButton.setOnClickListener(this);
            spinner = findViewById(R.id.category_spinner);

        TextView tvLink = findViewById(R.id.tv_link);
        final String linkInfo;

        if(tabPosition == 0) {
            MyAdapter spinnerAdapter = new MyAdapter(this, R.layout.custom_spinner_4_, workoutCategories);

            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(spinnerAdapter);
            spinner.setOnItemSelectedListener(this);
            spinnerAdapter.notifyDataSetChanged();

            tvLink.setText(name + " Workout List Source");
            linkInfo = sliderList.getWorkoutList().get(tagNum).getLink();
        }else{
            spinner.setVisibility(View.GONE);

            tvLink.setText(name + " Diet List Source");
            linkInfo = sliderList.getDietList().get(tagNum).getLink();
        }

        tvLink.setPaintFlags(tvLink.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkInfo));
                browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(browserIntent);
            }
        });
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

            String currentItem = workoutCategories.get(position);
            for (AthleteWorkouts workout : athleteWorkoutList) {
                if (workout.getCategory().equals(currentItem)) {
                    selectWorkoutList.add(workout);
                }
            }
            adapter = new _4_RecyclerViewAdapter(selectWorkoutList);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


        public class MyAdapter extends ArrayAdapter {

            public MyAdapter(Context context, int textViewResourceId,
                             ArrayList objects) {
                super(context, textViewResourceId, objects);

            }

            private View getCustomView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.custom_spinner_4_, parent, false);

                TextView categoryTXT = layout.findViewById(R.id.textViewCategoryName);


               if(workoutCategories.size() > 0 && position < workoutCategories.size()) {
                   for (int i = 0; i < workoutCategories.size(); i++){
                           categoryTXT.setText(workoutCategories.get(position));
                   }
               }

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

    @Override
    protected void onResume() {
        super.onResume();
        boolean screenOn = getSharedPreferences("com.binyamin.trainme",Context.MODE_PRIVATE).getBoolean("screenOn", true);
        if (screenOn) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }
}
