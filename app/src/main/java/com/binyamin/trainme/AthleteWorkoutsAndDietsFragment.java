package com.binyamin.trainme;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class AthleteWorkoutsAndDietsFragment extends Fragment {
    View view;
    SharedPreferences sharedPreferences;
    PurchaseProduct purchaseProduct;


    public AthleteWorkoutsAndDietsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = requireContext().getSharedPreferences("com.binyamin.trainme", Context.MODE_PRIVATE);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TabLayout tabLayout;
        ViewPager viewPager;

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_athlete_workouts_and_diets, container, false);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.workout_diet_viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager(),0);
        setUpPurchaseProduct();
        adapter.addFragment(new AllWorkoutsFragment(purchaseProduct), "Athlete Workouts");
        adapter.addFragment(new AllDietsFragment(purchaseProduct), "All Diets");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    private void setUpPurchaseProduct(){
        purchaseProduct = new PurchaseProduct(getContext(), getActivity(), getResources().getString(R.string.productId), sharedPreferences);
        purchaseProduct.setUp();
    }

    @Override
    public void onResume() {
        super.onResume();
        //Billing Client
        PurchaseProduct product = new PurchaseProduct(getContext(),getActivity(),"premium_features_sub_2",sharedPreferences);
        product.setUp();

        //Keep screen on?
        boolean screenOn =  sharedPreferences.getBoolean("screenOn",true);
        if(screenOn){
            requireActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }
}
