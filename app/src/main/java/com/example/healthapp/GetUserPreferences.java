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
    String[] weekDays = new String[7];

    File myExternalFile;

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
//            startActivity(new Intent(GetUserPreferences.this, HomePage.class));
        }
    }

    private class LongClickListener implements View.OnLongClickListener {
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

                    boolean drop = true;
                    if (viewDroppedAt.getChildAt(0) != null)
                        drop = false;
                    if (viewDroppedAt == bottomLayer)
                        drop = true;

                    if (drop){
                        droppedFrom.removeView(textDropped);
                    viewDroppedAt.addView(textDropped);

                    if (viewDroppedAt == topLayerMon) {
                        weekDays[0] = String.valueOf(textDropped.getText());
                    }
                    if (viewDroppedAt == topLayerTues) {
                        weekDays[1] = String.valueOf(textDropped.getText());
                    }
                    if (viewDroppedAt == topLayerWed) {
                        weekDays[2] = String.valueOf(textDropped.getText());
                    }
                    if (viewDroppedAt == topLayerThurs) {
                        weekDays[3] = String.valueOf(textDropped.getText());
                    }
                    if (viewDroppedAt == topLayerFri) {
                        weekDays[4] = String.valueOf(textDropped.getText());
                    }
                    if (viewDroppedAt == topLayerSat) {
                        weekDays[5] = String.valueOf(textDropped.getText());
                    }
                    if (viewDroppedAt == topLayerSun) {
                        weekDays[6] = String.valueOf(textDropped.getText());
                    }

                    if (droppedFrom == topLayerMon) {
                        weekDays[0] = "";
                    }
                    if (droppedFrom == topLayerTues) {
                        weekDays[1] = "";
                    }
                    if (droppedFrom == topLayerWed) {
                        weekDays[2] = "";
                    }
                    if (droppedFrom == topLayerThurs) {
                        weekDays[3] = "";
                    }
                    if (droppedFrom == topLayerFri) {
                        weekDays[4] = "";
                    }
                    if (droppedFrom == topLayerSat) {
                        weekDays[5] = "";
                    }
                    if (droppedFrom == topLayerSun) {
                        weekDays[6] = "";
                    }
                }
            }

            return true;
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
//        Mon choice
//        tues choice
//        wed choice
//        thurs choice
//        fri choice
//        sat choice
//        sun choice
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
                br.newLine();
                br.append(weekDays[i]);
            }

            br.close();
//            Toast.makeText(this, path.toString(), Toast.LENGTH_SHORT).show();
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

            Toast.makeText(this, sb.toString(), Toast.LENGTH_SHORT).show();
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