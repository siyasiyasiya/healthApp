package com.example.healthapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GetUserPreferences extends AppCompatActivity {
    private String fitnessGoal = "";
    private TextView goalErr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_user_preferences);

        View strengthBtn = findViewById(R.id.strengthBtn);
        View plyoBtn = findViewById(R.id.plyoBtn);
        View cardioBtn = findViewById(R.id.cardioBtn);
        View flexibilityBtn = findViewById(R.id.flexibilityBtn);
        View powerliftingBtn = findViewById(R.id.powerliftiingBtn);
        View weightliftingBtn = findViewById(R.id.weightliftingBtn);
        goalErr = findViewById(R.id.goalErr);

        strengthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strengthBtn.setSelected(true);
                plyoBtn.setSelected(false);
                cardioBtn.setSelected(false);
                flexibilityBtn.setSelected(false);
                powerliftingBtn.setSelected(false);
                weightliftingBtn.setSelected(false);
                fitnessGoal = "strength";
            }
        });

        plyoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strengthBtn.setSelected(false);
                plyoBtn.setSelected(true);
                cardioBtn.setSelected(false);
                flexibilityBtn.setSelected(false);
                powerliftingBtn.setSelected(false);
                weightliftingBtn.setSelected(false);
                fitnessGoal = "plyometrics";
            }
        });

        cardioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strengthBtn.setSelected(false);
                plyoBtn.setSelected(false);
                cardioBtn.setSelected(true);
                flexibilityBtn.setSelected(false);
                powerliftingBtn.setSelected(false);
                weightliftingBtn.setSelected(false);
                fitnessGoal = "cardio";
            }
        });

        flexibilityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strengthBtn.setSelected(false);
                plyoBtn.setSelected(false);
                cardioBtn.setSelected(false);
                flexibilityBtn.setSelected(true);
                powerliftingBtn.setSelected(false);
                weightliftingBtn.setSelected(false);
                fitnessGoal = "flexibility";
            }
        });

        powerliftingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strengthBtn.setSelected(false);
                plyoBtn.setSelected(false);
                cardioBtn.setSelected(false);
                flexibilityBtn.setSelected(false);
                powerliftingBtn.setSelected(true);
                weightliftingBtn.setSelected(false);
                fitnessGoal = "powerlifting";
            }
        });

        weightliftingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strengthBtn.setSelected(false);
                plyoBtn.setSelected(false);
                cardioBtn.setSelected(false);
                flexibilityBtn.setSelected(false);
                powerliftingBtn.setSelected(false);
                weightliftingBtn.setSelected(true);
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
            startActivity(new Intent(GetUserPreferences.this, HomePage.class));
        }
    }
}