package com.example.healthapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import java.net.URI;


public class NewUserLanding extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_landing);
    }

    public void createProfile(View view){
        Intent intent = new Intent(NewUserLanding.this, GetUserData.class);
        startActivity(intent);
    }
}

