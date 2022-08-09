package com.example.smart_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Activity_R extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView banner;
    private EditText ed_name, ed_age, ed_email, ed_password;
    private Button registeruser, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r);

        mAuth = FirebaseAuth.getInstance();

        banner = findViewById(R.id.banner);
        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Activity_R.this, MainActivity.class));
            }
        });

        registeruser = findViewById(R.id.registeruser);
        registeruser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registeruser();
            }
        });

        ed_name = findViewById(R.id.name);
        ed_age = findViewById(R.id.age);
        ed_email = findViewById(R.id.email);
        ed_password = findViewById(R.id.password);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Activity_R.this, MainActivity.class));
            }
        });

    }

    private void registeruser() {
        String email = ed_email.getText().toString().trim();
        String password = ed_password.getText().toString().trim();
        String name = ed_name.getText().toString().trim();
        String age = ed_age.getText().toString().trim();
        Double temp = 0.0;
        String status = ("0");
        String set_temp = ("25");
        Integer water = 1;
        Integer set_fire = 0;
        Double set_temperature = 0.0;
        Double set_gas = 0.0;
        Double set_humidity = 0.0;

        if(name.isEmpty()){
            ed_name.setError("Your name is required!");
            ed_name.requestFocus();
            return;
        }

        if(age.isEmpty()){
            ed_age.setError("Age is required!");
            ed_age.requestFocus();
            return;
        }

        if(email.isEmpty()){
            ed_email.setError("Email is required!");
            ed_email.requestFocus();
            return;
        }

        if(password.isEmpty()){
            ed_password.setError("Password is required!");
            ed_password.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            ed_email.setError("Please provide valid email!");
            ed_email.requestFocus();
            return;
        }

        if(password.length() < 6){
            ed_password.setError("Password langth should be more than 6 characters!");
            ed_password.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(name, age, email ,temp ,status ,set_temp, water, set_fire, set_temperature, set_gas, set_humidity);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Activity_R.this, "User has been registered successfully", Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(Activity_R.this, "Failed to register! Please try again!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(Activity_R.this, "Failed to register!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}