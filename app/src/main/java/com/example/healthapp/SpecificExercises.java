package com.example.healthapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;

public class SpecificExercises extends AppCompatActivity {
    String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_exercises);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            type = extras.getString("type");
        }
    }

    public void goBack(View v){
        startActivity(new Intent(SpecificExercises.this, MainExcercises.class));
    }

    public void navHome(View v){
        startActivity(new Intent(SpecificExercises.this, HomePage.class));
    }

    public void navProfile(View v){
        startActivity(new Intent(SpecificExercises.this, UserProfile.class));
    }
}