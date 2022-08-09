package com.example.smart_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;



public class MainFunction extends AppCompatActivity {

    public User set_temp;
    private FirebaseAuth mAuth;
    private TextView tv3, onoff, f_tv;
    private Button send, back;
    private Switch power;
    protected EditText temperature;
    private DatabaseReference reference, ref_status, ref_set_temp;
    private FirebaseUser user;
    private String a;
    private String temp;
    private String userID;
    private int sw_status, tv_temp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_function);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainFunction.this, ProfileActivity.class));
            }
        });

        mAuth = FirebaseAuth.getInstance();

        temperature = findViewById(R.id.temperature);
        send = findViewById(R.id.send);
        temperature.setVisibility(View.GONE);
        send.setVisibility(View.GONE);

        onoff = findViewById(R.id.tv5);

        reference = FirebaseDatabase.getInstance().getReference("Users");
        //ref_status = FirebaseDatabase.getInstance().getReference("Users/status");
        //ref_set_temp = FirebaseDatabase.getInstance().getReference("Users/set_temp");
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        final TextView temp_tv = findViewById(R.id.tv3);
        reference.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    String temp_u = String.valueOf(userProfile.set_temperature);

                    temp_tv.setText(temp_u);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainFunction.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });

        final TextView hud_tv = findViewById(R.id.tv5);
        reference.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    String temp_u = String.valueOf(userProfile.set_humidity);

                    temp_tv.setText(temp_u);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainFunction.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });

        //reference.child(userID).child("status").equalTo("1").startAt();

    /*    reference.child(userID).child("status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    String status_u = String.valueOf(userProfile.status);
                    String set_temp_u = String.valueOf(userProfile.set_temp);
                    int d = Integer.parseInt(status_u);
                    int e = Integer.parseInt(set_temp_u);

                    if(d == 1){
                        power.setChecked(true);
                        temperature.setVisibility(View.VISIBLE);
                        send.setVisibility(View.VISIBLE);
                        onoff.setText("ON");
                        temperature.setText(e);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainFunction.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });
*/
        status();


        power = findViewById(R.id.switch1);


       /* if(sw_status == 1){
            boolean value = true;
            temperature.setVisibility(View.VISIBLE);
            send.setVisibility(View.VISIBLE);
            onoff.setText("ON");
            temperature.setText(tv_temp);
            final SharedPreferences sharedPreferences = getSharedPreferences("isChecked", 0);
            value = sharedPreferences.getBoolean("isChecked", value);
            power.setChecked(value);
        }
*/


        power.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked == true){
                    temperature.setVisibility(View.VISIBLE);
                    send.setVisibility(View.VISIBLE);
                    //onoff.setText("ON");



                    send = findViewById(R.id.send);
                    send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            temp = temperature.getText().toString().trim();
                            final String c = temp;

                            if(temp.isEmpty()){
                                temperature.setError("temperature is required!");
                                temperature.requestFocus();
                                return;
                            }

                            int a = Integer.parseInt(temp);

                            if(a < 16){
                                temperature.setError("The temperature needs to be above 16℃!");
                                temperature.requestFocus();
                                return;
                            }
                            if(a > 30){
                                temperature.setError("The temperature needs to be below 30℃!");
                                temperature.requestFocus();
                                return;
                            }

                            reference.child(userID).child("set_temp").setValue(c);
                            reference.child(userID).child("status").setValue("1");
                            sw_status = 1;
                            tv_temp = Integer.parseInt(temp);
                        }
                    });



                }else{
                    temperature.setVisibility(View.GONE);
                    send.setVisibility(View.GONE);
                    //onoff.setText("OFF");
                    reference.child(userID).child("status").setValue("0");
                    sw_status = 0;

                }
            }
        });



    }

    private void status() {
        if(sw_status == 1){
            boolean value = true;
            temperature.setVisibility(View.VISIBLE);
            send.setVisibility(View.VISIBLE);
            onoff.setText("ON");
            temperature.setText(tv_temp);
            final SharedPreferences sharedPreferences = getSharedPreferences("isChecked", 0);
            value = sharedPreferences.getBoolean("isChecked", value);
            power.setChecked(value);
        }
    }


}