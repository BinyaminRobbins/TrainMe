package com.binyamin.hollywoodgainz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Main3Activity extends AppCompatActivity {
    ImageView imageViewMan;
    ImageView imageViewWoman;
    SharedPreferences sharedPreferences;

    public void setGender(View v){
        int id = v.getId();
        if (id == imageViewMan.getId()) {
            sharedPreferences.edit().putString("gender", "man").apply();
        }else sharedPreferences.edit().putString("gender","woman").apply();

        Intent intent = new Intent(getApplicationContext(),Main4Activity.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        imageViewMan = findViewById(R.id.imageViewMan);
        imageViewWoman = findViewById(R.id.imageViewWoman);

        sharedPreferences = this.getSharedPreferences("com.binyamin.hollywoodgainz", Context.MODE_PRIVATE);


        Intent intent = getIntent();
        //Main2Activity.class.

    }
}
