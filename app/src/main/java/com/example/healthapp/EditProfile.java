package com.example.healthapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

    TextView workoutPrefsErr;
    LinearLayout topLayerMon,topLayerTues,topLayerWed,topLayerThurs,topLayerFri,topLayerSat,topLayerSun, bottomLayer;

    TextView dragLeg,dragChoice,dragArms,dragBack,dragChest,dragRest,dragSho,dragAbbs;

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

        workoutPrefsErr = findViewById(R.id.workoutPrefsErr2);

         dragLeg = (TextView) findViewById(R.id.dragLegs2);
        dragLeg.setOnLongClickListener(new GetUserPreferences.LongClickListener());

         dragChoice = (TextView) findViewById(R.id.dragChoice2);
        dragChoice.setOnLongClickListener(new GetUserPreferences.LongClickListener());

         dragArms = (TextView) findViewById(R.id.dragArms2);
        dragArms.setOnLongClickListener(new GetUserPreferences.LongClickListener());

         dragBack = (TextView) findViewById(R.id.dragBack2);
        dragBack.setOnLongClickListener(new GetUserPreferences.LongClickListener());

         dragChest = (TextView) findViewById(R.id.dragChest2);
        dragChest.setOnLongClickListener(new GetUserPreferences.LongClickListener());

         dragRest = (TextView) findViewById(R.id.dragRest2);
        dragRest.setOnLongClickListener(new GetUserPreferences.LongClickListener());

         dragSho = (TextView) findViewById(R.id.dragSho2);
        dragSho.setOnLongClickListener(new GetUserPreferences.LongClickListener());

         dragAbbs = (TextView) findViewById(R.id.dragAbbs2);
        dragAbbs.setOnLongClickListener(new GetUserPreferences.LongClickListener());

        topLayerMon = (LinearLayout) findViewById(R.id.mon2);
        topLayerTues = (LinearLayout) findViewById(R.id.tues2);
        topLayerWed = (LinearLayout) findViewById(R.id.wed2);
        topLayerThurs = (LinearLayout) findViewById(R.id.thurs2);
        topLayerFri = (LinearLayout) findViewById(R.id.fri2);
        topLayerSat = (LinearLayout) findViewById(R.id.sat2);
        topLayerSun = (LinearLayout) findViewById(R.id.sun2);

        bottomLayer = (LinearLayout) findViewById(R.id.changeBottomLinLayout);

        topLayerMon.setOnDragListener(new DragListener());
        topLayerTues.setOnDragListener(new DragListener());
        topLayerWed.setOnDragListener(new DragListener());
        topLayerThurs.setOnDragListener(new DragListener());
        topLayerFri.setOnDragListener(new DragListener());
        topLayerSat.setOnDragListener(new DragListener());
        topLayerSun.setOnDragListener(new DragListener());

        bottomLayer.setOnDragListener(new DragListener());

        for (int i = 1; i < weekDays.get(0).size(); i++) {
            setUpCal(0, i, topLayerMon);
        }
        for (int i = 1; i < weekDays.get(1).size(); i++) {
            setUpCal(1, i, topLayerTues);
        }
        for (int i = 1; i < weekDays.get(2).size(); i++) {
            setUpCal(2, i, topLayerWed);
        }
        for (int i = 1; i < weekDays.get(3).size(); i++) {
            setUpCal(3, i, topLayerThurs);
        }
        for (int i = 1; i < weekDays.get(4).size(); i++) {
            setUpCal(4, i, topLayerFri);
        }
        for (int i = 1; i < weekDays.get(5).size(); i++) {
            setUpCal(5, i, topLayerSat);
        }
        for (int i = 1; i < weekDays.get(6).size(); i++) {
            setUpCal(6, i, topLayerSun);
        }

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

        if(Objects.equals(fitnessGoal, "weight loss"))
            goalSelection.setSelection(0);
        if(Objects.equals(fitnessGoal, "plyometrics"))
            goalSelection.setSelection(1);
        if(Objects.equals(fitnessGoal, "muscle gain"))
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

    public void setUpCal(int index, int i, LinearLayout draggedTo){
        if(weekDays.get(index).get(i).equals("legs"))
            addWorkout(dragLeg, bottomLayer, draggedTo, index, true);
        if(weekDays.get(index).get(i).equals("arms"))
            addWorkout(dragArms, bottomLayer, draggedTo, index, true);
        if(weekDays.get(index).get(i).equals("choice"))
            addWorkout(dragChoice, bottomLayer, draggedTo, index, true);
        if(weekDays.get(index).get(i).equals("rest"))
            addWorkout(dragRest, bottomLayer, draggedTo, index, true);
        if(weekDays.get(index).get(i).equals("back"))
            addWorkout(dragBack, bottomLayer, draggedTo, index, true);
        if(weekDays.get(index).get(i).equals("shoulder"))
            addWorkout(dragSho, bottomLayer, draggedTo, index, true);
        if(weekDays.get(index).get(i).equals("abs"))
            addWorkout(dragAbbs, bottomLayer, draggedTo, index, true);
        if(weekDays.get(index).get(i).equals("chest"))
            addWorkout(dragChest, bottomLayer, draggedTo, index, true);
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

                    if (viewDroppedAt == topLayerMon) {
                        addWorkout(textDropped, droppedFrom, viewDroppedAt, 0, false);
                    }
                    if (viewDroppedAt == topLayerTues) {
                        addWorkout(textDropped, droppedFrom, viewDroppedAt, 1, false);
                    }
                    if (viewDroppedAt == topLayerWed) {
                        addWorkout(textDropped, droppedFrom, viewDroppedAt, 2, false);
                    }
                    if (viewDroppedAt == topLayerThurs) {
                        addWorkout(textDropped, droppedFrom, viewDroppedAt, 3, false);
                    }
                    if (viewDroppedAt == topLayerFri) {
                        addWorkout(textDropped, droppedFrom, viewDroppedAt, 4, false);
                    }
                    if (viewDroppedAt == topLayerSat) {
                        addWorkout(textDropped, droppedFrom, viewDroppedAt, 5, false);
                    }
                    if (viewDroppedAt == topLayerSun) {
                        addWorkout(textDropped, droppedFrom, viewDroppedAt, 6, false);
                    }

                    if(viewDroppedAt == bottomLayer){
                        droppedFrom.removeView(textDropped);
                        viewDroppedAt.addView(textDropped);
                        workoutPrefsErr.setText("");
                    }
            }
            return true;
        }
    }

    //    verifications for user workout choice
//    also allows user to take out a workout choice from calendar
//    validations such as you can;t work legs and have a rest day the same day
//    can't work more than two muscle groups per day
    public void addWorkout(TextView textDropped, ViewGroup droppedFrom, ViewGroup viewDroppedAt, int index, boolean init){
        boolean works = true;
        if(!init) {
            for (int i = 0; i < weekDays.get(index).size(); i++) {
                if (weekDays.get(index).get(i).equals("rest") || weekDays.get(index).get(i).equals("choice"))
                    works = false;
            }
            if (textDropped.getText().equals("rest") || textDropped.getText().equals("choice")) {
                if (weekDays.get(index).size() > 1)
                    works = false;
            }
        }
        if(works){
            if (weekDays.get(index).size()>3 && init){
                workoutPrefsErr.setText("You may not choose to work more than two muscle groups per day.");
            }else if(weekDays.get(index).size()>2 && !init) {
                workoutPrefsErr.setText("You may not choose to work more than two muscle groups per day.");
            }else{
                if (!init)
                    weekDays.get(index).add(String.valueOf(textDropped.getText()));
                droppedFrom.removeView(textDropped);
                viewDroppedAt.addView(textDropped);
                workoutPrefsErr.setText("");
            }
        }else{
            workoutPrefsErr.setText("You may not combine a muscle group focus day with a rest or choice day");
        }
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
            if (txt==null || txt.equals(weekDay)){
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
                fitnessGoal = "weight loss";
                break;
            case 1:
                fitnessGoal = "plyometrics";
                break;
            case 2:
                fitnessGoal = "muscle gain";
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