package com.example.healthapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class HomePage extends AppCompatActivity {
    public String workoutDay;
    String name;
    String[] muscleGroups = {"Legs", "Arms", "Back", "Chest", "Shoulder", "Abs", "Rest", "Choice"};
    String[][] muscles = {{"Glutes", "Quadriceps", "Hamstrings", "Calves", "Adductors", "Abductors"}, {"Biceps", "Forearms", "Triceps"}, {"Traps", "Middle Back", "Lower Back", "Lats"}, {"Chest"}, {"Traps", "Neck"}, {"Abs"}};
    String[] weekDays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    String[] weekDaysAbbr = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
    String[] fitnessFactArr =  {"You use 200 muscles to take a single step",
                                "The pressure on your feet when you run is as much as 4 times your body weight",
                                "Eating protein before your workout increases gains in muscle mass",
                                "The average person walks about 70,000 miles in their lifetime",
                                "Working out increases your lifespan",
                                "It takes at least 12 weeks of regular exercise to get into shape",
                                "Switching up your workout will help you lose more weight",
                                "Being dehydrated impairs your exercise performance",
                                "People who don???t exercise regularly can lose 80% of their strength by age 65",
                                "Walking briskly burns nearly as many calories as jogging",
                                "Staying active reduces your risk of many cancers",
                                "You???re never too old to benefit from exercise",
                                "Regular exercise improves your mental health",
                                "You???re more productive when you???re active",
                                "It only takes 2.5 hours of moderate physical activity to see cardiovascular benefits",
                                "People who exercise regularly have higher vitamin D levels in their blood.",
                                "Regular exercise can prevent illness",
                                "For every pound of muscle gained, the body burns 50 extra calories every day",
                                "People burn an average of 50 calories every hour while they sleep",
                                "Scheduling rest days helps you meet your fitness goals",
                                "The endorphins released during exercise give you an energy boost",
                                "The best time to exercise is 2-3 hours after eating",
                                "Heavy workouts in the morning can compromise your immune system",
                                "Lifting boosts metabolism, especially your resting metabolic rate",
                                "Women???s muscles recover much faster than men???s after weightlifting",
                                "Weight training reduces your risk of injuries",
                                "Stretching allows you to recharge and refresh the blood flow throughout your body",
                                "Stretching can also improve your body posture"};
    ArrayList<String> todayMuscles = new ArrayList<>();
    ArrayList<String> todayMuscleGroupsButtons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //setting a new random fitness fact every time user logs in
        TextView fitnessLbl = findViewById(R.id.fitnessFunFact);
        fitnessLbl.setText(fitnessFactArr[randomNumber(0, fitnessFactArr.length-1)]);

        //sets the date
        java.util.Date ogDate = new java.util.Date();
        String date = ogDate.toString().substring(0, 10);
        TextView dateLbl = findViewById(R.id.todayDateLbl);
        String day = "";
        for(int i = 0; i < weekDays.length; i++){
            if(date.contains(weekDaysAbbr[i])){
                date = date.replace(weekDaysAbbr[i], weekDays[i]);
                day = weekDays[i];
            }
        }
        dateLbl.setText(date);

        //sets the workout day
        loadData(day);
        TextView todayWork = findViewById(R.id.todayGoalLbl);
        todayWork.setText(workoutDay);
        displayExerciseGroups();

        //sets the greeting label
        TextView greeting = findViewById(R.id.greetingLbl);
        String text = "<string name=\"greeting\">Hi <font color='#F9245B'>" + name +"</font>!</string>";
        greeting.setText(Html.fromHtml(text));


        findViewById(R.id.excerciseGroup1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, MainExcercises.class);
                intent.putExtra("muscle",todayMuscleGroupsButtons.get(0).toLowerCase(Locale.ROOT));
                intent.putExtra("type", "all");
                intent.putExtra("level", "all");
                startActivity(intent);
            }
        });
        findViewById(R.id.excerciseGroup2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, MainExcercises.class);
                intent.putExtra("muscle",todayMuscleGroupsButtons.get(1).toLowerCase(Locale.ROOT));
                intent.putExtra("type", "all");
                intent.putExtra("level", "all");
                startActivity(intent);
            }
        });
        findViewById(R.id.excerciseGroup3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, MainExcercises.class);
                intent.putExtra("muscle",todayMuscleGroupsButtons.get(2).toLowerCase(Locale.ROOT));
                intent.putExtra("type", "all");
                intent.putExtra("level", "all");
                startActivity(intent);
            }
        });
        findViewById(R.id.excerciseGroup4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, MainExcercises.class);
                intent.putExtra("muscle",todayMuscleGroupsButtons.get(3).toLowerCase(Locale.ROOT));
                intent.putExtra("type", "all");
                intent.putExtra("level", "all");
                startActivity(intent);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void displayExerciseGroups(){
        Button group1 = findViewById(R.id.excerciseGroup1);
        Button group2 = findViewById(R.id.excerciseGroup2);
        Button group3 = findViewById(R.id.excerciseGroup3);
        Button group4 = findViewById(R.id.excerciseGroup4);
        Button[] groups = {group1, group2, group3, group4};

        //if the user chose a muscle group for the date
        for(int i = 0; i < muscleGroups.length - 2; i++){
            if(workoutDay.contains(muscleGroups[i])){
                todayMuscles.addAll(Arrays.asList(muscles[i]));
            }
        }

        //if the user chose rest or choice day
        if(workoutDay.contains("Choice")){
            for (String[] muscle : muscles) {
                todayMuscles.addAll(Arrays.asList(muscle));
            }
        }

        System.out.println(todayMuscles);

        //updating buttons based on the day the user has chosen
        if(todayMuscles.size() <= 4){
            for (int i = 0; i < todayMuscles.size(); i++) {
                groups[i].setText(todayMuscles.get(i) + " Exercises");
                String muscleString = todayMuscles.get(i);
                if(muscleString.equals("Middle Back"))
                    muscleString = "middle_back";
                if(muscleString.equals("Lower Back"))
                    muscleString = "lower_back";
                if(muscleString.equals("Abs"))
                    muscleString = "abdominals";
                todayMuscleGroupsButtons.add(muscleString.toLowerCase(Locale.ROOT));
            }
            for (int i = 3; i >= todayMuscles.size(); i--) {
                groups[i].setVisibility(View.INVISIBLE);
            }
        } else {
            for (int i = 0; i < 4; i++) {
                String muscle = todayMuscles.get(randomNumber(0, todayMuscles.size()-1));
                String muscleString = muscle;
                if(muscle.equals("Middle Back"))
                    muscleString = "middle_back";
                if(muscle.equals("Lower Back"))
                    muscleString = "lower_back";
                if(muscleString.equals("Abs"))
                    muscleString = "abdominals";
                todayMuscleGroupsButtons.add(muscleString.toLowerCase(Locale.ROOT));
                todayMuscles.remove(muscle);
                groups[i].setText(muscle + " Exercises");
            }
        }
    }

    public int randomNumber(int a, int b) {
        double x = Math.floor(Math.random() * (b - a + 1) + a);
        return (int) x;
    }

    public void loadData(String day){
        FileInputStream fis = null;
        try{
            fis = openFileInput("userData.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            boolean read = false;
            boolean nameTrue = true;
            int count = 0;
            String group = "";
            while((text = br.readLine()) != null){
                //reads first line to get the name of the user
                if(nameTrue){
                    name = "Siya";
                    nameTrue = false;
                }
                //if the day isnt sunday and the line the reader is at is the next date then no more changes will occur
                if(!day.equals("Sunday") && text.equals(weekDays[Arrays.asList(weekDays).indexOf(day) + 1])){
                    read = false;
                }
                if(read){
                    count++;
                    //if this is the second muscle group then the text will appear differently on the screen
                    if(count == 2){
                        group = group.replace("Day", ("& " + text.substring(0,1).toUpperCase() + text.substring(1)));
                    } else {
                        group = (text.substring(0,1).toUpperCase() + text.substring(1) + " Day");
                    }
                }
                //if the line equals to the the day of the week boolean is true so the loop will read the next lines
                if(text.equals(day)){
                    read = true;
                }
//                sb.append(text).append("\n");
            }

            //if the user didn't input a choice then it is automatically set to choice day
            if(group.equals("")){
                group = "Choice Day";
            }

            workoutDay = group;

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

    public void navProfile(View v){
        startActivity(new Intent(HomePage.this, UserProfile.class));
    }

    public void navExercises(View v){
        startActivity(new Intent(HomePage.this, MainExcercises.class));
    }

    public void navLogWorkout(View v){
        startActivity(new Intent(HomePage.this, LogWorkout.class));
    }

    public void navLog(View v){
        startActivity(new Intent(HomePage.this, CalendarLog.class));
    }
}