package com.example.logindetail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Signup3screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup3screen);
    }

    public void movetologinscreen(View view) {
        Intent intent = new Intent(getApplicationContext(), login.class);
        startActivity(intent);
    }
}