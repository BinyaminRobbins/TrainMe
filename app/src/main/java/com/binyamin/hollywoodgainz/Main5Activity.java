package com.binyamin.hollywoodgainz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import java.util.ArrayList;
public class Main5Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ListView listview;
    ArrayAdapter<CharSequence> spinnerAdapter;
    Spinner spinner;
    CustomAdapter customAdapter;
    int currentButton;
    int[][] prattImages = {gifs.prattWarmups,gifs.prattArms, gifs.prattCircuit};
    int[][] efronImages = {gifs.prattWarmups,gifs.prattArms, gifs.prattCircuit};

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            listview.setAdapter(customAdapter);
            customAdapter.list.clear();
            Log.i("Cleared", Integer.toString(customAdapter.list.size()));
            if (currentButton == 0) {

            } else if (currentButton == 1) {
                switch (position) {
                    case 0:
                        for (int i = 0; i < Main4Activity.prattWorkout[0].length; i++) {
                            customAdapter.list.add(new singlerow(prattImages[0][i], Main4Activity.prattWorkout[0][i][0], Main4Activity.prattWorkout[0][i][1]));
                        }
                        break;
                    case 1:
                        for (int i = 0; i < Main4Activity.prattWorkout[1].length; i++) {
                            customAdapter.list.add(new singlerow(prattImages[1][i], Main4Activity.prattWorkout[1][i][0], Main4Activity.prattWorkout[1][i][1]));
                        }
                        break;
                    case 2:
                        for (int i = 0; i < Main4Activity.prattWorkout[2].length; i++) {
                            customAdapter.list.add(new singlerow(prattImages[2][i], Main4Activity.prattWorkout[2][i][0], Main4Activity.prattWorkout[2][i][1]));
                        }
                        break;
                }
            } else if (currentButton == 2) {
                switch (position) {
                    case 0:
                        for (int i = 0; i < Main4Activity.efronWorkout[0].length; i++) {
                            customAdapter.list.add(new singlerow(efronImages[0][i], Main4Activity.efronWorkout[0][i][0], Main4Activity.efronWorkout[0][i][1]));
                        }
                        break;
                    case 1:
                        for (int i = 0; i < Main4Activity.efronWorkout[1].length; i++) {
                            customAdapter.list.add(new singlerow(efronImages[1][i], Main4Activity.efronWorkout[1][i][0], Main4Activity.efronWorkout[1][i][1]));
                        }
                        break;
                    case 2:
                        for (int i = 0; i < Main4Activity.efronWorkout[2].length; i++) {
                            customAdapter.list.add(new singlerow(efronImages[2][i], Main4Activity.efronWorkout[2][i][0], Main4Activity.efronWorkout[2][i][1]));
                        }
                        break;
                }
            }
            customAdapter.notifyDataSetChanged();
        }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        TextView currentCeleb = findViewById(R.id.textViewCurrentCeleb);
        listview = findViewById(R.id.listView);
        customAdapter = new CustomAdapter(this);
        listview.setAdapter(customAdapter);

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
                for(int i = 0; i < Main4Activity.prattWorkout.length; i ++){
                    customAdapter.list.add(new singlerow(prattImages[0][i],Main4Activity.prattWorkout[0][i][0],Main4Activity.prattWorkout[0][i][1]));
                }
                customAdapter.notifyDataSetChanged();
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
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.singlerow, parent, false);
            ImageView imageView = row.findViewById(R.id.imageView);
            TextView titleTxt = row.findViewById(R.id.titleTxt);
            TextView subTitleTxt = row.findViewById(R.id.subTitleTxt);

            singlerow tmp = list.get(position);

            imageView.setImageResource(tmp.img);
            titleTxt.setText(tmp.title);
            subTitleTxt.setText(tmp.subTitle);

            return row;
        }
    }
