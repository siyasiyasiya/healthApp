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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        ImageView femaleIcon = findViewById(R.id.genderIcon1);
        ImageView maleIcon = findViewById(R.id.genderIcon2);
        ImageView otherIcon = findViewById(R.id.genderIcon3);
        TextView nameLbl = findViewById(R.id.nameLbl);

        loadData();

        nameLbl.setText("Hi " + name +"!");
        if(gender.equals("female")){
            femaleIcon.setAlpha(100);
            maleIcon.setAlpha(25);
            otherIcon.setAlpha(25);
        }else if(gender.equals("male")){
            femaleIcon.setAlpha(25);
            maleIcon.setAlpha(100);
            otherIcon.setAlpha(25);
        }else{
            femaleIcon.setAlpha(25);
            maleIcon.setAlpha(25);
            otherIcon.setAlpha(100);
        }
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