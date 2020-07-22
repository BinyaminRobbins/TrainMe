package com.binyamin.trainme;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

class SettingsListAdapter extends ArrayAdapter<SettingsItem> {
    Context context;
    ArrayList<SettingsItem> settingItems;
    int resource;
    boolean switches;
    Switch switch1;

    public SettingsListAdapter(Context context, int resource,ArrayList<SettingsItem> settingItems,boolean switches){
        super(context,resource,settingItems);
        this.context = context;
        this.resource = resource;
        this.settingItems = settingItems;
        this.switches = switches;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        int image = settingItems.get(position).getImage();
        String name = settingItems.get(position).getName();

        SettingsItem settingsItem = new SettingsItem(image,name);

        final SharedPreferences sharedPreferences = _Page3_SelectWorkout.context.getSharedPreferences("com.binyamin.trainme",Context.MODE_PRIVATE);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource,parent,false);

        TextView textView = convertView.findViewById(R.id.tvListItem);
        ImageView imageView = convertView.findViewById(R.id.ivListItem);
        if(switches) {
            switch1 = convertView.findViewById(R.id.switch1);
            switch1.setTag(position);
            switch1.setVisibility(View.VISIBLE);
            setItemsChecked(position,sharedPreferences);
            if(position == 0){
                switch1.setText("M - F");
            }
            switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    switch(buttonView.getTag().toString()){
                        case "0":
                            if(buttonView.isChecked()){
                                sharedPreferences.edit().putString("usergender","Woman").apply();
                            }else{
                                sharedPreferences.edit().putString("usergender","Man").apply();
                            }
                            break;
                        case "1":
                            if(buttonView.isChecked()){
                                sharedPreferences.edit().putBoolean("scrollOn",true).apply();
                            }else{
                                sharedPreferences.edit().putBoolean("scrollOn",false).apply();
                            }
                            break;
                        case "2":
                            if(buttonView.isChecked()){
                                sharedPreferences.edit().putBoolean("screenOn",true).apply();
                            }else{
                                sharedPreferences.edit().putBoolean("screenOff",false).apply();
                            }
                            break;
                    }
                }
            });
        }
        textView.setText(name);
        imageView.setBackgroundResource(image);

        return convertView;
    }

    private void setItemsChecked(int position,SharedPreferences prefs){
        switch(position){
            case 0:
                if(prefs.getString("usergender","Man").equals("Man")){
                    switch1.setChecked(true);
                }else switch1.setChecked(false);
                break;
            case 1:
                if(prefs.getBoolean("scrollOn",false)){
                    switch1.setChecked(true);
                }else switch1.setChecked(false);
                break;
            case 2:
                if(prefs.getBoolean("screenOn",true)){
                    switch1.setChecked(true);
                }else switch1.setChecked(false);
                break;
        }
    }
    }

