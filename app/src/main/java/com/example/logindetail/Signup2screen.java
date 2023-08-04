package com.example.logindetail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Signup2screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup2screen);
    }

    public void nextsignup3screen(View view) {
        Intent intent = new Intent(getApplicationContext(), Signup3screen.class);
        startActivity(intent);
    }

    public void movetologinscreen(View view) {
        Intent intent = new Intent(getApplicationContext(), login.class);
        startActivity(intent);
    }
}