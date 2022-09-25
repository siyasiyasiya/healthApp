package com.example.healthapp;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class UserProfile extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        TextView nameLbl = findViewById(R.id.nameLbl);
        TextView genderView = findViewById(R.id.genderView);
        TextView birthdayView = findViewById(R.id.birthdayView);
        TextView heightView = findViewById(R.id.heightView);
        TextView weightView = findViewById(R.id.weightView);

        TextView fitnessGoalLbl = findViewById(R.id.fitnessGoalLbl);

        TextView mondayGoal = findViewById(R.id.mondayGoal);
        TextView tuesdayGoal = findViewById(R.id.tuesdayGoal);
        TextView wednesdayGoal = findViewById(R.id.wednesdayGoal);
        TextView thursdayGoal = findViewById(R.id.thurdayGoal);
        TextView fridayGoal = findViewById(R.id.fridayGoal);
        TextView saturdayGoal = findViewById(R.id.saturdayGoal);
        TextView sundayGoal = findViewById(R.id.sundayGoal);

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

        loadData();

        mondayGoal.setText(displayDailyGoal(0));
        tuesdayGoal.setText(displayDailyGoal(1));
        wednesdayGoal.setText(displayDailyGoal(2));
        thursdayGoal.setText(displayDailyGoal(3));
        fridayGoal.setText(displayDailyGoal(4));
        saturdayGoal.setText(displayDailyGoal(5));
        sundayGoal.setText(displayDailyGoal(6));

        nameLbl.setText("Hi " + name +"!");
        genderView.setText(gender);
        birthdayView.setText(month +"/"+day+"/"+year);
        heightView.setText(feet + " ft " + inches+" in");
        weightView.setText(weight+ " lbs");
        fitnessGoalLbl.setText(fitnessGoal);

    }

    public String displayDailyGoal(int index){
        String txt = "";
        for (int i = 1; i < weekDays.get(index).size()-1; i++) {
            txt+=weekDays.get(index).get(i)+" & ";
        }
        if(weekDays.get(index).size()==1)
            txt="no goal set";
        else
            txt+=weekDays.get(index).get(weekDays.get(index).size()-1);
        return txt;
    }

    public void navHome(View v){
        startActivity(new Intent(UserProfile.this, HomePage.class));
    }

    public void navExercises(View v){
        startActivity(new Intent(UserProfile.this, MainExcercises.class));
    }
    public void navLog(View v){
        startActivity(new Intent(UserProfile.this, CalendarLog.class));
    }
    public void navLogWorkout(View v){
        startActivity(new Intent(UserProfile.this, LogWorkout.class));
    }

    public void loadData(){
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
            if (txt==null || txt.equals(weekDay)){
                day = false;
            }else {
                weekDays.get(indexAdd).add(txt);
            }
        }
    }

    public void editProfile(View v){
        startActivity(new Intent(UserProfile.this, EditProfile.class));
    }

    public void deleteProfile(View v){
        deleteFile("userData.txt");
        startActivity(new Intent(UserProfile.this, NewUserLanding.class));
    }

    @Override
    public boolean deleteFile(String name) {
        return super.deleteFile(name);
    }
}