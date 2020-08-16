package com.binyamin.trainme;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_WorkoutInfo extends Fragment {
    int position;
    private String[] detailsArray;

    Fragment_WorkoutInfo(int position, String[] detailsArray) {
        // Required empty public constructor
        this.position = position;
        this.detailsArray = detailsArray;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView workoutInfoTxt = view.findViewById(R.id.workout_infoTxt);
        workoutInfoTxt.setText(detailsArray[position]);
        workoutInfoTxt.setTextColor(Color.WHITE);
        if(AthleteWorkoutsAndDietsFragment.tabPosition == 1) {
            workoutInfoTxt.setTextSize(25);
            workoutInfoTxt.setEms(40);
        }else{
            workoutInfoTxt.setTextSize(22);
            workoutInfoTxt.setEms(40);
        }

        ImageButton closeButton = view.findViewById(R.id.closeInfoButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AppCompatActivity appCompatActivity = (AppCompatActivity) v.getContext();
                Fragment fragment = _3_SliderAdapter.fragment_workoutInfo;
                appCompatActivity.getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment__workout_info, container, false);
    }
}
