package com.binyamin.trainme;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.play.core.review.ReviewManagerFactory;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Settings extends Fragment implements View.OnClickListener {
    //ReviewManager manager;

    public Fragment_Settings() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().setTitle("Settings");
        //Fullscreen
        //getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Button button = view.findViewById(R.id.settings_premium_button);
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

        //SupportUs List View
        ListView listViewSupportUs = view.findViewById(R.id.listViewSupportUs);
        SettingsItem supportItem1 = new SettingsItem(R.drawable.ic_app_icon_rateus,"Rate Us");
        SettingsItem supportItem2 = new SettingsItem(R.drawable.ic_app_icon_blockads,"Remove Ads");
        SettingsItem supportItem3 = new SettingsItem(R.drawable.ic_app_icon_feedback,"Feedback by Mail");
        SettingsItem supportItem4 = new SettingsItem(R.drawable.ic_app_icon_more,"More By SyntApps");
        SettingsItem supportItem5 = new SettingsItem(R.drawable.ic_app_icon_share,"Share with friends");
        SettingsItem supportItem6 = new SettingsItem(R.drawable.ic_app_icon_instagram,"Instagram");

        ArrayList<SettingsItem> supportItems = new ArrayList<>();
        supportItems.add(supportItem1);
        supportItems.add(supportItem2);
        supportItems.add(supportItem3);
        supportItems.add(supportItem4);
        supportItems.add(supportItem5);
        supportItems.add(supportItem6);


        SettingsListAdapter supportItemAdapter = new SettingsListAdapter(getContext(),R.layout.adapter_view_settings_general,supportItems,false);
        listViewSupportUs.setAdapter(supportItemAdapter);
        listViewSupportUs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                Handler handler = new Handler();
                view.setBackgroundColor(Color.parseColor("#b0bec5"));
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    }
                },200);
                Toast.makeText(getContext(),"Loading...",Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 0:
                        final String appPackageName = requireActivity().getPackageName(); // getPackageName() from Context or Activity object
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
                        String aEmailList = "syntappsdev@gmail.com";
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
                    case 5:
                        startActivity(newInstagramProfileIntent(requireContext().getPackageManager(),"syntapps"));
                }
            }
        });

       /* Task<ReviewInfo> request = manager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.i("Review","Task Successful");
                // We can get the ReviewInfo object
                ReviewInfo reviewInfo = task.getResult();
                Task<Void> flow = manager.launchReviewFlow(requireActivity(), reviewInfo);
                flow.addOnCompleteListener(task2 -> {
                    Log.i("Review","Flow Finished");
                    // The flow has finished. The API does not indicate whether the user
                    // reviewed or not, or even whether the review dialog was shown. Thus, no
                    // matter the result, we continue our app flow.
                });
            } else {
                // There was some problem, continue regardless of the result.
                Log.i("review","A Problem Occurred");
            }
        });*/

    }

    //from https://stackoverflow.com/questions/15497261/open-instagram-user-profile
    private Intent newInstagramProfileIntent(PackageManager pm, String url) {
        final Intent intent = new Intent(Intent.ACTION_VIEW);
        try {
            if (pm.getPackageInfo("com.instagram.android", 0) != null) {
                if (url.endsWith("/")) {
                    url = url.substring(0, url.length() - 1);
                }
                final String username = url.substring(url.lastIndexOf("/") + 1);
                // http://stackoverflow.com/questions/21505941/intent-to-open-instagram-user-profile-on-android
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse("http://instagram.com/_u/" + username));
                intent.setPackage("com.instagram.android");
                return intent;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        intent.setData(Uri.parse(url));
        return intent;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // manager = ReviewManagerFactory.create(requireContext());
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requireActivity().setTitle(R.string.app_name);
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
