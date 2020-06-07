package com.binyamin.trainme;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
public class ProgressButton {
    private TextView textView;
    private ConstraintLayout constraintLayout;
    private CardView cardView;
    private ProgressBar progressBar;
    Animation fade_in;

    public ProgressButton(Context ct, View view){
        fade_in = AnimationUtils.loadAnimation(ct,R.anim.fade_anim);
        cardView = view.findViewById(R.id.cardView);
        constraintLayout = view.findViewById(R.id.conLayout);
        progressBar = view.findViewById(R.id.progressButton);
        textView = view.findViewById(R.id.textViewProgress);
    }
    void buttonActivated(){
        progressBar.setAnimation(fade_in);
        progressBar.setVisibility(View.VISIBLE);
        textView.setAnimation(fade_in);
        textView.setText("Loading...");
    }
    void endAnimation(){
      //  progressBar.setAnimation(fade_in);
        progressBar.setVisibility(View.INVISIBLE);
        //textView.setAnimation(fade_in);
        textView.setText("Let's Go!");
    }
}
