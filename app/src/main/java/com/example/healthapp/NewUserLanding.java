package com.example.healthapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;


public class NewUserLanding extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!loadData()) {
            setContentView(R.layout.activity_new_user_landing);
        }else{
            Intent intent = new Intent(NewUserLanding.this, HomePage.class);
            startActivity(intent);
        }
    }

    public void createProfile(View view){
        Intent intent = new Intent(NewUserLanding.this, GetUserData.class);
        startActivity(intent);
    }

    public boolean loadData(){
        FileInputStream fis = null;
        try{
            fis = openFileInput("userData.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            if(br.readLine().length()>1){
                return true;
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
        return false;
    }
}

