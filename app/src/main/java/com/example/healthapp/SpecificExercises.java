package com.example.healthapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SpecificExercises extends AppCompatActivity {
    String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_exercises);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            type = extras.getString("type");
        }

        Log.d("data",callApi());

    }

    public void goBack(View v){
        startActivity(new Intent(SpecificExercises.this, MainExcercises.class));
    }

    public String callApi(){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://exercises-by-api-ninjas.p.rapidapi.com/v1/exercises?muscle=biceps")
                .get()
                .addHeader("X-RapidAPI-Key", "247582ae51msh56290d9b7f6a437p1ab9a1jsnf3636db8cad3")
                .addHeader("X-RapidAPI-Host", "exercises-by-api-ninjas.p.rapidapi.com")
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void navHome(View v){
        startActivity(new Intent(SpecificExercises.this, HomePage.class));
    }

    public void navProfile(View v){
        startActivity(new Intent(SpecificExercises.this, UserProfile.class));
    }
}