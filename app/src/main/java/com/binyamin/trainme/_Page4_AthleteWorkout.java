package com.binyamin.trainme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.binyamin.trainme._Page2_UserDetails.recyclerView;

public class _Page4_AthleteWorkout extends AppCompatActivity implements View.OnClickListener {
    static final ArrayList<_3_SliderItem> list = _Page3_SelectWorkout.list;
    ImageView backButton;
    static int tagNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4_athlete_workout);

        Intent intent = getIntent();
        tagNum = Integer.valueOf(intent.getStringExtra("tag"));

        ImageView imageViewHeader = findViewById(R.id.imageViewHeader);

        _3_SliderItem sliderItem = list.get(tagNum);

        imageViewHeader.setImageResource(sliderItem.getImage());
        final Matrix matrix = imageViewHeader.getImageMatrix();
        final float imageWidth = imageViewHeader.getDrawable().getIntrinsicWidth();
        final int screenWidth = getResources().getDisplayMetrics().widthPixels;
        final float scaleRatio = screenWidth / imageWidth;
        matrix.postScale(scaleRatio, scaleRatio - 0.05f);
        imageViewHeader.setImageMatrix(matrix);

        TextView athleteName = findViewById(R.id.textViewAthleteName);
        athleteName.setText(sliderItem.getAthleteName());

         backButton = findViewById(R.id.backButton);
         backButton.setOnClickListener(this);

         initRecyclerView();

    }
    public void initRecyclerView(){
            ArrayList<AthleteWorkouts> athleteWorkoutList = _Page3_SelectWorkout.allAthleteWorkouts.get(tagNum).getAthleteWorkoutArrayList();
            RecyclerView recyclerView = findViewById(R.id.workoutRV);
            LinearLayoutManager manager = new _2_RecyclerViewScrollSpeed(this, _2_RecyclerViewScrollSpeed.VERTICAL, false);
            recyclerView.setLayoutManager(manager);
            _4_RecyclerViewAdapter adapter = new _4_RecyclerViewAdapter(athleteWorkoutList);
            recyclerView.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == backButton.getId()){
            finish();
        }
    }
}
