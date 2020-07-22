package com.binyamin.trainme;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllWorkoutsFragment extends Fragment {
    static protected ArrayList<_3_SliderItem> sliderItems;
    static ViewPager2 viewPager2;
    Handler sliderHandler;
    SharedPreferences sharedPreferences;
    short delay;
    boolean scrollOn;

    public AllWorkoutsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        delay = 2500;

        sharedPreferences = getContext().getSharedPreferences("com.binyamin.trainme",Context.MODE_PRIVATE);
        viewPager2 = view.findViewById(R.id.ImageSlider);
        scrollOn = sharedPreferences.getBoolean("scrollOn",false);

        sliderHandler = new Handler();

        //preparing list of images from drawable folder
        SQLiteDatabase database = _Page3_SelectWorkout.context.openOrCreateDatabase("Workouts", Context.MODE_PRIVATE,null);
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
        viewPager2.setCurrentItem(2,false);

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
                if(scrollOn) {
                    if(viewPager2.getCurrentItem() != sliderItems.size() - 1) {
                        viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1, true);
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

        //Billing Client
        PurchaseProduct product = new PurchaseProduct(getContext(),getActivity(),"premium_features_sub_2",sharedPreferences);
        product.setUp();

        //Keep screen on?
        boolean screenOn =  sharedPreferences.getBoolean("screenOn",true);
        if(screenOn){
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }
}
