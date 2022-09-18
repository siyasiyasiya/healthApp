package com.example.healthapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainExcercises extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_excercises);
    }

    public void navHome(View v){
        startActivity(new Intent(MainExcercises.this, HomePage.class));
    }

    public void navProfile(View v){
        startActivity(new Intent(MainExcercises.this, UserProfile.class));
    }
}