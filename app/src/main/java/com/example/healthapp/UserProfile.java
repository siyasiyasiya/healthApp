package com.example.healthapp;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.fonts.Font;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class UserProfile extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{
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
    String[] weekDaysAbbr = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};

    private static final String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    ArrayList<Integer> weights = new ArrayList<>();
    Spinner daySelector;
    TextView todayMuscle;

    GraphView weightLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        TextView nameLbl = findViewById(R.id.nameLbl);
        TextView genderView = findViewById(R.id.genderView);
        TextView birthdayView = findViewById(R.id.birthdayView);
        TextView heightView = findViewById(R.id.heightView);
        TextView weightView = findViewById(R.id.weightView);
        todayMuscle = findViewById(R.id.todayMuscle);

        TextView fitnessGoalLbl = findViewById(R.id.fitnessGoalLbl);

        daySelector = (Spinner)findViewById(R.id.daySpinners);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(UserProfile.this,
                android.R.layout.simple_spinner_item, days);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySelector.setAdapter(adapter);
        daySelector.setOnItemSelectedListener(this);

        weightLog = findViewById(R.id.weightLog);


        java.util.Date ogDate = new java.util.Date();
        String date = ogDate.toString().substring(0, 10);
        String day = "";
        for(int i = 0; i < weekDaysAbbr.length; i++){
            if(date.contains(weekDaysAbbr[i])){
                day = weekDaysAbbr[i];
            }
        }

        if(day.equals("Mon")){
            daySelector.setSelection(0);
        }else if(day.equals("Tue")) {
            daySelector.setSelection(1);
        }else if(day.equals("Wed")) {
            daySelector.setSelection(2);
        }else if(day.equals("Thu")) {
            daySelector.setSelection(3);
        }else if(day.equals("Fri")) {
            daySelector.setSelection(4);
        }else if(day.equals("Sat")) {
            daySelector.setSelection(5);
        }else{
            daySelector.setSelection(6);
        }

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

        nameLbl.setText("Hi " + name +"!");
        genderView.setText(gender);
        birthdayView.setText(month +"/"+day+"/"+year);
        heightView.setText(feet + " ft " + inches+" in");
        weightView.setText(weight+ " lbs");
        fitnessGoalLbl.setText(fitnessGoal);

        loadDataWeight();

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                // on below line we are adding
                // each point on our x and y axis.
                new DataPoint(1, weights.get(0)),
                new DataPoint(2, weights.get(1)),
                new DataPoint(3, weights.get(2)),
                new DataPoint(4, weights.get(3)),
                new DataPoint(5, weights.get(4)),
                new DataPoint(6, weights.get(5)),
                new DataPoint(7, weights.get(6)),
                new DataPoint(8, weights.get(7)),
                new DataPoint(9, weights.get(8)),
                new DataPoint(10, weights.get(9)),
                new DataPoint(11, weights.get(10)),
                new DataPoint(12, weights.get(11)),
        });

        weightLog.setTitle("12 Month Weight Log");
        weightLog.setTitleTextSize(25);
        series.setColor(Color.parseColor("#FFFF0057"));
        weightLog.addSeries(series);
        weightLog.getViewport().setYAxisBoundsManual(false);
        weightLog.getViewport().setXAxisBoundsManual(true);
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

    public void loadDataWeight(){
        FileInputStream fis = null;
        try{
            fis = openFileInput("userDataWeight.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
//            StringBuilder sb = new StringBuilder();
//            String text;
            for (int i = 0; i < 12; i++) {
                br.readLine();
                weights.add(Integer.valueOf(br.readLine()));
            }
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                todayMuscle.setText(displayDailyGoal(0));
                break;
            case 1:
                todayMuscle.setText(displayDailyGoal(1));
                break;
            case 2:
                todayMuscle.setText(displayDailyGoal(2));
                break;
            case 3:
                todayMuscle.setText(displayDailyGoal(3));
                break;
            case 4:
                todayMuscle.setText(displayDailyGoal(4));
                break;
            case 5:
                todayMuscle.setText(displayDailyGoal(5));
                break;
            case 6:
                todayMuscle.setText(displayDailyGoal(6));
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}