package com.binyamin.trainme;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_UserGoals extends Fragment implements View.OnClickListener {
    CardView cv1;
    CardView cv2;
    CardView cv3;
    CardView cv4;
    ProgressBar progressBar;


    public Fragment_UserGoals() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cv1 = view.findViewById(R.id.cv_gainmuscle);
        cv2 = view.findViewById(R.id.cv_loseweight);
        cv3 = view.findViewById(R.id.cv_shapeforsports);
        cv4 = view.findViewById(R.id.cv_stayfit);

        cv1.setOnClickListener(this);
        cv2.setOnClickListener(this);
        cv3.setOnClickListener(this);
        cv4.setOnClickListener(this);

        progressBar = _Page2_UserDetails.progressBar;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_user_goals, container, false);
    }

    @Override
    public void onClick(View v) {
        _2_ProgressBarAnimation anim = new _2_ProgressBarAnimation(progressBar, progressBar.getProgress(), 67);
        anim.setDuration(500);
        progressBar.startAnimation(anim);




    }

}
