package com.binyamin.trainme;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllWorkoutsFragment extends Fragment {
    static private ArrayList<_3_SliderItem> sliderItems;
    static ViewPager2 viewPager2;
    private Handler sliderHandler;
    private short delay;
    private boolean scrollOn;
    private PurchaseProduct product;
    private SharedPreferences sharedPreferences;

    AllWorkoutsFragment(PurchaseProduct purchaseProduct) {
        // Required empty public constructor
        this.product = purchaseProduct;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        delay = 2500;

        viewPager2 = view.findViewById(R.id.ImageSlider);

        sliderHandler = new Handler();

        //preparing list of images from drawable folder
        SliderList sliderList = new SliderList(getContext(),sharedPreferences);
        sliderItems = sliderList.getWorkoutList();

        viewPager2.setAdapter(new _3_SliderAdapter(sliderList,sliderItems,product,true));
        viewPager2.setClipToPadding(false);

        viewPager2.setElevation(40);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(sliderItems.size());
        viewPager2.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(25));
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
        View view = inflater.inflate(R.layout.fragment_all_workouts, container, false);
        sharedPreferences = requireContext().getSharedPreferences("com.binyamin.trainme",Context.MODE_PRIVATE);
        scrollOn = sharedPreferences.getBoolean("scrollOn",true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(scrollOn)
                    Toast.makeText(getContext(), "You Can Disable Auto-Scroll in Settings", Toast.LENGTH_LONG).show();
            }
        },2000);

        return view;
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
        if(scrollOn)
            sliderHandler.postDelayed(sliderRunnable,delay);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(scrollOn)
            sliderHandler.removeCallbacks(sliderRunnable);
    }
}
