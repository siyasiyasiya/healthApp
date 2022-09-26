package com.example.healthapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class CalendarLog extends AppCompatActivity {
    String date = "";
    DBHelper DB;
    ArrayList<ImageView> stars = new ArrayList<>();
    CalendarView calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DB = new DBHelper(this);
//        day = ((HomePage)getActivity()).workoutDay;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_log);
        calendar = findViewById(R.id.calendar);


        ImageView star1 = findViewById(R.id.star1);
        ImageView star2 = findViewById(R.id.star2);
        ImageView star3 = findViewById(R.id.star3);
        ImageView star4 = findViewById(R.id.star4);
        ImageView star5 = findViewById(R.id.star5);

        stars.add(star1);
        stars.add(star2);
        stars.add(star3);
        stars.add(star4);
        stars.add(star5);

        loadData();

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                date = month + "/" + dayOfMonth + "/" + year;
                loadData();
            }
        });
    }

    public void loadData() {
        TextView title = findViewById(R.id.title);
        TextView duration = findViewById(R.id.durationLbl);
        TextView list = findViewById(R.id.exerciseListLbl);

        for (ImageView star : stars) {
            star.setVisibility(View.VISIBLE);
        }
        duration.setVisibility(View.VISIBLE);
        list.setVisibility(View.VISIBLE);

        try (Cursor res = DB.getData()) {
            if (res.getCount() == 0) {
                title.setText("No Workout Logged");
                for (ImageView star : stars) {
                    star.setVisibility(View.INVISIBLE);
                }
                duration.setVisibility(View.INVISIBLE);
                list.setVisibility(View.INVISIBLE);
                return;
            }

            boolean read = false;
            boolean readOnce = false;
            while (res.moveToNext()) {
                if (res.getString(0).equals(date)) {
                    read = true;
                    readOnce = true;
                }
                if (read) {
                    title.setText("Workout Log");
                    duration.setText("Duration: " + res.getString(1) + " Minutes");
                    list.setText("Exercises: " + res.getString(3));
                    System.out.println(res.getString(2));
                    colorStars(Integer.parseInt(res.getString(2)) - 1);
                    read = false;
                }
            }

            if(!readOnce){
                title.setText("No Workout Logged");
                for (ImageView star : stars) {
                    star.setVisibility(View.INVISIBLE);
                }
                duration.setVisibility(View.INVISIBLE);
                list.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void colorStars(int place){
        for (ImageView star: stars) {
            star.setImageResource(R.drawable.gold_star);
        }

        for (int i = 0; i <= place; i++) {
            stars.get(i).setImageResource(R.drawable.gold_star_full);
        }
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