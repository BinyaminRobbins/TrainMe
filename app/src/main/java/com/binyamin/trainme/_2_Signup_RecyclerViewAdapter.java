package com.binyamin.trainme;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class _2_Signup_RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private static final int LAYOUT_ONE = 0;
    private static final int LAYOUT_TWO = 1;
    private static final int LAYOUT_THREE = 2;
    ConstraintLayout clteens;
    ConstraintLayout cl20s;
    ConstraintLayout cl30s;
    ConstraintLayout cl40s;
    ConstraintLayout cl50s;
    ConstraintLayout cl60s;

    public _2_Signup_RecyclerViewAdapter(){

    }

    @Override
    public int getItemViewType(int position){
        if(position == 0)
            return LAYOUT_ONE;
        else if(position == 1)
            return LAYOUT_TWO;
        else
            return LAYOUT_THREE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;
        RecyclerView.ViewHolder viewHolder = null;

        if(viewType == 0){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customlayout_age_groups,parent,false);
            viewHolder = new ViewHolderOne(view);
        }else if(viewType == 1){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customlayout_age_groups,parent,false);
            viewHolder = new ViewHolderOne(view);
        }else if(viewType == 2){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customlayout_age_groups,parent,false);
            viewHolder = new ViewHolderOne(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder.getItemViewType() == LAYOUT_ONE){
            Log.i("Layout","1");
            ViewHolderOne v1 = (ViewHolderOne) holder;
            v1.tvTitle.setText("Please Select Your Age Group");
            clteens.setOnClickListener(this);
            cl20s.setOnClickListener(this);
            cl30s.setOnClickListener(this);
            cl40s.setOnClickListener(this);
            cl50s.setOnClickListener(this);
            cl60s.setOnClickListener(this);

        }else{
            Log.i("Layout","not 1");

        }

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public void onClick(final View v) {
        if(v == clteens || v == cl20s || v == cl30s || v == cl40s || v == cl50s || v == cl60s) {
            //  v.setClickable(false);
            v.setBackgroundColor(Color.parseColor("#1976d2"));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    _Page2_UserDetails.recyclerView.scrollToPosition(1);
                    _2_ProgressBarAnimation anim = new _2_ProgressBarAnimation(_Page2_UserDetails.progressBar, 0, 33);
                    anim.setDuration(1300);
                    _Page2_UserDetails.progressBar.startAnimation(anim);
                }
            }, 200);
        }

    }

    public class ViewHolderOne extends RecyclerView.ViewHolder {
        TextView tvTitle;
        CardView cv_teens;
        CardView cv_20s;
        CardView cv_30s;
        CardView cv_40s;
        CardView cv_50s;
        CardView cv_60s;


        public ViewHolderOne(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            cv_teens = itemView.findViewById(R.id.age_teens);
            cv_20s = itemView.findViewById(R.id.age_20s);
            cv_30s = itemView.findViewById(R.id.age_30s);
            cv_40s = itemView.findViewById(R.id.age_40s);
            cv_50s = itemView.findViewById(R.id.age_50s);
            cv_60s = itemView.findViewById(R.id.age_60s);

            clteens = itemView.findViewById(R.id.clteens);
            cl20s = itemView.findViewById(R.id.cl20s);
            cl30s = itemView.findViewById(R.id.cl30s);
            cl40s = itemView.findViewById(R.id.cl40s);
            cl50s = itemView.findViewById(R.id.cl50s);
            cl60s = itemView.findViewById(R.id.cl60s);

        }
    }
    public class ViewHolderTwo extends RecyclerView.ViewHolder {
        ImageView circleImageView;
        TextView textViewName;
        TextView textViewFollowerCount;

        public ViewHolderTwo(View itemView) {
            super(itemView);


        }
    }
}
