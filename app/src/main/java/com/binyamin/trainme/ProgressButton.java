package com.binyamin.trainme;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgressButton {
    private TextView textView;
    private ProgressBar progressBar;
    Animation fade_in;

    public ProgressButton(Context ct, View view){
        fade_in = AnimationUtils.loadAnimation(ct,R.anim.fade_anim);
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
        progressBar.setVisibility(View.INVISIBLE);
        textView.setText("Let's Go!");
    }
}
