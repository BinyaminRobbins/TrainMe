package com.binyamin.trainme;


import android.content.Context;
import android.content.Intent;
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
import android.view.WindowManager;
import android.widget.ProgressBar;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Gender extends Fragment implements View.OnClickListener {
    CardView cvMan;
    CardView cvWoman;
    SharedPreferences sharedPreferences;
    ProgressBar progressBar;


    public Fragment_Gender() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        sharedPreferences = requireContext().getSharedPreferences("com.binyamin.trainme", Context.MODE_PRIVATE);

        cvMan = view.findViewById(R.id.cv_man);
        cvWoman = view.findViewById(R.id.cv_woman);

        cvMan.setOnClickListener(this);
        cvWoman.setOnClickListener(this);

        progressBar = _Page2_UserDetails.probar;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_gender, container, false);
    }

    @Override
    public void onClick(View v) {

        progressBar.setVisibility(View.VISIBLE);
        SliderList sliderList = new SliderList(getContext(),sharedPreferences);
        sliderList.setUpDB();

        _Page2_UserDetails.constraintLayout.setAlpha(0.4f);
        getActivity().getWindow().
                setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


        cvMan.setClickable(false);
        cvWoman.setClickable(false);

                Intent intent = new Intent(getContext(),_Page3_SelectWorkout.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();
                sharedPreferences.edit().putBoolean("hasOpenedBefore",true).apply();
                getActivity().overridePendingTransition( R.anim.intent_slide_out, R.anim.intent_slide_in );
                //Fragment added to Frame Layout

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
