package com.binyamin.trainme;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllWorkoutsFragment extends Fragment {
    static protected ArrayList<_3_SliderItem> sliderItems;
    static ViewPager2 viewPager2;
    Handler sliderHandler;
    SharedPreferences sharedPreferences;
    short delay;

    public AllWorkoutsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        delay = 3500;
        Toast.makeText(getContext(),"You can disable auto-scroll in \"My Profile\"",Toast.LENGTH_LONG).show();

        sharedPreferences = getContext().getSharedPreferences("com.binyamin.trainme",Context.MODE_PRIVATE);
        viewPager2 = view.findViewById(R.id.ImageSlider);
        sliderHandler = new Handler();

        //preparing list of images from drawable folder
        SQLiteDatabase database = _Page3_SelectWorkout.context.openOrCreateDatabase("Workouts", Context.MODE_PRIVATE,null);
        Log.i("Page 3","DB Created");
        SliderList sliderList = new SliderList(database);
        sliderItems = sliderList.getSliderList();

        viewPager2.setAdapter(new _3_SliderAdapter(sliderItems, viewPager2));
        viewPager2.setClipToPadding(false);
        viewPager2.setElevation(40);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(sliderItems.size());
        viewPager2.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(70));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable,delay);
            }
        });


        viewPager2.setCurrentItem(2,true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_workouts, container, false);


    }


    //Automate Scrolling:
    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            boolean scrollOn = sharedPreferences.getBoolean("scrollOn",true);
                if(scrollOn) {
                    if(viewPager2.getCurrentItem() != sliderItems.size() - 1) {
                        viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1, true);
                        //sliderHandler.postDelayed(sliderRunnable, delay);
                    }else{
                        viewPager2.setCurrentItem(0 , true);
                    }
            }

        }
    };

    @Override
    public void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable,delay);
    }

    @Override
    public void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);

    }

}
