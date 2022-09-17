package com.example.healthapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomePage extends AppCompatActivity {
    String[] fitnessFactArr = {"You use 200 muscles to take a single step",
                                "The pressure on your feet when you run is as much as 4 times your body weight",
                                "Eating protein before your workout increases gains in muscle mass",
                                "The average person walks about 70,000 miles in their lifetime",
                                "Working out increases your lifespan",
                                "It takes at least 12 weeks of regular exercise to get into shape",
                                "Switching up your workout will help you lose more weight",
                                "Being dehydrated impairs your exercise performance",
                                "People who don’t exercise regularly can lose 80% of their strength by age 65",
                                "Walking briskly burns nearly as many calories as jogging",
                                "Staying active reduces your risk of many cancers",
                                "You’re never too old to benefit from exercise",
                                "Regular exercise improves your mental health",
                                "You’re more productive when you’re active",
                                "It only takes 2.5 hours of moderate physical activity to see cardiovascular benefits",
                                "People who exercise regularly have higher vitamin D levels in their blood.",
                                "Regular exercise can prevent illness",
                                "or every pound of muscle gained, the body burns 50 extra calories every day",
                                "People burn an average of 50 calories every hour while they sleep",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                ""};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
    }

    public void navProfile(View v){
        startActivity(new Intent(HomePage.this, UserProfile.class));
    }
}