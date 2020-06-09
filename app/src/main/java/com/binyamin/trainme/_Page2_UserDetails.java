package com.binyamin.trainme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class _Page2_UserDetails extends AppCompatActivity {
    static RecyclerView recyclerView;
    static ProgressBar progressBar;
    static ImageView checkmark;
    static Context context;


    private void intitRecyclerView(){
        Log.i("Layout","Initiated");
        recyclerView = findViewById(R.id.signup_recycler_view);
        LinearLayoutManager manager = new _2_RecyclerViewScrollSpeed(this, _2_RecyclerViewScrollSpeed.HORIZONTAL, false){
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        recyclerView.setLayoutManager(manager);
        _2_Signup_RecyclerViewAdapter adapter = new _2_Signup_RecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._2_activity_user_details);

        CardView cv = findViewById(R.id.cv);
        cv.setBackgroundResource(R.drawable.background_custom_cardview);

        progressBar = findViewById(R.id.progressBar);

         checkmark = findViewById(R.id.checkmark);

         context = getApplicationContext();



        intitRecyclerView();


    }
}
