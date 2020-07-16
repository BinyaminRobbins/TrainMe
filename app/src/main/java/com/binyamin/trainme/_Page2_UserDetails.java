package com.binyamin.trainme;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class _Page2_UserDetails extends AppCompatActivity {
    static Context context;
    static ProgressBar probar;
    static ConstraintLayout constraintLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._2_activity_user_details);

        Fragment_Gender fragment_gender = new Fragment_Gender();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.flFragment,fragment_gender).commit();
        //Fragment added to Frame Layout

        /*TextView trainme = findViewById(R.id.textViewTrainMe);
        String title = "TrainMe";
        SpannableStringBuilder ssb = new SpannableStringBuilder(title);

        ForegroundColorSpan fgcs = new ForegroundColorSpan(getResources().getColor(R.color.yellowButton));

        ssb.setSpan(fgcs,5,7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        trainme.setText(ssb);*/

        probar = findViewById(R.id.proBar);
        constraintLayout = findViewById(R.id.cl2);

         context = getApplicationContext();
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        _Page1_HomeScreen.progressButton.endAnimation();
    }
}
