package com.example.healthapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class LogWorkout extends AppCompatActivity {
    public static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_workout);


//        for (int i = 0; i < 10; i++) {
//            fragmentManager = getSupportFragmentManager();
//            if(findViewById(R.id.exerciseScrollView)!=null){
//                if(savedInstanceState!=null){
//                    return;
//                }
//
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                ExerciseName exerciseName = new ExerciseName();
//                Button button = findViewById(R.id.viewExerciseBtn);
//                Bundle b = new Bundle();
////                b.putString("exercise", exercises[i]);
////                ExerciseName.setArguments(b);
//                fragmentTransaction.add(R.id.exerciseDisplay, exerciseName).commit();
//            }
//        }
    }

    public void navProfile(View v){
        startActivity(new Intent(LogWorkout.this, UserProfile.class));
    }

    public void navExercises(View v){
        startActivity(new Intent(LogWorkout.this, MainExcercises.class));
    }

    public void navHome(View v){
        startActivity(new Intent(LogWorkout.this, HomePage.class));
    }

    public void navLog(View v){
        startActivity(new Intent(LogWorkout.this, CalendarLog.class));
    }
}