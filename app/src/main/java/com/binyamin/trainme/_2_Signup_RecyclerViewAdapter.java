package com.binyamin.trainme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class _2_Signup_RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private Context context;
    private static final int LAYOUT_ONE = 0;
    private static final int LAYOUT_TWO = 1;
    private static final int LAYOUT_THREE = 2;
    int pos;
    //Age
    ConstraintLayout clteens;
    ConstraintLayout cl20s;
    ConstraintLayout cl30s;
    ConstraintLayout cl40s;
    ConstraintLayout cl50s;
    ConstraintLayout cl60s;
    //Training Goals
    ConstraintLayout clgainmuscle;
    ConstraintLayout clloseweight;
    ConstraintLayout clstayfit;
    ConstraintLayout clshapeforsports;
    //Gender
    ConstraintLayout cl_man;
    ConstraintLayout cl_woman;

    public _2_Signup_RecyclerViewAdapter(){
        this.context = _Page2_UserDetails.context;
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
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customlayout_2_age_groups,parent,false);
            viewHolder = new ViewHolderOne(view);
        }else if(viewType == 1){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customlayout_2_user_goals,parent,false);
            viewHolder = new ViewHolderTwo(view);
        }else if(viewType == 2){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customlayout_2_select_gender,parent,false);
            viewHolder = new ViewHolderThree(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder.getItemViewType() == LAYOUT_ONE){
            ViewHolderOne v1 = (ViewHolderOne) holder;
            v1.tvTitle.setText("Please Select Your Age Group");
            clteens.setOnClickListener(this);
            cl20s.setOnClickListener(this);
            cl30s.setOnClickListener(this);
            cl40s.setOnClickListener(this);
            cl50s.setOnClickListener(this);
            cl60s.setOnClickListener(this);


        }else if(holder.getItemViewType() == LAYOUT_TWO){
            ViewHolderTwo v2 = (ViewHolderTwo) holder;
            v2.tvTitle.setText("Choose the Goal That Applies Best to You");
            v2.tv_shapeforsports.setText("Get In Shape\nFor A Specific Sport");
            clgainmuscle.setOnClickListener(this);
            clloseweight.setOnClickListener(this);
            clstayfit.setOnClickListener(this);
            clshapeforsports.setOnClickListener(this);

        }else if(holder.getItemViewType() == LAYOUT_THREE){
            ViewHolderThree v3 = (ViewHolderThree) holder;
            cl_man.setOnClickListener(this);
            cl_woman.setOnClickListener(this);

        }


    }

    public void setAgesUnclickable(){
        clteens.setClickable(false);
        cl20s.setClickable(false);
        cl30s.setClickable(false);
        cl40s.setClickable(false);
        cl50s.setClickable(false);
        cl60s.setClickable(false);
    }
    public void setGoalsUnClickable(){
        clgainmuscle.setClickable(false);
        clloseweight.setClickable(false);
        clshapeforsports.setClickable(false);
        clstayfit.setClickable(false);
    }
    public void setGenderUnClickable(){
        cl_man.setClickable(false);
        cl_woman.setClickable(false);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    @Override
    public void onClick(final View v) {
        v.setClickable(false);

        final ProgressBar progressBar = _Page2_UserDetails.progressBar;
        if(progressBar.getProgress() == 0) {
            setAgesUnclickable();
            pos = 1;
        }
        else if(progressBar.getProgress() == 33) {
            setGoalsUnClickable();
            pos = 2;
        }
        else if(progressBar.getProgress() == 66) {
            setGenderUnClickable();
            pos = 3;
        }
        /*else if(progressBar.getProgress() == 100) {
            pos = 4;
        }*/
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    _Page2_UserDetails.recyclerView.scrollToPosition(pos);
                }
            }, 500);

        v.setBackgroundColor(Color.parseColor("#1976d2"));
        _2_ProgressBarAnimation anim;
        if(progressBar.getProgress() == 66) {
            setGenderUnClickable();
            anim = new _2_ProgressBarAnimation(progressBar, progressBar.getProgress(), 100);
            _Page2_UserDetails.checkmark.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(context,_Page3_SelectWorkout.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }, 500);

        }else {
            anim = new _2_ProgressBarAnimation(progressBar, progressBar.getProgress(), progressBar.getProgress() + 33);
        }
            anim.setDuration(300);
            progressBar.startAnimation(anim);
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
        TextView tvTitle;
        TextView tv_shapeforsports;
        CardView cv_gainmuscle;
        CardView cv_loseweight;
        CardView cv_stayfit;
        CardView cv_shapeforsports;


        public ViewHolderTwo(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            cv_gainmuscle = itemView.findViewById(R.id.cv_gainmuscle);
            cv_loseweight = itemView.findViewById(R.id.cv_loseweight);
            cv_shapeforsports = itemView.findViewById(R.id.cv_shapeforsports);
            cv_stayfit = itemView.findViewById(R.id.cv_stayfit);

            tv_shapeforsports = itemView.findViewById(R.id.tvShapeForSports);

            clgainmuscle = itemView.findViewById(R.id.clgainmuscle);
            clloseweight = itemView.findViewById(R.id.clloseweight);
            clstayfit = itemView.findViewById(R.id.clgenerallyfit);
            clshapeforsports = itemView.findViewById(R.id.clshapeforsports);

        }

    }
    public class ViewHolderThree extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvMan;
        TextView tvWoman;


        public ViewHolderThree(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvMan = itemView.findViewById(R.id.tvMan);
            tvWoman = itemView.findViewById(R.id.tvWoman);

            cl_man = itemView.findViewById(R.id.clman);
            cl_woman = itemView.findViewById(R.id.clwoman);

        }

    }
}
