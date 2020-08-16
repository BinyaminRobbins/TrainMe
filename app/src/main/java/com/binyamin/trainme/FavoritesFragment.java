package com.binyamin.trainme;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {
    View view;
    static int favoritesTabPostion;


    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_favorites, container, false);

        TabLayout favoritesTabLayout = view.findViewById(R.id.tabLayout_favorites);
        favoritesTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                favoritesTabPostion = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        favoritesTabPostion = 0;
        ViewPager viewPager = view.findViewById(R.id.favorites_viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager(),0);
        adapter.addFragment(new WorkoutsFavoritesFragment(), "Favorite Workouts");
        adapter.addFragment(new DietsFavoritesFragment(), "Favorite Diets");
        viewPager.setAdapter(adapter);
        favoritesTabLayout.setupWithViewPager(viewPager);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().setTitle("Your Favorites");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        requireActivity().setTitle(R.string.app_name);
    }

}
