package com.binyamin.trainme;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    SharedPreferences sharedPreferences;


    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //UserName{
        sharedPreferences = _Page3_SelectWorkout.context.getSharedPreferences("com.binyamin.trainme", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "404 error");
        final TextView tvUsername = view.findViewById(R.id.textViewUsername);
        if (username != null) {
            tvUsername.setText(username);
        }
        ImageButton imageButton = view.findViewById(R.id.imageButtonEditName);
        sharedPreferences = getContext().getSharedPreferences("com.binyamin.trainme", Context.MODE_PRIVATE);
        final EditText input = new EditText(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                final EditText edittext = new EditText(_Page3_SelectWorkout.context);
                alert.setMessage("Make Changes Below");
                alert.setTitle("Edit Name");
                edittext.setTextColor(getResources().getColor(R.color.colorPrimary));
                alert.setView(edittext);

                alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String txt = edittext.getText().toString();
                        if (txt == null || txt == " " || txt.isEmpty() || txt == "  " || txt == "   " || txt == "    " || txt == "     " || txt == "      " || txt == "       " || txt == "        " || txt == "         ") {
                            Toast.makeText(getContext(), "Name can't be empty", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        sharedPreferences.edit().putString("username", txt).commit();
                        tvUsername.setText(txt);
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                    }
                });

                alert.show();
            }
        });
        //} Gender {
        TextView genderTxt = view.findViewById(R.id.textviewUserGender);
        String gender = sharedPreferences.getString("usergender", "404 error");
        genderTxt.setText(gender);
        //} Goals {
        TextView goalsTxt = view.findViewById(R.id.textViewUserGoals);
        String goals = sharedPreferences.getString("usergoals", "404 error");
        goalsTxt.setText(goals);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

}
