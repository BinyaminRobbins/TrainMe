package com.binyamin.trainme;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Gender extends Fragment implements View.OnClickListener {
    CardView cvMan;
    CardView cvWoman;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;


    public Fragment_Gender() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        sharedPreferences = _Page2_UserDetails.context.getSharedPreferences("com.binyamin.trainme", Context.MODE_PRIVATE);
        cvMan = view.findViewById(R.id.cv_man);
        cvWoman = view.findViewById(R.id.cv_woman);

        cvMan.setOnClickListener(this);
        cvWoman.setOnClickListener(this);

        progressBar = _Page2_UserDetails.progressBar;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_gender, container, false);
    }

    @Override
    public void onClick(final View v) {
        int delay = 800;

        cvMan.setClickable(false);
        cvWoman.setClickable(false);

        _2_ProgressBarAnimation anim = new _2_ProgressBarAnimation(progressBar, progressBar.getProgress(), progressBar.getProgress() + 26);
        anim.setDuration(delay - 400);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment_Completed frag_completed = new Fragment_Completed();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, frag_completed).addToBackStack(null).commit();
                //Fragment added to Frame Layout
            }
        }, delay + 250);

        progressBar.startAnimation(anim);
        v.animate().rotationXBy(360).setDuration(delay).start();

        switch(v.getId()){
            case R.id.cv_man:
                sharedPreferences.edit().putString("usergender","Man").apply();
                break;
            case R.id.cv_woman:
                sharedPreferences.edit().putString("usergender","Woman").apply();
                break;
        }

    }

}
