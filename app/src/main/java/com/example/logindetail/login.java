package com.example.logindetail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {
    Button createaccountbtn, loginbtn;
    TextInputLayout username_var, password_var;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_design);
        createaccountbtn = findViewById(R.id.create_account_login_btn);
        loginbtn = findViewById(R.id.login_btn);
        username_var = findViewById(R.id.username_text_field_design);
        password_var = findViewById(R.id.password_input_field);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usename_ = username_var.getEditText().getText().toString();
                String password_ = password_var.getEditText().getText().toString();
                if (!usename_.isEmpty()){
                    username_var.setError(null);
                    username_var.setErrorEnabled(false);
                    if (!password_.isEmpty()){
                        password_var.setError(null);
                        password_var.setErrorEnabled(false);

                        final String username_data = username_var.getEditText().getText().toString();
                        final String password_data = password_var.getEditText().getText().toString();

                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference = firebaseDatabase.getReference("Datauser");

                        Query check_username = databaseReference.orderByChild("username").equalTo(username_data);
                        check_username.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()){
                                    username_var.setError(null);
                                    username_var.setErrorEnabled(false);
                                    String passwordcheck = snapshot.child(username_data).child("password").getValue(String.class);
                                    if (passwordcheck.equals(password_data)){
                                        password_var.setError(null);
                                        password_var.setErrorEnabled(false);
                                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), Signup1screen.class);
                                        startActivity(intent);
                                        finish();

                                    }else {
                                        password_var.setError("Wrong password");

                                    }

                                }else {
                                    username_var.setError("Username does not exist");

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }else{
                        password_var.setError("please enter the password");
                    }
                }else {
                    username_var.setError("please enter the username");
                }

            }
        });


    }

    public void nextsignup1screen(View view) {
        Intent intent = new Intent(getApplicationContext(), Signup1screen.class);
        startActivity(intent);
    }

    public void toolbarscreen(View view) {
        Intent intent = new Intent(getApplicationContext(), toolbarscreen.class);
        startActivity(intent);
    }
}