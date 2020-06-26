package com.binyamin.trainme;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
    ArrayList<_3_SliderItem> sliderItems;
    public FavoritesFragmentRecyclerViewAdapter(ArrayList<_3_SliderItem> sliderItems){
        this.sliderItems = sliderItems;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_favorites_fragment_rv,parent,false);
        RecyclerView.ViewHolder viewHolder = new ViewHolderClass (view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final ViewHolderClass viewHolderClass = (ViewHolderClass) holder;

        viewHolderClass.athleteTextView.setText(sliderItems.get(position).getAthleteName());
        viewHolderClass.athleteImageView.setImageResource(sliderItems.get(position).getImage());
        viewHolderClass.star.setImageResource(R.drawable.ic_action_star_clicked_border);
        viewHolderClass.star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setIcon(R.drawable.ic_action_star_clicked_border)
                        .setTitle("Remove From Favorites?")
                        .setMessage("Would you like to remove this workout from your \"Favorites\" list?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SQLiteDatabase database = _Page3_SelectWorkout.context.openOrCreateDatabase("Workouts", Context.MODE_PRIVATE,null);

                                ContentValues cv = new ContentValues();
                                cv.put("isFavorite","false"); //These Fields should be your String values of actual column names

                                database.update("Workouts", cv, "tagNum="+sliderItems.get(position).getTagNum(), null);
                                FavoritesFragment.updateList();
                            }
                        })
                        .setNegativeButton("Never Mind",null)
                        .show();
            }
        });
        viewHolderClass.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(_Page3_SelectWorkout.context, _Page4_AthleteWorkout.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("tag", String.valueOf(sliderItems.get(position).getTagNum()));
                    _Page3_SelectWorkout.context.startActivity(intent);
            }
        });

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
