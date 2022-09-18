package com.example.healthapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class GetUserPreferences extends AppCompatActivity{
    private String fitnessGoal = "";
    private TextView goalErr;
    LinearLayout topLayerMon,topLayerTues,topLayerWed,topLayerThurs,topLayerFri,topLayerSat,topLayerSun, bottomLayer;
    String name ="";
    String gender="";
    int month=0;
    int day=0;
    int year=0;
    int feet=0;
    int inches =0;
    int weight = 0;
    ArrayList<ArrayList<String>> weekDays = new ArrayList<>();

    TextView workoutPrefsErr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_user_preferences);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name = extras.getString("name");
            gender = extras.getString("gender");
            month = extras.getInt("month");
            day = extras.getInt("day");
            year = extras.getInt("year");
            feet = extras.getInt("feet");
            inches = extras.getInt("inches");
            weight = extras.getInt("weight");
        }

        View strengthBtn = findViewById(R.id.strengthBtn);
        View plyoBtn = findViewById(R.id.plyoBtn);
        View cardioBtn = findViewById(R.id.cardioBtn);
        View flexibilityBtn = findViewById(R.id.flexibilityBtn);
        View powerliftingBtn = findViewById(R.id.powerliftiingBtn);
        View weightliftingBtn = findViewById(R.id.weightliftingBtn);
        goalErr = findViewById(R.id.goalErr);

        workoutPrefsErr = findViewById(R.id.workoutPrefsErr);

        TextView dragLeg = (TextView) findViewById(R.id.dragLegs);
        dragLeg.setOnLongClickListener(new LongClickListener());

        TextView dragChoice = (TextView) findViewById(R.id.dragChoice);
        dragChoice.setOnLongClickListener(new LongClickListener());

        TextView dragArms = (TextView) findViewById(R.id.dragArms);
        dragArms.setOnLongClickListener(new LongClickListener());

        TextView dragBack = (TextView) findViewById(R.id.dragBack);
        dragBack.setOnLongClickListener(new LongClickListener());

        TextView dragChest = (TextView) findViewById(R.id.dragChest);
        dragChest.setOnLongClickListener(new LongClickListener());

        TextView dragRest = (TextView) findViewById(R.id.dragRest);
        dragRest.setOnLongClickListener(new LongClickListener());

        TextView dragSho = (TextView) findViewById(R.id.dragSho);
        dragSho.setOnLongClickListener(new LongClickListener());

        TextView dragAbbs = (TextView) findViewById(R.id.dragAbbs);
        dragAbbs.setOnLongClickListener(new LongClickListener());

         topLayerMon = (LinearLayout) findViewById(R.id.mon);
         topLayerTues = (LinearLayout) findViewById(R.id.tues);
         topLayerWed = (LinearLayout) findViewById(R.id.wed);
         topLayerThurs = (LinearLayout) findViewById(R.id.thurs);
         topLayerFri = (LinearLayout) findViewById(R.id.fri);
         topLayerSat = (LinearLayout) findViewById(R.id.sat);
         topLayerSun = (LinearLayout) findViewById(R.id.sun);

         bottomLayer = (LinearLayout) findViewById(R.id.bottomLinLayout);

        topLayerMon.setOnDragListener(new DragListener());
        topLayerTues.setOnDragListener(new DragListener());
        topLayerWed.setOnDragListener(new DragListener());
        topLayerThurs.setOnDragListener(new DragListener());
        topLayerFri.setOnDragListener(new DragListener());
        topLayerSat.setOnDragListener(new DragListener());
        topLayerSun.setOnDragListener(new DragListener());

        bottomLayer.setOnDragListener(new DragListener());

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
            saveData(view);
            loadData(view);
            Intent intent = new Intent(GetUserPreferences.this, HomePage.class);
            startActivity(intent);
        }
    }

    public static class LongClickListener implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View v) {
            ClipData.Item item = new ClipData.Item((String)v.getTag());
            String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
            ClipData data = new ClipData((String) v.getTag(), mimeTypes, item);
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDrag(data, shadowBuilder, v, 0);
            return true;
        }
    }

    private class DragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch(event.getAction()) {
                case DragEvent.ACTION_DROP:
                    TextView textDropped = (TextView) event.getLocalState();
                    textDropped.setVisibility(View.VISIBLE);
                    ViewGroup viewDroppedAt = (ViewGroup) v;
                    ViewGroup droppedFrom = ((ViewGroup) textDropped.getParent());

                    if (viewDroppedAt == topLayerMon) {
                        addWorkout(v, textDropped, droppedFrom, viewDroppedAt, 0);
                    }
                    if (viewDroppedAt == topLayerTues) {
                        addWorkout(v, textDropped, droppedFrom, viewDroppedAt, 1);
                    }
                    if (viewDroppedAt == topLayerWed) {
                        addWorkout(v, textDropped, droppedFrom, viewDroppedAt, 2);
                    }
                    if (viewDroppedAt == topLayerThurs) {
                        addWorkout(v, textDropped, droppedFrom, viewDroppedAt, 3);
                    }
                    if (viewDroppedAt == topLayerFri) {
                        addWorkout(v, textDropped, droppedFrom, viewDroppedAt, 4);
                    }
                    if (viewDroppedAt == topLayerSat) {
                        addWorkout(v, textDropped, droppedFrom, viewDroppedAt, 5);
                    }
                    if (viewDroppedAt == topLayerSun) {
                        addWorkout(v, textDropped, droppedFrom, viewDroppedAt, 6);
                    }

                    if(viewDroppedAt == bottomLayer){
                        droppedFrom.removeView(textDropped);
                        viewDroppedAt.addView(textDropped);
                        workoutPrefsErr.setText("");
                    }

                    if (droppedFrom == topLayerMon) {
                        weekDays.get(0).remove(String.valueOf(textDropped.getText()));
                    }
                    if (droppedFrom == topLayerTues) {
                        weekDays.get(1).remove(String.valueOf(textDropped.getText()));
                    }
                    if (droppedFrom == topLayerWed) {
                        weekDays.get(2).remove(String.valueOf(textDropped.getText()));
                    }
                    if (droppedFrom == topLayerThurs) {
                        weekDays.get(3).remove(String.valueOf(textDropped.getText()));
                    }
                    if (droppedFrom == topLayerFri) {
                        weekDays.get(4).remove(String.valueOf(textDropped.getText()));
                    }
                    if (droppedFrom == topLayerSat) {
                        weekDays.get(5).remove(String.valueOf(textDropped.getText()));
                    }
                    if (droppedFrom == topLayerSun) {
                        weekDays.get(6).remove(String.valueOf(textDropped.getText()));
                    }
                }
            return true;
        }
    }

//    verifications for user workout choice
//    also allows user to take out a workout choice from calendar
//    validations such as you can;t work legs and have a rest day the same day
//    can't work more than two muscle groups per day
    private void addWorkout(View view, TextView textDropped, ViewGroup droppedFrom, ViewGroup viewDroppedAt, int index){
        boolean works = true;
        for (int i = 0; i < weekDays.get(index).size(); i++) {
            if(weekDays.get(index).get(i).equals("rest") || weekDays.get(index).get(i).equals("choice"))
                works = false;
        }
        if(textDropped.getText().equals("rest")||textDropped.getText().equals("choice")){
            if (weekDays.get(index).size()>1)
                works = false;
        }
        if(works){
            if (weekDays.get(index).size()>2){
                workoutPrefsErr.setText("You may not choose to work more than two muscle groups per day.");
            }else {
                weekDays.get(index).add(String.valueOf(textDropped.getText()));
                droppedFrom.removeView(textDropped);
                viewDroppedAt.addView(textDropped);
                workoutPrefsErr.setText("");
            }
        }else{
            workoutPrefsErr.setText("You may not combine a muscle group focus day with a rest or choice day");
        }
    }

//    storing data in a text file
    public void saveData(View v) {
//        name
//        gender
//        birth month
//        birth day
//        birth year
//        feet (height)
//        inches (height)
//        weight
//        fitness goal
//        Mon choices
//        tues choices
//        wed choices
//        thurs choices
//        fri choices
//        sat choices
//        sun choices
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    how to load data example
    public void loadData(View v){
        FileInputStream fis = null;
        try{
            fis = openFileInput("userData.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while((text = br.readLine()) != null){
                sb.append(text).append("\n");
            }

//            Toast.makeText(this, sb.toString(), Toast.LENGTH_SHORT).show();
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
}