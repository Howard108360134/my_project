package com.example.smart_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Light_Control extends AppCompatActivity {

    private Button on, off, back;
    private DatabaseReference reference;
    private FirebaseUser user;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_control);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Light_Control.this, ProfileActivity.class));
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("Users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        on = findViewById(R.id.ON);
        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                reference.child(userID).child("set_temp").setValue("18a");
            }
        });

        off = findViewById(R.id.OFF);
        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                reference.child(userID).child("set_temp").setValue("18b");
            }
        });
    }
}