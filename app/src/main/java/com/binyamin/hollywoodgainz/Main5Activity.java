package com.binyamin.hollywoodgainz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
public class Main5Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ListView listview;
    ArrayAdapter arrayAdapter;
    ArrayAdapter<CharSequence> spinnerAdapter;
    ArrayList<String> currentArrayList = new ArrayList<>();
    Spinner spinner;
    int spinnerPosition;
    int currentButton;

    //Images:
    ArrayList<String[][][]> WorkoutURLs = new ArrayList<>();
    String[][] theRockWorkoutUrls = {};
    String[][] prattWorkoutURLs = {ImageURLs.prattWarmup,ImageURLs.prattArms,ImageURLs.prattCircuit};
    String[][] efronWorkoutURLs = {ImageURLs.efronArms,ImageURLs.efronLegs,ImageURLs.efronChest};

    static ImageView imageView;

    //Method to Display the amount of reps (contained in the 3D String Workout Arrays) as a Toast.
    public void setRepsText(int position) {
        String[][][] currentWorkout = new String[3][][];
        switch (currentButton) {
            case 0:
                currentWorkout = Main4Activity.theRockWorkout;
                break;
            case 1:
                currentWorkout = Main4Activity.prattWorkout;
                break;
            case 2:
                currentWorkout = Main4Activity.efronWorkout;
                break;
            default:
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, currentWorkout[spinnerPosition][position][1], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerPosition = position;
        currentArrayList.clear();
        if (currentButton == 1) {
            String[][] prattWorkout = new String[2][];
            switch (spinnerPosition) {
                case 0:
                    prattWorkout = Main4Activity.prattWorkout[0];
                    break;
                case 1:
                    prattWorkout = Main4Activity.prattWorkout[1];
                    break;
                case 2:
                    prattWorkout = Main4Activity.prattWorkout[2];
                    break;
            }
            for (int i = 0; i < prattWorkout.length; i++) {
                currentArrayList.add(prattWorkout[i][0]);
            }
            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, currentArrayList);
            listview.setAdapter(arrayAdapter);

        } else if (currentButton == 2) {
            String[][] efronWorkout = new String[3][];
            switch (spinnerPosition) {
                case 0:
                    efronWorkout = Main4Activity.efronWorkout[0];
                    break;
                case 1:
                    efronWorkout = Main4Activity.efronWorkout[1];
                    break;
                case 2:
                    efronWorkout = Main4Activity.efronWorkout[2];
                    break;
            }
            for (int i = 0; i < efronWorkout.length; i++) {
                currentArrayList.add(efronWorkout[i][0]);
            }
            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, currentArrayList);
            listview.setAdapter(arrayAdapter);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        Toast.makeText(this, "Select A Workout for Rep/Set Info", Toast.LENGTH_SHORT).show();

        if (currentArrayList.size() >= 0) {
            currentArrayList.clear();
        }

        TextView currentCeleb = findViewById(R.id.textViewCurrentCeleb);
        spinner = findViewById(R.id.spinner);
        Intent intent = getIntent();
        currentButton = intent.getIntExtra("currentButton", -1);
        switch (currentButton) {
            case 0:
                currentCeleb.setText(Main4Activity.theRockTitle.getText().toString());
                currentCeleb.setTextSize(44);
                break;
            case 1:
                currentCeleb.setText(Main4Activity.prattTitle.getText().toString());
                currentCeleb.setTextSize(42);
                String[][] prattWorkout = Main4Activity.prattWorkout[0];
                for (int i = 0; i < prattWorkout.length; i++) {
                    currentArrayList.add(prattWorkout[i][0]);
                }

                //spinner set to the starting workout for efron listView
                spinnerAdapter = ArrayAdapter.createFromResource(this,
                        R.array.chrisPratt, android.R.layout.simple_spinner_item);
                break;
            case 2:
                //set currentCeleb(Text View) to Celeb. Name
                currentCeleb.setText(Main4Activity.efronTitle.getText().toString());
                currentCeleb.setTextSize(45);
                //listView gets data from default Efron i.e. efronWorkout(0)
                String[][] efronWorkout = Main4Activity.efronWorkout[0];
                for (int i = 0; i < efronWorkout.length; i++) {
                    currentArrayList.add(efronWorkout[i][0]);
                }
                //spinner set to the starting workout for efron listView
                spinnerAdapter = ArrayAdapter.createFromResource(this,
                        R.array.zacEfron, android.R.layout.simple_spinner_item);
                break;
            default:
                currentCeleb.setText("Error in (int)Current Button");
        }
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);

        listview = findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, currentArrayList);
        listview.setAdapter(arrayAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "That Didn't Work!!!!", Toast.LENGTH_SHORT).show();
                setRepsText(position);
                String[][] currentImageArray = {};
                String imageUri = " ";
                switch (currentButton) {
                    case 0:
                        imageUri = theRockWorkoutUrls[spinnerPosition][position];
                        break;
                    case 1:
                        imageUri = prattWorkoutURLs[spinnerPosition][position];
                        break;
                    case 2:
                        imageUri = efronWorkoutURLs[spinnerPosition][position];
                        break;
                }
                imageView = findViewById(R.id.imageView);
                    ImageView imageView = findViewById(R.id.imageView);
                    Picasso.with(getApplicationContext()).load(imageUri).into(imageView);

            }
        });
    }
}

