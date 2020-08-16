package com.binyamin.trainme;


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
    private View currentView;
    private Handler connectionHandler;
    private boolean isSetUp;


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
            videoItemAthleanX.youtubePageUrl = "https://www.youtube.com/c/athleanx/videos";
            videoItems.add(videoItemAthleanX);

            VideoItem videoItemVShred = new VideoItem();
            videoItemVShred.videoTitle = "VSHRED";
            videoItemVShred.videoDescription = "Great workouts of every category for men & women. Check out VSHRED on YouTube";
            videoItemVShred.videoUrl = "https://www.youtube.com/watch?v=VUTn5-tV4Ak";
            videoItemVShred.youtubePageUrl = "https://www.youtube.com/c/vshred/videos";
            videoItems.add(videoItemVShred);

            VideoItem videoItemTHENX = new VideoItem();
            videoItemTHENX.videoTitle = "THENX";
            videoItemTHENX.videoDescription = "Elite Fitness, Body-Weight, Calisthenics-Type, Training, By An Elite Trainer & Team";
            videoItemTHENX.videoUrl = "https://www.youtube.com/watch?v=vwk7XPs5YdM";
            videoItemTHENX.youtubePageUrl = "https://www.youtube.com/c/OFFICIALTHENXSTUDIOS/videos";
            videoItems.add(videoItemTHENX);

            VideoItem videoItemChrisHeria = new VideoItem();
            videoItemChrisHeria.videoTitle = "Chris Heria";
            videoItemChrisHeria.videoDescription = "Chris Heria, the creator of THENX, has 3M+ subscribers and a channel with amazing home-workouts and bodyweight exercises";
            videoItemChrisHeria.videoUrl = "https://www.youtube.com/watch?v=Yrv4HMJCklI";
            videoItemChrisHeria.youtubePageUrl = "https://www.youtube.com/c/CHRISHERIA/videos";
            videoItems.add(videoItemChrisHeria);

            VideoItem videoItemJeremy_Ethier = new VideoItem();
            videoItemJeremy_Ethier.videoTitle = "Jeremy Ethier";
            videoItemJeremy_Ethier.videoDescription = "This channel is focused on providing science-backed training and nutritional videos for everyone. Jeremy is certified by NASM and FMS and a Kinesiology graduate - so he knows what he's talking about!";
            videoItemJeremy_Ethier.videoUrl = "https://www.youtube.com/watch?v=95846CBGU0M";
            videoItemJeremy_Ethier.youtubePageUrl = "https://www.youtube.com/c/JeremyEthier/videos";
            videoItems.add(videoItemJeremy_Ethier);

            VideoItem videoItemBodybuildingCom = new VideoItem();
            videoItemBodybuildingCom.videoTitle = "Bodybuilding.com";
            videoItemBodybuildingCom.videoDescription = "Bodybuilders.com Is Meant To Help Users Reach Their Health, Fitness And Appearance Goals Through Information, Motivation, And Supplementation.";
            videoItemBodybuildingCom.videoUrl = "https://youtu.be/wDPND0ejH50";
            videoItemBodybuildingCom.youtubePageUrl = "https://www.youtube.com/c/bodybuildingcom/videos";
            videoItems.add(videoItemBodybuildingCom);

            VideoItem videoItemBuffDudes = new VideoItem();
            videoItemBuffDudes.videoTitle = "Buff Dudes";
            videoItemBuffDudes.videoDescription = "Healthy Food Recipes & Gym Workout Routines. Lose Fat, Gain Muscle.";
            videoItemBuffDudes.videoUrl = "https://youtu.be/_ZeUlqJwNmw";
            videoItemBuffDudes.youtubePageUrl = "https://www.youtube.com/c/BuffDudesOfficial/videos";
            videoItems.add(videoItemBuffDudes);

            VideoItem videoItemTheRock = new VideoItem();
            videoItemTheRock.videoTitle = "The Rock";
            videoItemTheRock.videoDescription = "The official YouTube channel for Dwayne \"The Rock\" Johnson & Seven Bucks Digital Studios.";
            videoItemTheRock.videoUrl = "https://youtu.be/24fdcMw0Bj0";
            videoItemTheRock.youtubePageUrl = "https://www.youtube.com/user/therock/videos";
            videoItems.add(videoItemTheRock);


            VideoItem videoItemMikeThurston = new VideoItem();
            videoItemMikeThurston.videoTitle = "Mike Thurston";
            videoItemMikeThurston.videoDescription = "This channel was created to educate, motivate, and to help viewers optimise their body composition, whilst making the process as enjoyable and sustainable as possible.";
            videoItemMikeThurston.videoUrl = "https://youtu.be/Uerf4Mj60Ug";
            videoItemMikeThurston.youtubePageUrl = "https://www.youtube.com/c/MikeThurston/videos";
            videoItems.add(videoItemMikeThurston);

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
                if(!isSetUp) {
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
