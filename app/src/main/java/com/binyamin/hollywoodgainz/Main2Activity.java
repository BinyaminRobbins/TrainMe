package com.binyamin.hollywoodgainz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton radioButton;
    SeekBar heightSeekBar;
    SeekBar weightSeekBar;
    int goals;
    Boolean goalSelected = false;
    SharedPreferences sharedPreferences;

    public void checkButton(View v){
        goalSelected = true;
        radioGroup = findViewById(R.id.radioGroup);
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        //  1 = workout
        //  2 = lose weight
        if(String.valueOf(radioButton.getId()) == "radio_one"){
            goals = 1;
        }else{
            goals = 2;
        }
    }

    public void next(View v){
        if(goalSelected == false){
            Toast.makeText(this,"Please Select A Goal", Toast.LENGTH_SHORT).show();
        }else{
            sharedPreferences.edit().putInt("goals",goals).apply();
            sharedPreferences.edit().putInt("height", heightSeekBar.getProgress()).apply();
            sharedPreferences.edit().putInt("weight", weightSeekBar.getProgress()).apply();
             Intent intent = new Intent(getApplicationContext(),Main3Activity.class);
             startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        sharedPreferences = this.getSharedPreferences("com.binyamin.hollywoodgainz", Context.MODE_PRIVATE);

        heightSeekBar  = findViewById(R.id.heightSeekBar);
        heightSeekBar.setMax(200);
        if (Build.VERSION.SDK_INT >= 26) {
            heightSeekBar.setMin(150);
        }
        heightSeekBar.incrementProgressBy(1);
        heightSeekBar.setProgress(175);
        heightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView heightDisplay = findViewById(R.id.heightDisplayTxt);
                heightDisplay.setText(progress + "cm");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        weightSeekBar  = findViewById(R.id.weightSeekBar);
        weightSeekBar.setMax(110);
        if (Build.VERSION.SDK_INT >= 26) {
            weightSeekBar.setMin(25);
        }
        weightSeekBar.incrementProgressBy(5);
        weightSeekBar.setProgress(50);
        weightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView weightDisplay = findViewById(R.id.weightDisplayTxt);
                weightDisplay.setText(progress + "kg");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
}
