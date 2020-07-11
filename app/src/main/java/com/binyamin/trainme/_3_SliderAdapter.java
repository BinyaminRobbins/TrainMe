package com.binyamin.trainme;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class _3_SliderAdapter extends RecyclerView.Adapter<_3_SliderAdapter.SliderViewHolder> implements View.OnClickListener {
    private List<_3_SliderItem> sliderItems;
    private ViewPager2 viewPager2;
    boolean colorChanged;
    SQLiteDatabase database;
    SharedPreferences sharedPreferences;
    static String[] detailsArray;
    static Fragment_WorkoutInfo fragment_workoutInfo;

    public _3_SliderAdapter(List<_3_SliderItem> sliderItems, ViewPager2 viewPager2) {
        this.sliderItems = sliderItems;
        this.viewPager2 = viewPager2;
    }




    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout._3_slide_item_container,
                        parent,
                        false
                )
        );

    }

    @Override
    public void onBindViewHolder(@NonNull final SliderViewHolder holder, final int position) {
        sharedPreferences = _Page3_SelectWorkout.context.getSharedPreferences("com.binyamin.trainme",Context.MODE_PRIVATE);
         detailsArray = _Page3_SelectWorkout.context.getResources().getStringArray(R.array.descriptions);
        holder.setLockedImage(sliderItems.get(position));
        holder.setImage(sliderItems.get(position));
        holder.setTextViewHeader(sliderItems.get(position));
        holder.startButton.setTag(position);
        holder.startButton.setOnClickListener(this);

        holder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                fragment_workoutInfo = new Fragment_WorkoutInfo(position);
                activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fraginfo_fade_in,R.anim.fraginfo_fade_out).replace(R.id.fl_workoutInfo, fragment_workoutInfo).addToBackStack(null).commit();
            }
        });
        holder.star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!colorChanged) {
                    holder.star.setImageResource(R.drawable.ic_action_star_clicked);
                    colorChanged = true;

                    try {
                        database = _Page3_SelectWorkout.context.openOrCreateDatabase("Workouts",Context.MODE_PRIVATE,null);
                        ContentValues cv = new ContentValues();
                        cv.put("isFavorite","true"); //These Fields should be your String values of actual column names

                        database.update("Workouts", cv, "tagNum="+sliderItems.get(position).getTagNum(), null);

                    }catch(Exception e){
                        e.printStackTrace();
                    }

                } else if (colorChanged) {
                    holder.star.setImageResource(R.drawable.ic_action_star);
                    colorChanged = false;

                    try {
                        database = _Page3_SelectWorkout.context.openOrCreateDatabase("workouts",Context.MODE_PRIVATE,null);

                        ContentValues cv = new ContentValues();
                        cv.put("isFavorite","false"); //These Fields should be your String values of actual column names

                        database.update("workouts", cv, "tagNum ="+sliderItems.get(position).getTagNum(), null);
                    }catch(Exception e){
                        e.printStackTrace();
                    }

                }
            }
        });
        if (sliderItems.get(position).getIsFavorite() == true) {
            holder.star.setImageResource(R.drawable.ic_action_star_clicked);
        } else {
            holder.star.setImageResource(R.drawable.ic_action_star);
        }
    }

    @Override
    public void onClick(View v) {
        Context context = _Page3_SelectWorkout.context;
        if(v.getId() == R.id.startButton){
            Intent intent = new Intent(context,_Page4_AthleteWorkout.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("tag",v.getTag().toString());
            context.startActivity(intent);
        }

    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    public class SliderViewHolder extends RecyclerView.ViewHolder{

        private RoundedImageView imageView;
        private TextView textViewHeader;
        private View locked;
        private Button startButton;
        private ImageButton info;
        private ImageButton star;

        SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageSlide);
            textViewHeader = itemView.findViewById(R.id.textViewHeader);
            locked = itemView.findViewById(R.id.include_lock);
            startButton = itemView.findViewById(R.id.startButton);
            star = itemView.findViewById(R.id.imageButton_star);
            info = itemView.findViewById(R.id.imageButton_info);
        }
        void setImage(_3_SliderItem sliderItem){
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(sliderItem.getImage());

        }
        void setTextViewHeader(_3_SliderItem sliderItem){
            textViewHeader.setText(sliderItem.getAthleteName());
        }
        void setLockedImage(_3_SliderItem sliderItem){
            if(sliderItem.getIfRequiresPremium() == true){
                locked.setVisibility(View.VISIBLE);
            }

        }
    }
}
