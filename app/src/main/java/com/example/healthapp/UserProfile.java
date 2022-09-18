package com.example.healthapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserProfile extends AppCompatActivity {
    String name = "";
    String gender = "";
    int month = 0;
    int day = 0;
    int year=0;
    int feet=0;
    int inches =0;
    int weight = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        TextView nameLbl = findViewById(R.id.nameLbl);
        TextView genderView = findViewById(R.id.genderView);
        TextView birthdayView = findViewById(R.id.birthdayView);
        TextView heightView = findViewById(R.id.heightView);
        TextView weightView = findViewById(R.id.weightView);

        loadData();

        nameLbl.setText("Hi " + name +"!");
        genderView.setText(gender);
        birthdayView.setText(month +"/"+day+"/"+year);
        heightView.setText(feet + " ft " + inches+" in");
        weightView.setText(weight+ " lbs");

    }

    public void navHome(View v){
        startActivity(new Intent(UserProfile.this, HomePage.class));
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
//            while((text = br.readLine()) != null){
//                sb.append(text).append("\n");
//            }

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