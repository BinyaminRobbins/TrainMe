package com.binyamin.trainme;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import pl.droidsonroids.gif.GifImageView;

public class _4_RecyclerViewAdapter extends RecyclerView.Adapter<_4_RecyclerViewAdapter.ViewHolder> {
    String exerciseName;
    String repCount;
    int gifLocation;
    public _4_RecyclerViewAdapter(String exerciseName, String repCount, int gifLocation){
        this.exerciseName = exerciseName;
        this.repCount = repCount;
        this.gifLocation = gifLocation;
    }
    @Override
    public _4_RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customlayout_listviewrow,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull _4_RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.gifImageView.setImageResource(R.drawable.workout_benchpress);
        holder.titleText.setText("Bench Press");
        holder.subTitleText.setText("10 Reps");

    }

    @Override
    public int getItemCount() {
        return 5;
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