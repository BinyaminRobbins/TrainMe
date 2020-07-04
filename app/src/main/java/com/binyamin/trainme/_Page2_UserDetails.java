package com.binyamin.trainme;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class _Page2_UserDetails extends AppCompatActivity {
    static RecyclerView recyclerView;
    static ProgressBar progressBar;
    static ImageView checkmark;
    static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._2_activity_user_details);

        Fragment_UserAge fragUserAge = new Fragment_UserAge();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.flFragment,fragUserAge).commit();
        //Fragment added to Frame Layout


        progressBar = findViewById(R.id.progressBar);

         checkmark = findViewById(R.id.checkmark);

         context = getApplicationContext();

    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        _Page1_HomeScreen.progressButton.endAnimation();

    }
}
