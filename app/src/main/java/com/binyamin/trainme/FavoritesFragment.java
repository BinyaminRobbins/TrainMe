package com.binyamin.trainme;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {
    static RecyclerView rv;
    private static ArrayList<_3_SliderItem> sliderItems;
    static ArrayList<_3_SliderItem> favoritesList = new ArrayList<>();

    public FavoritesFragment() {
        // Required empty public constructor
    }
    public static void updateList(){
        LinearLayoutManager manager = new LinearLayoutManager(_Page3_SelectWorkout.context, RecyclerView.VERTICAL, false);
        rv.setLayoutManager(manager);
        favoritesList.clear();

        SQLiteDatabase database = _Page3_SelectWorkout.context.openOrCreateDatabase("Workouts", Context.MODE_PRIVATE,null);
        SliderList sliderList = new SliderList(database);
        sliderItems = sliderList.getSliderList();

        for(_3_SliderItem item : sliderItems){
            if(item.getIsFavorite()){
                favoritesList.add(item);
            }
        }
        Log.i("FavoritesList",favoritesList.toString());
        rv.setOverScrollMode(View.OVER_SCROLL_NEVER);
        FavoritesFragmentRecyclerViewAdapter adapter = new FavoritesFragmentRecyclerViewAdapter(favoritesList);
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
