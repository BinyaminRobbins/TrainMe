package com.binyamin.trainme;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import static android.content.Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION;
import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener{
    SharedPreferences sharedPreferences;
    ImageView profileImage;
    Intent takePicture;
    Intent pickPhoto;

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
        //{ Profile Image
        profileImage = view.findViewById(R.id.profilePic);
        profileImage.setOnClickListener(this);
        if(sharedPreferences.contains("profileBitmap")) {
            String bitmap = sharedPreferences.getString("profileBitmap", "404 error");
            byte[] imageAsBytes = Base64.decode(bitmap.getBytes(), Base64.DEFAULT);
            profileImage.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        }else if(sharedPreferences.contains("profileURI")){
            String uri = sharedPreferences.getString("profileURI","404 error");
            profileImage.setImageURI(Uri.parse(uri));
        }
        //} switch {
        Switch scroll = view.findViewById(R.id.switch1);
        boolean scrollOn = sharedPreferences.getBoolean("scrollOn",true);
        if(scrollOn){
            scroll.setChecked(true);
        }else scroll.setChecked(false);

        scroll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    sharedPreferences.edit().putBoolean("scrollOn",true).apply();
                }else{
                    sharedPreferences.edit().putBoolean("scrollOn",false).apply();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.profilePic){
            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
            dialog.setMessage("Select or Take a profile photo");
            dialog.setTitle("Profile Photo");
            dialog.setNeutralButton("Take Selfie", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Take Picture
                    takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);//zero can be replaced with any action code (called requestCode)
                }
            });
            dialog.setPositiveButton("Select Photo", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Select photo from gallery
                    pickPhoto = new Intent(Intent.ACTION_OPEN_DOCUMENT,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    pickPhoto.addFlags(FLAG_GRANT_READ_URI_PERMISSION);
                    pickPhoto.addFlags(FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                    startActivityForResult(pickPhoto , 1);//one can be replaced with any action code
                }
            });
            dialog.show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case 0:
                if(requestCode == 0){
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                   // profileImage.setImageBitmap(imageBitmap);
                    //sharedPreferences.edit().putString("bitmap",imageUri.toString()).apply();

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
                    byte[] b = baos.toByteArray();
                    String encoded = Base64.encodeToString(b, Base64.DEFAULT);
                    byte[] imageAsBytes = Base64.decode(encoded.getBytes(), Base64.DEFAULT);
                    profileImage.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));

                    if(sharedPreferences.contains("profileURI")){
                        sharedPreferences.edit().remove("profileURI").commit();
                    }
                    sharedPreferences.edit().putString("profileBitmap",encoded).commit();

                }

                break;
            case 1:
                if(requestCode == 1){
                    profileImage.setImageURI(null);
                    Uri selectedImage = data.getData();
                    profileImage.setImageURI(selectedImage);
                    if(sharedPreferences.contains("profileBitmap")){
                        sharedPreferences.edit().remove("profileBitmap").commit();
                    }
                    sharedPreferences.edit().putString("profileURI",selectedImage.toString()).commit();
                }
                break;
        }

    }
}
