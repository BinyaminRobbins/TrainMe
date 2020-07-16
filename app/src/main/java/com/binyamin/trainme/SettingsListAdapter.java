package com.binyamin.trainme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

class SettingsListAdapter extends ArrayAdapter<SettingsItem> {
    Context context;
    ArrayList<SettingsItem> settingItems;
    int resource;

    public SettingsListAdapter(Context context, int resource,ArrayList<SettingsItem> settingItems){
        super(context,resource,settingItems);
        this.context = context;
        this.resource = resource;
        this.settingItems = settingItems;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        int image = settingItems.get(position).getImage();
        String name = settingItems.get(position).getName();

        SettingsItem settingsItem = new SettingsItem(image,name);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource,parent,false);

        TextView textView = convertView.findViewById(R.id.tvListItem);
        ImageView imageView = convertView.findViewById(R.id.ivListItem);

        textView.setText(name);
        imageView.setBackgroundResource(image);

        return convertView;
    }
}

