package com.binyamin.trainme;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClass viewHolderClass = (ViewHolderClass) holder;

        viewHolderClass.athleteTextView.setText(sliderItems.get(position).getAthleteName());
        viewHolderClass.athleteImageView.setImageResource(sliderItems.get(position).getImage());
        viewHolderClass.star.setImageResource(R.drawable.ic_action_star_clicked_border);

    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder{
        private ImageView athleteImageView;
        private TextView athleteTextView;
        private ImageButton star;

        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);

            athleteImageView = itemView.findViewById(R.id.athleteImageView);
            athleteTextView = itemView.findViewById(R.id.athleteTextView);
            star = itemView.findViewById(R.id.imageButtonStar);
        }
    }
}
