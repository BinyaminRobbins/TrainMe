package com.binyamin.hollywoodgainz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    public void toGenderActivity(View view) {
        if (sharedPreferences.contains("goals")) {
            Intent intent = new Intent(getApplicationContext(), Main4Activity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
            startActivity(intent);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = this.getSharedPreferences("com.binyamin.hollywoodgainz",Context.MODE_PRIVATE);
        //sharedPreferences.edit().clear().apply();
        Button button = findViewById(R.id.button);
        TextView textView = findViewById(R.id.textView);

    }
}
