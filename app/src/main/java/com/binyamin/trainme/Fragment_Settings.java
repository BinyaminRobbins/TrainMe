package com.binyamin.trainme;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Settings extends Fragment implements View.OnClickListener {
    Button button;

    public Fragment_Settings() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Settings");
        //Fullscreen
        //getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        button = view.findViewById(R.id.settings_premium_button);
        button.setOnClickListener(this);
        //General Settings List View
        ListView generalListView = view.findViewById(R.id.listViewGeneral);
        SettingsItem generalItem1 = new SettingsItem(R.drawable.ic_app_icon_gender,"Gender");
        SettingsItem generalItem2 = new SettingsItem(R.drawable.ic_app_icon_scroll,"Auto-Scroll");
        SettingsItem generalItem3 = new SettingsItem(R.drawable.ic_app_icon_screenon,"Keep Screen On");

        ArrayList<SettingsItem> generalItems = new ArrayList<>();
        generalItems.add(generalItem1);
        generalItems.add(generalItem2);
        generalItems.add(generalItem3);

        SettingsListAdapter generalListAdapter = new SettingsListAdapter(getContext(),R.layout.adapter_view_settings_general,generalItems,true);
        generalListView.setAdapter(generalListAdapter);
        generalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                Toast.makeText(getContext(),"Touched",Toast.LENGTH_SHORT).show();
                Handler handler = new Handler();
                view.setBackgroundColor(Color.parseColor("#b0bec5"));
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.setBackgroundColor(getResources().getColor(android.R.color.white));
                    }
                },200);

            }
        });

        //SupportUs List View
        ListView listViewSupportUs = view.findViewById(R.id.listViewSupportUs);
        SettingsItem supportItem1 = new SettingsItem(R.drawable.ic_app_icon_rateus,"Rate Us");
        SettingsItem supportItem2 = new SettingsItem(R.drawable.ic_app_icon_blockads,"Remove Ads");
        SettingsItem supportItem3 = new SettingsItem(R.drawable.ic_app_icon_feedback,"Feedback by Mail");
        SettingsItem supportItem4 = new SettingsItem(R.drawable.ic_app_icon_more,"More By SyntApps");
        SettingsItem supportItem5 = new SettingsItem(R.drawable.ic_app_icon_share,"Share with friends");

        ArrayList<SettingsItem> supportItems = new ArrayList<>();
        supportItems.add(supportItem1);
        supportItems.add(supportItem2);
        supportItems.add(supportItem3);
        supportItems.add(supportItem4);
        supportItems.add(supportItem5);

        SettingsListAdapter supportItemAdapter = new SettingsListAdapter(getContext(),R.layout.adapter_view_settings_general,supportItems,false);
        listViewSupportUs.setAdapter(supportItemAdapter);
        listViewSupportUs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),"Loading...",Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 0:
                        final String appPackageName = getActivity().getPackageName(); // getPackageName() from Context or Activity object
                        try {
                            Intent launchIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName));
                            launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(launchIntent);

                        } catch (android.content.ActivityNotFoundException e) {
                            Intent launchIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName));
                            launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(launchIntent);

                        }
                        break;
                    case 1:
                        goGetPremium();
                        break;
                    case 2:
                        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                        String aEmailList[] = {"syntappsdev@gmail.com"};
                        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, aEmailList);
                        emailIntent.setType("plain/text");
                        startActivity(emailIntent);
                        break;
                    case 3:
                        try {
                            Intent launchIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/dev?id=9194990442886517371&hl=en"));
                            launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(launchIntent);

                        } catch (android.content.ActivityNotFoundException e) {
                           e.printStackTrace();
                        }
                        break;
                    case 4:
                        try {
                            Intent shareIntent = new Intent(Intent.ACTION_SEND);
                            shareIntent.setType("text/plain");
                            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
                            String shareMessage= "\nCheck out TrainMe - real workouts & diets by PRO ATHLETES\n\n";
                            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                            startActivity(Intent.createChooser(shareIntent, "select to share"));
                        } catch(Exception e) {
                            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                }
            }
        });



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getActivity().setTitle(R.string.app_name);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.settings_premium_button){
           goGetPremium();
        }
    }

    private void goGetPremium() {
        _Page3_SelectWorkout.navController.navigate(R.id.menuNavigation_premium);
    }
}
