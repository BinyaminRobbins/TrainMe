package com.binyamin.trainme;


import android.content.Context;
import android.content.SharedPreferences;
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
public class WorkoutsFavoritesFragment extends Fragment {
    static RecyclerView rv;
    private static ArrayList<_3_SliderItem> sliderItems = new ArrayList<>();
    static ArrayList<_3_SliderItem> favoritesList = new ArrayList<>();
    static SharedPreferences prefs;

    public WorkoutsFavoritesFragment() {
        // Required empty public constructor

    }
    public static void updateList(Context context){
        LinearLayoutManager manager = new LinearLayoutManager(_Page3_SelectWorkout.context, RecyclerView.VERTICAL, false);
        rv.setLayoutManager(manager);

        SliderList sliderList = new SliderList(context,prefs);
        sliderItems.clear();
        sliderItems = sliderList.getWorkoutList();
        favoritesList.clear();

        for(_3_SliderItem item : sliderItems){
            if(item.getIsFavorite()){
                favoritesList.add(item);
            }
        }
        Log.i("FavoritesList",favoritesList.toString());
        rv.setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);
        FavoritesFragmentRecyclerViewAdapter adapter = new FavoritesFragmentRecyclerViewAdapter(context,prefs,sliderList,favoritesList,context.getResources().getString(R.string.workoutsTable));
        rv.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv = view.findViewById(R.id.favorites_rv);
        prefs = getContext().getSharedPreferences("com.binyamin.trainme",Context.MODE_PRIVATE);
        updateList(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites_workouts, container, false);
    }

}

