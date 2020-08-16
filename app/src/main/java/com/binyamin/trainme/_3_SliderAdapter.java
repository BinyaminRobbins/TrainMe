package com.binyamin.trainme;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.makeramen.roundedimageview.RoundedImageView;
import java.util.ArrayList;

public class _3_SliderAdapter extends RecyclerView.Adapter<_3_SliderAdapter.SliderViewHolder>{
    private ArrayList <_3_SliderItem> sliderItems;
    private boolean colorChanged = false;
    private SliderList sliderList;
    static String[] detailsArray;
    static Fragment_WorkoutInfo fragment_workoutInfo;
    static private AlertDialog dialog = null;
    static private AlertDialog.Builder builder = null;
    private PurchaseProduct purchaseProduct;
    private boolean isWorkoutsFragment;

    public _3_SliderAdapter(SliderList sliderList, ArrayList<_3_SliderItem> sliderItems, PurchaseProduct product,boolean isWorkouts) {
        this.sliderList = sliderList;
        this.sliderItems = sliderItems;
        this.purchaseProduct = product;
        this.isWorkoutsFragment = isWorkouts;
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

    private String getTableName(){
        String tableName;
        if(isWorkoutsFragment){
            tableName = _Page3_SelectWorkout.context.getResources().getString(R.string.workoutsTable);
        }else{
            tableName = _Page3_SelectWorkout.context.getResources().getString(R.string.dietsTable);
        }
        return tableName;
    }

    private String[] getDetailsArray(){
        if(isWorkoutsFragment){
            detailsArray = _Page3_SelectWorkout.context.getResources().getStringArray(R.array.workoutDescriptions);
        }else {
            detailsArray = _Page3_SelectWorkout.context.getResources().getStringArray(R.array.dietDescriptions);
        }
        return detailsArray;
    }

    @Override
    public void onBindViewHolder(@NonNull final SliderViewHolder holder, final int position) {
        System.out.println("Position = " + position);
        holder.startButton.setTag(position);
        holder.setImage(sliderItems.get(position));
        holder.setLockedImage(sliderItems.get(position));
        holder.setTextViewHeader(sliderItems.get(position));
        holder.progressBar.setElevation(5f);
        holder.progressBar.setVisibility(View.GONE);

        if(isWorkoutsFragment){
            holder.startButton.setText("See Workouts");
            getDetailsArray();
        }else {
            holder.startButton.setText("See Diet");
            holder.startButton.setTextSize(16f);
            holder.startButton.setBackgroundResource(R.drawable.button_custom_selectdiet);
            //Diets Info:
            getDetailsArray();
        }

        holder.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if(v.getId() == R.id.startButton){
                     holder.progressBar.setElevation(30);
                     holder.progressBar.setVisibility(View.VISIBLE);
                     holder.startButton.setText("");
                     if(!sliderItems.get(position).getIfRequiresPremium()){
                         openAct4(v.getContext(),v);
                         holder.progressBar.setVisibility(View.GONE);
                         if(AthleteWorkoutsAndDietsFragment.tabPosition == 0){
                             holder.startButton.setText("See Workouts");
                         }else{
                             holder.startButton.setText("See Diet");
                         }
                     }else if(sliderItems.get(position).getIfRequiresPremium()){
                         if(purchaseProduct.checkIfOwned()){
                             openAct4(v.getContext(),v);
                             holder.progressBar.setVisibility(View.GONE);
                             if(AthleteWorkoutsAndDietsFragment.tabPosition == 0){
                                 holder.startButton.setText("See Workouts");
                             }else{
                                 holder.startButton.setText("See Diet");
                             }
                         }else{
                             holder.progressBar.setVisibility(View.GONE);
                             if(AthleteWorkoutsAndDietsFragment.tabPosition == 0){
                                 holder.startButton.setText("See Workouts");
                             }else{
                                 holder.startButton.setText("See Diet");
                             }
                             builder = new AlertDialog.Builder(v.getContext());
                             builder.setTitle("Upgrade to Premium");
                             builder.setIcon(R.drawable.ic_action_premium);
                             builder.setMessage("You have discovered a premium feature.");
                             builder.setPositiveButton("Check It Out", new DialogInterface.OnClickListener() {
                                 @Override
                                 public void onClick(DialogInterface dialog, int which) {
                                     _Page3_SelectWorkout.navController.navigate(R.id.menuNavigation_premium);
                                 }
                             });
                             builder.setNegativeButton("Not now", null);
                             dialog = builder.create();
                             dialog.show();
                         }
                     }
                 }
            }
        });

        holder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                if(AthleteWorkoutsAndDietsFragment.tabPosition == 0) {
                    fragment_workoutInfo = new Fragment_WorkoutInfo(position,getDetailsArray());
                }else if(AthleteWorkoutsAndDietsFragment.tabPosition == 1){
                    fragment_workoutInfo = new Fragment_WorkoutInfo(position,getDetailsArray());
                }
                activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fraginfo_fade_in, R.anim.fraginfo_fade_out).replace(R.id.fl_workoutInfo, fragment_workoutInfo).addToBackStack(null).commit();
            }
        });
        holder.star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!colorChanged) {
                    holder.star.setImageResource(R.drawable.ic_action_star_clicked);
                    colorChanged = true;
                    try {
                        ContentValues cv = new ContentValues();
                        cv.put("isFavorite","true"); //These Fields should be your String values of actual column names

                        sliderList.getDB().update(getTableName(), cv, "tagNum ="+sliderItems.get(position).getTagNum(), null);
                        System.out.println(sliderItems.get(position).getAthleteName() + " Added to favorites (tagnum = " + sliderItems.get(position).getTagNum() + ")");

                    }catch(Exception e){
                        e.printStackTrace();
                        Toast.makeText(_Page3_SelectWorkout.context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else if (colorChanged) {
                    holder.star.setImageResource(R.drawable.ic_action_star);
                    colorChanged = false;

                    try {
                        ContentValues cv = new ContentValues();
                        cv.put("isFavorite","false"); //These Fields should be your String values of actual column names

                        sliderList.getDB().update(getTableName(), cv, "tagNum ="+sliderItems.get(position).getTagNum(), null);
                        System.out.println(sliderItems.get(position).getAthleteName() + " Removed from favorites");


                    }catch(Exception e){
                        e.printStackTrace();
                        Toast.makeText(_Page3_SelectWorkout.context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        //On start show which items are already favorites
        if (sliderItems.get(position).getIsFavorite()) {
            holder.star.setImageResource(R.drawable.ic_action_star_clicked);
        } else {
            holder.star.setImageResource(R.drawable.ic_action_star);
        }

    }

    private void openAct4(Context context, View v){
        Intent intent = new Intent(context,_Page4_AthleteWorkout.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("tag",v.getTag().toString());
        Log.i("TagNum",v.getTag().toString());
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    public class SliderViewHolder extends RecyclerView.ViewHolder{

        private RoundedImageView imageView;
        private TextView textViewHeader;
        private TextView locktv;
        private Button startButton;
        private ImageButton info;
        private ImageButton star;
        ProgressBar progressBar;

        SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageSlide);
            textViewHeader = itemView.findViewById(R.id.textViewHeader);
            locktv = itemView.findViewById(R.id.locktv);
            startButton = itemView.findViewById(R.id.startButton);
            star = itemView.findViewById(R.id.imageButton_star);
            info = itemView.findViewById(R.id.imageButton_info);
            progressBar = itemView.findViewById(R.id.startProgressBar);
        }
        void setImage(_3_SliderItem sliderItem){
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(sliderItem.getImage());

        }
        void setTextViewHeader(_3_SliderItem sliderItem){
            textViewHeader.setText(sliderItem.getAthleteName());
        }
        void setLockedImage(_3_SliderItem sliderItem){
           if(sliderItem.getIfRequiresPremium()){
                locktv.setVisibility(View.VISIBLE);
            }

        }
    }
}
