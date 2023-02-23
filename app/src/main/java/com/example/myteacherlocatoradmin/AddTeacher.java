package com.example.myteacherlocatoradmin;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddTeacher extends AppCompatActivity {

    EditText name_editText;
    EditText username_editText;
    EditText pin_editText;
    EditText latitude_editText;
    EditText longitude_editText;

    Button add_button;
    Button addArea_button;

    DatabaseReference teachersRef;

    ActivityResultLauncher<String> lat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);

        name_editText = findViewById(R.id.name_editText);
        username_editText = findViewById(R.id.username_editText);
        pin_editText = findViewById(R.id.pin_editText);
        latitude_editText = findViewById(R.id.latitude_editText);
        longitude_editText = findViewById(R.id.longitude_editText);

        add_button = findViewById(R.id.add_button);
        addArea_button = findViewById(R.id.addArea_button);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validate()){
                    teachersRef = FirebaseDatabase.getInstance().getReference("Teachers/"+username_editText.getText().toString());

                    teachersRef.child("name").setValue(name_editText.getText().toString());
                    teachersRef.child("pin").setValue(pin_editText.getText().toString());
                    teachersRef.child("username").setValue(username_editText.getText().toString());
                    teachersRef.child("area").child("lat").setValue(latitude_editText.getText().toString());
                    teachersRef.child("area").child("long").setValue(longitude_editText.getText().toString());

                    name_editText.setText("");
                    pin_editText.setText("");
                    username_editText.setText("");
                    latitude_editText.setText("");
                    longitude_editText.setText("");

                    Toast.makeText(AddTeacher.this, "Teacher is added!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        addArea_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddTeacher.this,MapsActivity.class);
                startActivityForResult(intent,1);
            }
        });

    }

    private boolean validate() {
        boolean valid = true;

        if (name_editText.getText().toString().equals("")){
            name_editText.setError("Enter Name");
            valid=false;
        }

        if (username_editText.getText().toString().equals("")){
            username_editText.setError("Enter Username");
            valid=false;
        }
        if (pin_editText.getText().toString().equals("")){
            pin_editText.setError("Enter Pin");
            valid=false;
        }
        if (latitude_editText.getText().toString().equals("")){
            latitude_editText.setError("Enter Latitude");
            valid=false;
        }
        if (longitude_editText.getText().toString().equals("")){
            longitude_editText.setError("Enter Longitude");
            valid=false;
        }


        return valid;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            String lat=data.getStringExtra("LAT");
            String longi=data.getStringExtra("LONG");
            latitude_editText.setText(lat);
            longitude_editText.setText(longi);

        }
    }
}