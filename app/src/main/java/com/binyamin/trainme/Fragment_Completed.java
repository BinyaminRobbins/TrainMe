package com.binyamin.trainme;


import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Completed extends Fragment {
    ProgressBar progressBar;


    public Fragment_Completed() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {

        final ImageView done = view.findViewById(R.id.done_animation);
        final Drawable drawable = done.getDrawable();
        done.setVisibility(View.INVISIBLE);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AnimatedVectorDrawable avd;
                AnimatedVectorDrawableCompat avdCompat;
                if (drawable instanceof AnimatedVectorDrawableCompat) {
                    avdCompat = (AnimatedVectorDrawableCompat) drawable;
                    done.setVisibility(View.VISIBLE);
                    avdCompat.start();
                } else if (drawable instanceof AnimatedVectorDrawable) {
                    done.setVisibility(View.VISIBLE);
                    avd = (AnimatedVectorDrawable) drawable;
                    avd.start();
                }
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(_Page2_UserDetails.context, _Page3_SelectWorkout.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        getActivity().overridePendingTransition( R.anim.intent_slide_out, R.anim.intent_slide_in );
                        getActivity().finish();
                    }
                },350);
            }
            },1000);


                progressBar = _Page2_UserDetails.progressBar;
                progressBar.setVisibility(View.INVISIBLE);
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment__completed, container, false);
    }

}
