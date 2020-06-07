package com.binyamin.trainme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
class Athlete extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    /*ListView listview;
    ArrayAdapter<CharSequence> spinnerAdapter;
    Spinner spinner;
    CustomAdapter customAdapter;
    int currentButton;
    int[][] prattImages = {gifs.prattWarmups,gifs.prattArms, gifs.prattCircuit};
    int[][] lebronImages = {gifs.prattWarmups,gifs.prattArms, gifs.prattCircuit};*/

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
           /* listview.setAdapter(customAdapter);
            customAdapter.list.clear();
            Log.i("Cleared", Integer.toString(customAdapter.list.size()));
            if (currentButton == 0) {

            } else if (currentButton == 1) {
                switch (position) {
                    case 0:
                        for (int i = 0; i < Select_A_Workout.prattWorkout[0].length; i++) {
                            customAdapter.list.add(new singlerow(prattImages[0][i], Select_A_Workout.prattWorkout[0][i][0], Select_A_Workout.prattWorkout[0][i][1]));
                        }
                        break;
                    case 1:
                        for (int i = 0; i < Select_A_Workout.prattWorkout[1].length; i++) {
                            customAdapter.list.add(new singlerow(prattImages[1][i], Select_A_Workout.prattWorkout[1][i][0], Select_A_Workout.prattWorkout[1][i][1]));
                        }
                        break;
                    case 2:
                        for (int i = 0; i < Select_A_Workout.prattWorkout[2].length; i++) {
                            customAdapter.list.add(new singlerow(prattImages[2][i], Select_A_Workout.prattWorkout[2][i][0], Select_A_Workout.prattWorkout[2][i][1]));
                        }
                        break;
                }
            } else if (currentButton == 2) {
                switch (position) {
                    case 0:
                        for (int i = 0; i < Select_A_Workout.lebronWorkout[0].length; i++) {
                            customAdapter.list.add(new singlerow(lebronImages[0][i], Select_A_Workout.lebronWorkout[0][i][0], Select_A_Workout.lebronWorkout[0][i][1]));
                        }
                        break;
                    case 1:
                        for (int i = 0; i < Select_A_Workout.lebronWorkout[1].length; i++) {
                            customAdapter.list.add(new singlerow(lebronImages[1][i], Select_A_Workout.lebronWorkout[1][i][0], Select_A_Workout.lebronWorkout[1][i][1]));
                        }
                        break;
                    case 2:
                        for (int i = 0; i < Select_A_Workout.lebronWorkout[2].length; i++) {
                            customAdapter.list.add(new singlerow(lebronImages[2][i], Select_A_Workout.lebronWorkout[2][i][0], Select_A_Workout.lebronWorkout[2][i][1]));
                        }
                        break;
                }
            }
            customAdapter.notifyDataSetChanged();*/
        }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
    }

      /*  listview = findViewById(R.id.listView);
        customAdapter = new CustomAdapter(this);
        listview.setAdapter(customAdapter);
        ImageView imageViewPortrait = findViewById(R.id.imageViewPortrait);
        spinner = findViewById(R.id.spinner);
        Intent intent = getIntent();
        currentButton = intent.getIntExtra("currentButton", -1);
        switch (currentButton) {
            case 0:

                break;
            case 1:
                imageViewPortrait.setImageResource(R.drawable.starlord_portrait);
                for(int i = 0; i < Select_A_Workout.prattWorkout.length; i ++){
                    customAdapter.list.add(new singlerow(prattImages[0][i], Select_A_Workout.prattWorkout[0][i][0], Select_A_Workout.prattWorkout[0][i][1]));
                }
                customAdapter.notifyDataSetChanged();
                //spinner set to the starting workout for homescreen_lebron listView
                spinnerAdapter = ArrayAdapter.createFromResource(this,
                        R.array.chrisPratt, android.R.layout.simple_spinner_item);
                break;
            case 2:
                imageViewPortrait.setImageResource(R.drawable.lebron_portrait);
                String[][] lebronWorkout = Select_A_Workout.lebronWorkout[0];
                //spinner set to the starting workout for homescreen_lebron listView

                spinnerAdapter = ArrayAdapter.createFromResource(this,
                        R.array.lebron, android.R.layout.simple_spinner_item);
                break;
            default:
        }

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);
    }
}
    class singlerow {
        String title;
        String subTitle;
        int img;

        singlerow(int img, String title, String subTitle) {
            this.img = img;
            this.title = title;
            this.subTitle = subTitle;
        }
    }

    class CustomAdapter extends BaseAdapter {
        static ArrayList<singlerow> list;
        Context c;

        CustomAdapter(Context context) {
            list = new ArrayList<>();
            c = context;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ImageView imageView = convertView.findViewById(R.id.imageView);
            TextView titleTxt = convertView.findViewById(R.id.titleTxt);
            TextView subTitleTxt = convertView.findViewById(R.id.subTitleTxt);

            singlerow tmp = list.get(position);

            imageView.setImageResource(tmp.img);
            titleTxt.setText(tmp.title);
            subTitleTxt.setText(tmp.subTitle);

            return convertView;
        }
    }*/
    }
