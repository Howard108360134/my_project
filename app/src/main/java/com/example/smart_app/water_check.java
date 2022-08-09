package com.example.smart_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class water_check extends AppCompatActivity {

    private Button back;
    private DatabaseReference reference;
    private FirebaseUser user;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_check);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(water_check.this, ProfileActivity.class));
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("Users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        final TextView water_tv = findViewById(R.id.tv3);
        reference.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null) {
                    String level = String.valueOf(userProfile.water);

                    water_tv.setText(level);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(water_check.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });

        final TextView fire_tv = findViewById(R.id.tv6);
        reference.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null) {
                    String status = String.valueOf(userProfile.set_fire);

                    int a = Integer.parseInt(status);

                    if(a == 1){
                        fire_tv.setText("Your Home is on fire");
                    }else if(a == 0){
                        fire_tv.setText("safe");
                    }

                    //fire_tv.setText(status);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(water_check.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });

        final TextView gas_tv = findViewById(R.id.tv9);
        reference.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null) {
                    String status = String.valueOf(userProfile.set_gas);

                    gas_tv.setText(status);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(water_check.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });
    }
}