package com.binyamin.trainme;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class AthleteWorkoutsAndDietsFragment extends Fragment{
    private SharedPreferences sharedPreferences;
    private PurchaseProduct purchaseProduct;
    static TabLayout tabLayout;
    private ViewPager viewPager;
    static int tabPosition;

    public AthleteWorkoutsAndDietsFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpPurchaseProduct();
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager(), 0);
        adapter.addFragment(new AllWorkoutsFragment(purchaseProduct), "Athlete Workouts");
        adapter.addFragment(new AllDietsFragment(purchaseProduct), "All Diets");
        viewPager.setAdapter(adapter);
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                viewPager.setCurrentItem(viewPager.getCurrentItem());
                return true;
            }
        });
        tabLayout.setupWithViewPager(viewPager);
        adapter.notifyDataSetChanged();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabPosition = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabPosition = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_athlete_workouts_and_diets, container, false);
        sharedPreferences = requireContext().getSharedPreferences("com.binyamin.trainme", Context.MODE_PRIVATE);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.workout_diet_viewpager);
        return view;
    }

    private void setUpPurchaseProduct() {
        purchaseProduct = new PurchaseProduct(getContext(), getActivity(), getResources().getString(R.string.inapp_productId), sharedPreferences);
        purchaseProduct.setUp();
    }

    @Override
    public void onResume() {
        super.onResume();
        //Billing Client
        setUpPurchaseProduct();

        //Keep screen on?
        boolean screenOn = sharedPreferences.getBoolean("screenOn", true);
        if (screenOn) {
            requireActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

}
