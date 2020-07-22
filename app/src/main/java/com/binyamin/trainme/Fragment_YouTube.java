package com.binyamin.trainme;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_YouTube extends Fragment {
    View currentView;
    Handler connectionHandler;
    boolean isSetUp;


    public Fragment_YouTube() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currentView = view;
        connectionHandler = new Handler();
        connectionHandler.postDelayed(connectionRunnable,2000);

        String status = NetworkUtil.getConnectivityStatusString(getContext());
        if(status != null){
            Toast.makeText(getContext(), status, Toast.LENGTH_LONG).show();
            isSetUp = false;
            return;
        }else {
            setUpVideo(currentView);
        }
    }
        private void setUpVideo(View view){
            Toast.makeText(getContext(), "Tap to Play", Toast.LENGTH_SHORT).show();
            Toast.makeText(getContext(), "Scroll like TikTok", Toast.LENGTH_LONG).show();

            final ViewPager2 videoViewPager = view.findViewById(R.id.TikTokLayout);
            List<VideoItem> videoItems = new ArrayList<>();

            VideoItem videoItemAthleanX = new VideoItem();
            videoItemAthleanX.videoTitle = "Athlean-X";
            videoItemAthleanX.videoDescription = "Get ready to put the science back in strength and take your training to the next level!  The ATHLEAN-X channel on YouTube is devoted to delivering \"NO B.S\", science backed training and workout advice.";
            videoItemAthleanX.videoUrl = "https://www.youtube.com/watch?v=vc1E5CfRfos";
            videoItems.add(videoItemAthleanX);

            VideoItem videoItemVShred = new VideoItem();
            videoItemVShred.videoTitle = "VSHRED";
            videoItemVShred.videoDescription = "Great workouts of every category for men & women. Check out VSHRED on YouTube";
            videoItemVShred.videoUrl = "https://www.youtube.com/watch?v=VUTn5-tV4Ak";
            videoItems.add(videoItemVShred);

            VideoItem videoItemJeremy_Ethier = new VideoItem();
            videoItemJeremy_Ethier.videoTitle = "Jeremy Ethier";
            videoItemJeremy_Ethier.videoDescription = "This channel is focused on providing science-backed training and nutritional videos for everyone. Jeremy is certified by NASM and FMS and a Kinesiology graduate - so he knows what he's talking about!";
            videoItemJeremy_Ethier.videoUrl = "https://www.youtube.com/watch?v=95846CBGU0M";
            videoItems.add(videoItemJeremy_Ethier);

            videoViewPager.setAdapter(new VideosAdapter(videoItems));

            isSetUp = true;
        }

    private Runnable connectionRunnable = new Runnable() {
        @Override
        public void run() {
            String status = NetworkUtil.getConnectivityStatusString(_Page3_SelectWorkout.context);
            if(status != null){
                Toast.makeText(getContext(), status, Toast.LENGTH_LONG).show();
                isSetUp = false;
            }else{
                if(isSetUp == false) {
                    setUpVideo(currentView);
                }
            }
            connectionHandler.postDelayed(connectionRunnable,2000);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        connectionHandler.removeCallbacks(connectionRunnable);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_youtube, container, false);
    }
}
