package com.binyamin.trainme;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import static androidx.core.content.ContextCompat.getSystemService;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_UserName extends Fragment implements View.OnKeyListener {
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;


    public Fragment_UserName() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        sharedPreferences = _Page2_UserDetails.context.getSharedPreferences("com.binyamin.trainme", Context.MODE_PRIVATE);
        progressBar = _Page2_UserDetails.progressBar;
        final EditText editText = view.findViewById(R.id.editTextEnterNameSignup);
        Button button = view.findViewById(R.id.continueButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                int delay = 800;
                _2_ProgressBarAnimation anim = new _2_ProgressBarAnimation(progressBar, progressBar.getProgress(), progressBar.getProgress() + 25);
                anim.setDuration(delay - 400);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AppCompatActivity activity = (AppCompatActivity) v.getContext();
                        Fragment_UserAge frag_Age = new Fragment_UserAge();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, frag_Age).addToBackStack(null).commit();

                    }
                },delay + 250);

                progressBar.startAnimation(anim);
                v.animate().rotationXBy(360).setDuration(delay).start();


                sharedPreferences.edit().putString("username",editText.getText().toString()).apply();

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment__user_name, container, false);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if(keyCode == event.KEYCODE_ENTER){
            View view = getActivity().findViewById(android.R.id.content);
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
        return false;
    }
}
