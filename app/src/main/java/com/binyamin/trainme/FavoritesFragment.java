package com.binyamin.trainme;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {
    static RecyclerView rv;

    public FavoritesFragment() {
        // Required empty public constructor
    }
    public static void updateList(){
        LinearLayoutManager manager = new LinearLayoutManager(_Page3_SelectWorkout.context, RecyclerView.VERTICAL, false);
        rv.setLayoutManager(manager);
        ArrayList<_3_SliderItem> sliderItems = new ArrayList<>();
        for(_3_SliderItem item : AllWorkoutsFragment.sliderItems){
            if(item.getIsFavorite()){
                sliderItems.add(item);
            }
        }
        rv.setOverScrollMode(View.OVER_SCROLL_NEVER);
        FavoritesFragmentRecyclerViewAdapter adapter = new FavoritesFragmentRecyclerViewAdapter(sliderItems);
        rv.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv = view.findViewById(R.id.favorites_rv);
        updateList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

}
