package com.example.healthapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

public class CalendarLog extends AppCompatActivity {
    String day = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CalendarView calendar = findViewById(R.id.calendar);
//        day = ((HomePage)getActivity()).workoutDay;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_log);

//        float scalingFactor = 2f; // scale down to half the size
//        calendar.setScaleX(scalingFactor);
//        calendar.setScaleY(scalingFactor);
    }


    public void navProfile(View v){
        startActivity(new Intent(CalendarLog.this, UserProfile.class));
    }

    public void navExercises(View v){
        startActivity(new Intent(CalendarLog.this, MainExcercises.class));
    }

    public void navLogWorkout(View v){
        startActivity(new Intent(CalendarLog.this, LogWorkout.class));
    }

    public void navHome(View v){
        startActivity(new Intent(CalendarLog.this, HomePage.class));
    }
}