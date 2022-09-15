package com.example.healthapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GetUserPreferences extends AppCompatActivity {
    private Button strengthBtn, plyoBtn, cardioBtn, flexibilityBtn, powerliftingBtn, weightliftingBtn;
    private String fitnessGoal = "";
    private TextView goalErr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_user_preferences);

        strengthBtn = findViewById(R.id.strengthBtn);
        plyoBtn = findViewById(R.id.plyoBtn);
        cardioBtn = findViewById(R.id.cardioBtn);
        flexibilityBtn = findViewById(R.id.flexibilityBtn);
        powerliftingBtn = findViewById(R.id.powerliftiingBtn);
        weightliftingBtn = findViewById(R.id.weightliftingBtn);
        goalErr = findViewById(R.id.goalErr);

        View dragLegs = findViewById(R.id.dragLegs);


        dragLegs.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });

        strengthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fitnessGoal = "strength";
            }
        });

        plyoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fitnessGoal = "plyometrics";
            }
        });

        cardioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fitnessGoal = "cardio";
            }
        });

        flexibilityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fitnessGoal = "flexibility";
            }
        });

        powerliftingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fitnessGoal = "powerlifting";
            }
        });

        weightliftingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fitnessGoal = "weightlifting";
            }
        });
    }

    public void completeSetUp(View view){
        boolean check = true;
        if (fitnessGoal.equals("")){
            check=false;
            goalErr.setText("Please select your workout goal");
        }else
            goalErr.setText("");

        if(check) {
//            startActivity(new Intent(GetUserPreferences.this, GetUserData.class));
        }
    }
}