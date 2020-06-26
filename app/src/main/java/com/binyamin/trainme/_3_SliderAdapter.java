package com.binyamin.trainme;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class _3_SliderAdapter extends RecyclerView.Adapter<_3_SliderAdapter.SliderViewHolder> implements View.OnClickListener {
    private List<_3_SliderItem> sliderItems;
    private ViewPager2 viewPager2;
    boolean colorChanged;

    public _3_SliderAdapter(List<_3_SliderItem> sliderItems, ViewPager2 viewPager2) {
       // this.sliderItems = sliderItems;
        this.viewPager2 = viewPager2;
        SQLiteDatabase database = _Page3_SelectWorkout.context.openOrCreateDatabase("Workouts", Context.MODE_PRIVATE,null);
        SliderList sliderList = new SliderList(database);
        this.sliderItems = sliderList.getSliderList();
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
        final String[] detailsArray = _Page3_SelectWorkout.context.getResources().getStringArray(R.array.descriptions);
        holder.setLockedImage(sliderItems.get(position));
        holder.setImage(sliderItems.get(position));
        holder.setTextViewHeader(sliderItems.get(position));
        holder.startButton.setTag(position);
        holder.startButton.setOnClickListener(this);
        holder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getVisibility() == View.VISIBLE){
                    holder.details.setVisibility(View.INVISIBLE);
                    holder.close.setVisibility(View.INVISIBLE);
                }
            }
        });
        holder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.close.getVisibility() == View.INVISIBLE ) {
                    holder.details.setVisibility(View.VISIBLE);
                    holder.close.setVisibility(View.VISIBLE);
                    holder.details.setText(detailsArray[position]);
                }

            }
        });
        holder.star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!colorChanged) {
                    holder.star.setImageResource(R.drawable.ic_action_star_clicked);
                    colorChanged = true;
                    //sliderItems.get(position).setAsFavorite(true);

                    try {
                        SQLiteDatabase database = _Page3_SelectWorkout.context.openOrCreateDatabase("Workouts",Context.MODE_PRIVATE,null);
                        //String query = "UPDATE  workouts  SET  isFavorite  = 'true' WHERE tagNum  = ' " + sliderItems.get(position).getTagNum() + " ' ";
                        //database.execSQL(query);
                        ContentValues cv = new ContentValues();
                        cv.put("isFavorite","true"); //These Fields should be your String values of actual column names

                        database.update("Workouts", cv, "tagNum="+sliderItems.get(position).getTagNum(), null);
                        Log.i("UPDATE","Should have updated TO FAVORITE");

                    }catch(Exception e){
                        e.printStackTrace();
                    }

                } else if (colorChanged) {
                    holder.star.setImageResource(R.drawable.ic_action_star);
                    colorChanged = false;

                    try {
                        SQLiteDatabase database = _Page3_SelectWorkout.context.openOrCreateDatabase("Workouts",Context.MODE_PRIVATE,null);
                        /*String query = "UPDATE  workouts  SET  isFavorite  = 'false' WHERE athleteName  = ' " + sliderItems.get(position).getAthleteName() + " ' ";
                        database.execSQL(query);*/

                        ContentValues cv = new ContentValues();
                        cv.put("isFavorite","false"); //These Fields should be your String values of actual column names

                        database.update("Workouts", cv, "tagNum ="+sliderItems.get(position).getTagNum(), null);
                        Log.i("UPDATE","Should have updated TO FAVORITE");

                        Log.i("UPDATE","Should have updated to NOT FAVORITE");

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

        /*if(position == sliderItems.size() - 2){
            viewPager2.post(runnable);
        }*/
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
        private ImageView lockedImage;
        private Button startButton;
        private ImageButton info;
        private ImageButton star;
        private TextView details;
        private ImageButton close;

        SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageSlide);
            textViewHeader = itemView.findViewById(R.id.textViewHeader);
            lockedImage = itemView.findViewById(R.id.imageButton_lock);
            startButton = itemView.findViewById(R.id.startButton);
            star = itemView.findViewById(R.id.imageButton_star);
            info = itemView.findViewById(R.id.imageButton_info);
            details = itemView.findViewById(R.id.textViewDetails);
            close = itemView.findViewById(R.id.imageButtonClose);
        }
        void setImage(_3_SliderItem sliderItem){
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(sliderItem.getImage());

        }
        void setTextViewHeader(_3_SliderItem sliderItem){
            textViewHeader.setText(sliderItem.getAthleteName());
        }
        void setLockedImage(_3_SliderItem sliderItem){
            if(sliderItem.getIfRequiresPremium() == true)
                lockedImage.setImageResource(R.drawable.ic_action_lock);
        }
    }
   /* private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            sliderItems.addAll(sliderItems);
            notifyDataSetChanged();
        }
    };*/
}
