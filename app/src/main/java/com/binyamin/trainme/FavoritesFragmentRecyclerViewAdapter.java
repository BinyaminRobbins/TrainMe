package com.binyamin.trainme;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FavoritesFragmentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<_3_SliderItem> sliderItems;
    Context context;
    private SliderList list;
    private SharedPreferences prefs;
    private String currentTableName;
    public FavoritesFragmentRecyclerViewAdapter(Context context,SharedPreferences prefs,SliderList list, ArrayList<_3_SliderItem> sliderItems, String currentTableName){
        this.prefs = prefs;
        this.context = context;
        this.list = list;
        this.sliderItems = sliderItems;
        this.currentTableName = currentTableName;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_favorites_fragment_rv,parent,false);
        ViewHolderClass viewHolderClass = new ViewHolderClass(view);
        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final ViewHolderClass viewHolderClass = (ViewHolderClass) holder;

        viewHolderClass.athleteTextView.setText(sliderItems.get(position).getAthleteName());
        if(FavoritesFragment.favoritesTabPostion == 1){
            viewHolderClass.button.setText("See Diet");
        }

        viewHolderClass.athleteImageView.setImageResource(sliderItems.get(position).getFavoriteImage());
        switch (sliderItems.get(position).getFavoriteImage()){
            case R.drawable.paulgeorge_favorites:
                viewHolderClass.athleteImageView.setScrollY(-30);
                break;
            case R.drawable.jamesharrison_favorites:
            case R.drawable.jimmybutler_favorites:
            case R.drawable.homescreen_julianedelman:
            case R.drawable.antoniobrown_favorites:
                viewHolderClass.athleteImageView.setScrollY(-45);
                break;
            case R.drawable.jjwatt_favorites:
                viewHolderClass.athleteImageView.setScrollY(-40);
                break;

        }



        viewHolderClass.star.setImageResource(R.drawable.ic_action_star_clicked_border);
        viewHolderClass.star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                new AlertDialog.Builder(v.getContext())
                        .setIcon(R.drawable.ic_action_star_clicked_border)
                        .setTitle("Remove From Favorites?")
                        .setMessage("Would you like to remove this workout from your \"Favorites\" list?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                ContentValues cv = new ContentValues();
                                cv.put("isFavorite","false"); //These Fields should be your String values of actual column names

                                list.getDB().update(currentTableName, cv, "tagNum="+sliderItems.get(position).getTagNum(), null);
                                if(currentTableName.equals(context.getResources().getString(R.string.workoutsTable))){
                                    WorkoutsFavoritesFragment.updateList(context);
                                }else if(currentTableName.equals(context.getResources().getString(R.string.dietsTable))){
                                    DietsFavoritesFragment.updateList(context);
                                }
                            }
                        })
                        .setNegativeButton("Never Mind",null)
                        .show();
            }
        });
        viewHolderClass.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!sliderItems.get(position).getIfRequiresPremium()) {
                    toAct4(v.getContext(),position);
                }else{
                    if(prefs.getBoolean("ProductIsOwned",false)){
                        toAct4(v.getContext(),position);
                    }else {
                        new AlertDialog.Builder(v.getContext())
                                .setTitle("Upgrade to Premium")
                                .setIcon(R.drawable.ic_action_premium)
                                .setMessage("You have discovered a premium feature.")
                                .setPositiveButton("Check It Out", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        _Page3_SelectWorkout.navController.navigate(R.id.menuNavigation_premium);
                                    }
                                })
                                .setNegativeButton("Not now", null)
                                .show();
                    }
                }
            }
        });


    }
    public void toAct4(Context context,int position){
        Intent intent = new Intent(context, _Page4_AthleteWorkout.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("tag", String.valueOf(sliderItems.get(position).getTagNum()));
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder{
        private ImageView athleteImageView;
        private TextView athleteTextView;
        private ImageButton star;
        private Button button;

        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);

            athleteImageView = itemView.findViewById(R.id.athleteImageView);
            athleteTextView = itemView.findViewById(R.id.athleteTextView);
            star = itemView.findViewById(R.id.imageButtonStar);
            button = itemView.findViewById(R.id.buttonStartWorkout);
        }
    }
}
