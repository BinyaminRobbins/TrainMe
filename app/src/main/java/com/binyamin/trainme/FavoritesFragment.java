package com.binyamin.trainme;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {
    static RecyclerView rv;
    private static ArrayList<_3_SliderItem> sliderItems = new ArrayList<>();
    static ArrayList<_3_SliderItem> favoritesList = new ArrayList<>();
    static SQLiteDatabase database;

    public FavoritesFragment() {
        // Required empty public constructor
    }
    public static void updateList(){
        LinearLayoutManager manager = new LinearLayoutManager(_Page3_SelectWorkout.context, RecyclerView.VERTICAL, false);
        rv.setLayoutManager(manager);

        SliderList sliderList = new SliderList(database);
        sliderItems.clear();
        sliderItems = sliderList.getSliderList();
        favoritesList.clear();

        for(_3_SliderItem item : sliderItems){
            if(item.getIsFavorite()){
                favoritesList.add(item);
            }
        }
        Log.i("FavoritesList",favoritesList.toString());
        rv.setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);
        //rv.setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);
        FavoritesFragmentRecyclerViewAdapter adapter = new FavoritesFragmentRecyclerViewAdapter(favoritesList);
        rv.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv = view.findViewById(R.id.favorites_rv);
        database = _Page3_SelectWorkout.context.openOrCreateDatabase("Workouts", Context.MODE_PRIVATE,null);
        updateList();

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("com.binyamin.trainme",Context.MODE_PRIVATE);
        ImageView profileImage = view.findViewById(R.id.profilePic);
        if(sharedPreferences.contains("profileBitmap")) {
            String bitmap = sharedPreferences.getString("profileBitmap", "404 error");
            byte[] imageAsBytes = Base64.decode(bitmap.getBytes(), Base64.DEFAULT);
            profileImage.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        }else if(sharedPreferences.contains("profileURI")){
            String uri = sharedPreferences.getString("profileURI","404 error");
            profileImage.setImageURI(Uri.parse(uri));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }
}

