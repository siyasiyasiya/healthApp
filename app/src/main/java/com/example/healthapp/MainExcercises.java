package com.example.healthapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainExcercises extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String[] workoutTypes = {"All", "Cardio", "Plyometrics", "Strength", "Powerlifting", "Flexibility", "Weightlifting"};
    private static final String[] muscleGroups = {"All", "Glutes", "Quads", "Hamstrings", "Calves", "Abductors", "Adductors","Biceps", "Forearms", "Triceps","Traps", "Middle Back", "Lower Back", "Lats", "Chest", "Neck","Abs"};
    private static final String[] levels = {"All", "Beginner", "Intermediate", "Expert"};

    String[] exercises = {"Squats", "Lunges", "Leg Presses", "Split Squat", "RDL", "Single Leg Squat", "Hip Extensions", "Bicep Curls", "Overhead Presses", "Hamstring Curls"};

    Spinner muscleSelect, typeSelect, levelSelect;

    String muscle = "All", type = "All", level = "All";

    public static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_excercises);

        for (int i = 0; i < 10; i++) {
            fragmentManager = getSupportFragmentManager();
            if(findViewById(R.id.exerciseTable)!=null){
                if(savedInstanceState!=null){

                    return;
                }
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ExerciseDisplayFragment exerciseDisplayFragment = new ExerciseDisplayFragment();
                Bundle b = new Bundle();
                b.putString("exercise", exercises[i]);
                exerciseDisplayFragment.setArguments(b);
                fragmentTransaction.add(R.id.exerciseTable, exerciseDisplayFragment).commit();
            }
        }

        muscleSelect = (Spinner)findViewById(R.id.muscleSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainExcercises.this,
                android.R.layout.simple_spinner_item, muscleGroups);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        muscleSelect.setAdapter(adapter);
        muscleSelect.setOnItemSelectedListener(this);

        typeSelect = (Spinner)findViewById(R.id.typeSpinner);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(MainExcercises.this,
                android.R.layout.simple_spinner_item, workoutTypes);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSelect.setAdapter(adapter2);
        typeSelect.setOnItemSelectedListener(this);

        levelSelect = (Spinner)findViewById(R.id.difficultySpinner);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(MainExcercises.this,
                android.R.layout.simple_spinner_item, levels);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        levelSelect.setAdapter(adapter3);
        levelSelect.setOnItemSelectedListener(this);
    }

    public void navHome(View v){
        startActivity(new Intent(MainExcercises.this, HomePage.class));
    }

    public void navProfile(View v){
        startActivity(new Intent(MainExcercises.this, UserProfile.class));
    }

//get spinner selection item
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent==muscleSelect) {
            switch (position) {
                case 0:
                    muscle = "all";
                    break;
                case 1:
                    muscle = "glutes";
                    break;
                case 2:
                    muscle = "quads";
                    break;
                case 3:
                    muscle = "hamstrings";
                    break;
                case 4:
                    muscle = "calves";
                    break;
                case 6:
                    muscle = "abductors";
                    break;
                case 7:
                    muscle = "adductors";
                    break;
                case 8:
                    muscle = "biceps";
                    break;
                case 9:
                    muscle = "forearms";
                    break;
                case 10:
                    muscle = "triceps";
                    break;
                case 11:
                    muscle = "traps";
                    break;
                case 12:
                    muscle = "middle back";
                    break;
                case 13:
                    muscle = "lower back";
                    break;
                case 14:
                    muscle = "lats";
                    break;
                case 15:
                    muscle = "chest";
                    break;
                case 16:
                    muscle = "neck";
                    break;
                case 17:
                    muscle = "abdominals";
                    break;
            }
        }

        if(parent == typeSelect){
            switch (position) {
                case 0:
                    type = "all";
                    break;
                case 1:
                    type = "cardio";
                    break;
                case 2:
                    type = "plyometrics";
                    break;
                case 3:
                    type = "strength";
                    break;
                case 4:
                    type = "powerlifting";
                    break;
                case 5:
                    type = "flexibility";
                    break;
                case 6:
                    type = "weightlifting";
                    break;

            }
        }

        if(parent==levelSelect){
            switch (position) {
                case 0:
                    level = "all";
                    break;
                case 1:
                    level = "beginner";
                    break;
                case 2:
                    level = "intermediate";
                    break;
                case 3:
                    level = "expert";
                    break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}