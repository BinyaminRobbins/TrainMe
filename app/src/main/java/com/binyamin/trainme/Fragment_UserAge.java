package com.binyamin.trainme;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_UserAge extends Fragment implements View.OnClickListener {
    CardView teens;
    CardView twenties;
    CardView thirties;
    CardView fourties;
    CardView fifties;
    CardView sixties;
    ProgressBar progressBar;
    public Fragment_UserAge() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_userage, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        teens = view.findViewById(R.id.age_teens);
        twenties = view.findViewById(R.id.age_20s);
        thirties = view.findViewById(R.id.age_30s);
        fourties = view.findViewById(R.id.age_40s);
        fifties = view.findViewById(R.id.age_50s);
        sixties = view.findViewById(R.id.age_60s);

        teens.setOnClickListener(this);
        twenties.setOnClickListener(this);
        thirties.setOnClickListener(this);
        fourties.setOnClickListener(this);
        fifties.setOnClickListener(this);
        sixties.setOnClickListener(this);

        progressBar = _Page2_UserDetails.progressBar;
    }

    @Override
    public void onClick(View v) {
        int delay = 800;

        teens.setClickable(false);
        twenties.setClickable(false);
        thirties.setClickable(false);
        fourties.setClickable(false);
        fifties.setClickable(false);
        sixties.setClickable(false);

        _2_ProgressBarAnimation anim = new _2_ProgressBarAnimation(progressBar, progressBar.getProgress(), 33);
        anim.setDuration(delay - 400);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Fragment_UserGoals fragUserGoals = new Fragment_UserGoals();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.disallowAddToBackStack();
                transaction.replace(R.id.flFragment,fragUserGoals).commit();
                //Fragment added to Frame Layout
            }
        },delay + 250);

        progressBar.startAnimation(anim);
        v.animate().rotationXBy(360).setDuration(delay).start();


    }
}
