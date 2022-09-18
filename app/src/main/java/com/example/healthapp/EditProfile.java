package com.example.healthapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Objects;

public class EditProfile extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    String name = "";
    String gender = "";
    int month = 0;
    int day = 0;
    int year=0;
    int feet=0;
    int inches =0;
    int weight = 0;
    String fitnessGoal = "";
    ArrayList<ArrayList<String>> weekDays = new ArrayList<>();

    private static final String[] workoutTypes = {"cardio", "plyometrics", "strength", "powerlifting", "flexibility", "weightlifting"};

    Spinner goalSelection;

    EditText nameTxt,monthTxt,dayTxt,yearTtx,feetTxt,inchTxt,weightTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        for (int i = 0; i < 7; i++) {
            weekDays.add(new ArrayList<>());
        }

        weekDays.get(0).add("Monday");
        weekDays.get(1).add("Tuesday");
        weekDays.get(2).add("Wednesday");
        weekDays.get(3).add("Thursday");
        weekDays.get(4).add("Friday");
        weekDays.get(5).add("Saturday");
        weekDays.get(6).add("Sunday");

        goalSelection = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditProfile.this,
                android.R.layout.simple_spinner_item, workoutTypes);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        goalSelection.setAdapter(adapter);
        goalSelection.setOnItemSelectedListener(this);

        loadChanges();

        View changeFem = findViewById(R.id.changeFem);
        View changeMale = findViewById(R.id.changeMale);
        View changeOther = findViewById(R.id.changeOther);

         nameTxt = findViewById(R.id.changeName);
         monthTxt = findViewById(R.id.changeMonth);
        dayTxt = findViewById(R.id.changeDay);
        yearTtx = findViewById(R.id.changeYear);
        feetTxt = findViewById(R.id.changeFeet);
        inchTxt = findViewById(R.id.changeInches);
        weightTxt = findViewById(R.id.changeWeight);

        nameTxt.setText(name);
        monthTxt.setText(String.valueOf(month));
        dayTxt.setText(String.valueOf(day));
        yearTtx.setText(String.valueOf(year));
        feetTxt.setText(String.valueOf(feet));
        inchTxt.setText(String.valueOf(inches));
        weightTxt.setText(String.valueOf(weight));

        if(Objects.equals(fitnessGoal, "cardio"))
            goalSelection.setSelection(0);
        if(Objects.equals(fitnessGoal, "plyometrics"))
            goalSelection.setSelection(1);
        if(Objects.equals(fitnessGoal, "strength"))
            goalSelection.setSelection(2);
        if(Objects.equals(fitnessGoal, "powerlifting"))
            goalSelection.setSelection(3);
        if(Objects.equals(fitnessGoal, "flexibility"))
            goalSelection.setSelection(4);
        if(Objects.equals(fitnessGoal, "weightlifting"))
            goalSelection.setSelection(5);


        if(gender.equals("female"))
            changeFem.setSelected(true);
        if(gender.equals("male"))
            changeMale.setSelected(true);
        if(gender.equals("other"))
            changeOther.setSelected(true);

        changeFem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "female";
                changeFem.setSelected(true);
                changeMale.setSelected(false);
                changeOther.setSelected(false);
            }
        });
        changeMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "male";
                changeFem.setSelected(false);
                changeMale.setSelected(true);
                changeOther.setSelected(false);
            }
        });
        changeOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "other";
                changeFem.setSelected(false);
                changeMale.setSelected(false);
                changeOther.setSelected(true);
            }
        });
    }

    public void loadChanges(){
        FileInputStream fis = null;
        try{
            fis = openFileInput("userData.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
//            StringBuilder sb = new StringBuilder();
//            String text;
            name = br.readLine();
            gender = br.readLine();
            month = Integer.parseInt(br.readLine());
            day = Integer.parseInt(br.readLine());
            year = Integer.parseInt(br.readLine());
            feet = Integer.parseInt(br.readLine());
            inches = Integer.parseInt(br.readLine());
            weight = Integer.parseInt(br.readLine());
            fitnessGoal = br.readLine();
            br.readLine();
            checkDayWorkout(0, "Tuesday", br);
            checkDayWorkout(1, "Wednesday", br);
            checkDayWorkout(2, "Thursday", br);
            checkDayWorkout(3, "Friday", br);
            checkDayWorkout(4, "Saturday", br);
            checkDayWorkout(5, "Sunday", br);
            checkDayWorkout(6, "end", br);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fis!=null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //    get each day's workout from txt file
    public void checkDayWorkout(int indexAdd, String weekDay, BufferedReader br){
        boolean day = true;
        while(day){
            String txt = null;
            try {
                txt = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
//            Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
            if (txt.equals(weekDay)){
                day = false;
            }else {
                weekDays.get(indexAdd).add(txt);
            }
        }
    }

    public void saveChanges(View v){
        TextView errors = findViewById(R.id.errors);

//         validations
        name = nameTxt.getText().toString();
        if (monthTxt.length()>0)
            month = Integer.parseInt(monthTxt.getText().toString());
        else
            errors.setText("Please enter a date");
        if (dayTxt.length()>0)
            day = Integer.parseInt(dayTxt.getText().toString());
        else
            errors.setText("Please enter a date");
        if (yearTtx.length()>0)
            year = Integer.parseInt(yearTtx.getText().toString());
        else
            errors.setText("Please enter a date");
        if (feetTxt.length()>0)
            feet = Integer.parseInt(feetTxt.getText().toString());
        else
            errors.setText("Please enter a height");
        if (inchTxt.length()>0)
            inches = Integer.parseInt(inchTxt.getText().toString());
        else
            errors.setText("Please enter a height");
        if (weightTxt.length()>0)
            weight = Integer.parseInt(weightTxt.getText().toString());
        else
            errors.setText("Please enter a weight");

        boolean works = true;

        if (name.equals("")){
            works = false;
            errors.setText("Please enter a name");
        }

        if(gender.equals("")) {
            works = false;
            errors.setText("Please select a gender");
        }

        if (month < 1 || month>12){
            works = false;
            errors.setText("Please enter a valid date");
        }else{
            if (day < 1 || day>31){
                works = false;
                errors.setText("Please enter a valid date");
            }else{
                if (year < 1900 || year>2022){
                    works = false;
                    errors.setText("Please enter a valid date");
                }else{
                    if (year>2009){
                        works = false;
                        errors.setText("YOU ARE TOO YOUNG FOR AN OMNIFIT ACCOUNT");
                    }else{
                    }
                }
            }
        }

        if(feet < 3 || feet > 8){
            works = false;
            errors.setText("Please enter a valid height");
        }else{
            if(inches < 0 || inches > 12){
                works = false;
                errors.setText("Please enter a valid height");
            }
        }

        if(weight < 50 || weight > 500) {
            works = false;
            errors.setText("Please enter a valid weight");
        }

        if(works){
            save(v);
        }
    }

    public void save (View v){
        File path = getApplicationContext().getFilesDir();
        try {
            FileOutputStream writer = new FileOutputStream(new File(path, "userData.txt"));
            OutputStreamWriter osr = new OutputStreamWriter(writer);
            BufferedWriter br = new BufferedWriter(osr);
            br.append(name);
            br.newLine();
            br.append(gender);
            br.newLine();
            br.append(String.valueOf(month));
            br.newLine();
            br.append(String.valueOf(day));
            br.newLine();
            br.append(String.valueOf(year));
            br.newLine();
            br.append(String.valueOf(feet));
            br.newLine();
            br.append(String.valueOf(inches));
            br.newLine();
            br.append(String.valueOf(weight));
            br.newLine();
            br.append(fitnessGoal);
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < weekDays.get(i).size(); j++) {
                    br.newLine();
                    br.append(weekDays.get(i).get(j));
                }
            }
            br.newLine();
            br.append("end");
            br.close();
            startActivity(new Intent(EditProfile.this, UserProfile.class));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                fitnessGoal = "cardio";
                break;
            case 1:
                fitnessGoal = "plyometrics";
                break;
            case 2:
                fitnessGoal = "strength";
                break;
            case 3:
                fitnessGoal = "powerlifting";
                break;
            case 4:
                fitnessGoal = "flexibility";
                break;
            case 5:
                fitnessGoal = "weightlifting";
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}