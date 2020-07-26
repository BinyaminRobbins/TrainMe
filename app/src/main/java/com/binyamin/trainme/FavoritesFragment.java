package com.binyamin.trainme;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {
    View view;


    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_favorites, container, false);

        TabLayout tabLayout = view.findViewById(R.id.tabLayout_favorites);
        ViewPager viewPager = view.findViewById(R.id.favorites_viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager(),0);
        adapter.addFragment(new WorkoutsFavoritesFragment(), "Favorite Workouts");
        adapter.addFragment(new DietsFavoritesFragment(), "Favorite Diets");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Your Favorites");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().setTitle(R.string.app_name);
    }

}
