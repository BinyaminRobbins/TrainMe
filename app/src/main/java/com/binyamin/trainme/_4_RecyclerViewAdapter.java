package com.binyamin.trainme;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class _4_RecyclerViewAdapter extends RecyclerView.Adapter<_4_RecyclerViewAdapter.ViewHolder> {
    ArrayList<AthleteWorkouts> arrayName;
    public _4_RecyclerViewAdapter(ArrayList<AthleteWorkouts> arrayName){
        this.arrayName = arrayName;
    }
    @Override
    public _4_RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customlayout_listviewrow,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull _4_RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.gifImageView.setImageResource(arrayName.get(position).getGif());
        holder.titleText.setText(arrayName.get(position).getWorkoutName());
        holder.subTitleText.setText(arrayName.get(position).getRepCount());

    }

    @Override
    public int getItemCount() {
        return arrayName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        GifImageView gifImageView;
        TextView titleText;
        TextView subTitleText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gifImageView = itemView.findViewById(R.id.gifImageView);
            titleText = itemView.findViewById(R.id.tvExerciseName);
            subTitleText = itemView.findViewById(R.id.tvRepCount);
        }
    }
}