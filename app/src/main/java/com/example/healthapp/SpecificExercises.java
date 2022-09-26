package com.example.healthapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class SpecificExercises extends AppCompatActivity {
    String name = "";
    String muscle = "";
    String type = "";
    String equipment = "";
    String difficulty = "";
    String instructions = "";

    String muscleInp,typeInp, levelInp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_exercises);

        TextView exerciseName = findViewById(R.id.exerciseNameTxt);
        TextView exerciseType = findViewById(R.id.exerciseTypeTxt);
        TextView exerciseMuscle = findViewById(R.id.targetMuscleTxt2);
        TextView exerciseEquipment = findViewById(R.id.equipmentTxt);
        TextView exerciseInstructions = findViewById(R.id.instructionsTxt);

        ImageView easyDumbell = findViewById(R.id.easyDumbell2);
        ImageView mediumDumbell = findViewById(R.id.mediumDumbell2);
        ImageView hardDumbell = findViewById(R.id.hardDumbell2);

//        get data from Intent extras
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name = extras.getString("name");
            muscle = extras.getString("muscle");
            equipment = extras.getString("equipment");
            type = extras.getString("type");
            difficulty = extras.getString("difficulty");
            instructions = extras.getString("instructions");

            muscleInp = extras.getString("muscle2");
            typeInp = extras.getString("type2");
            levelInp = extras.getString("level2");
        }

        exerciseName.setText(name);

        //        format and display exercise data into views
        String typeDisplay = type;
        String muscleDisplay = muscle;
        String equipDisplay = equipment;
        if(type.contains("_")){
            typeDisplay = type.substring(0, type.indexOf("_"))+" "+type.substring(type.indexOf("_")+1, type.indexOf("_")+2).toUpperCase()+type.substring(type.indexOf("_")+2);
        }
        if(muscle.contains("_")){
            muscleDisplay = muscle.substring(0, muscle.indexOf("_"))+" "+muscle.substring(muscle.indexOf("_")+1, muscle.indexOf("_")+2).toUpperCase()+muscle.substring(muscle.indexOf("_")+2);
        }
        if(equipment.contains("_")){
            equipDisplay = equipment.substring(0, equipment.indexOf("_"))+" "+equipment.substring(equipment.indexOf("_")+1, equipment.indexOf("_")+2).toUpperCase()+equipment.substring(equipment.indexOf("_")+2);
        }

        exerciseMuscle.setText("Target Muscle: "+muscleDisplay.substring(0,1).toUpperCase() + muscleDisplay.substring(1));
        exerciseType.setText("Exercise Type: "+typeDisplay.substring(0,1).toUpperCase() + typeDisplay.substring(1));
        exerciseEquipment.setText("Equipment: "+equipDisplay.substring(0,1).toUpperCase() + equipDisplay.substring(1));
        exerciseInstructions.setText(instructions);

        if(difficulty.equals("beginner")){
            easyDumbell.setAlpha(1.0F);
            mediumDumbell.setAlpha(0.25F);
            hardDumbell.setAlpha(0.25F);
        }
        if(difficulty.equals("intermediate")){
            easyDumbell.setAlpha(1.0F);
            mediumDumbell.setAlpha(1.0F);
            hardDumbell.setAlpha(0.25F);
        }
        if(difficulty.equals("expert")){
            easyDumbell.setAlpha(1.0F);
            mediumDumbell.setAlpha(1.0F);
            hardDumbell.setAlpha(1.0F);
        }
    }

    public void goBack(View v){
        Intent intent = new Intent(SpecificExercises.this, MainExcercises.class);
        intent.putExtra("muscle", muscleInp);
        intent.putExtra("type", typeInp);
        intent.putExtra("level", levelInp);
        startActivity(intent);
    }

    public void navHome(View v){
        startActivity(new Intent(SpecificExercises.this, HomePage.class));
    }

    public void navProfile(View v){
        startActivity(new Intent(SpecificExercises.this, UserProfile.class));
    }

    public void navLog(View v){
        startActivity(new Intent(SpecificExercises.this, CalendarLog.class));
    }

    public void navLogWorkout(View v){
        startActivity(new Intent(SpecificExercises.this, LogWorkout.class));
    }
}