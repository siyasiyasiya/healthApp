package com.example.healthapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Button;

public class LogWorkout extends AppCompatActivity {
    public static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_workout);


        for (int i = 0; i < 10; i++) {
            fragmentManager = getSupportFragmentManager();
            if(findViewById(R.id.exerciseScrollView)!=null){
                if(savedInstanceState!=null){
                    return;
                }

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ExerciseDisplayFragment exerciseDisplayFragment = new ExerciseDisplayFragment();
                Button button = findViewById(R.id.viewExerciseBtn);
                Bundle b = new Bundle();
//                b.putString("exercise", exercises[i]);
                exerciseDisplayFragment.setArguments(b);
                fragmentTransaction.add(R.id.exerciseScrollView, exerciseDisplayFragment).commit();
            }
        }
    }


}