package com.example.healthapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class GetUserData extends AppCompatActivity {

    String name ="";
    String gender="";
    int month=0;
    int day=0;
    int year=0;
    int feet=0;
    int inches =0;
    int weight = 0;

    private TextView nameErr, genderErr, dateErr, heightErr, weightErr;

    private EditText nameTxt , monthTxt,dayTxt,yearTtx,feetTxt ,inchTxt,weightTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_user_data);

        nameErr = findViewById(R.id.nameErr);
        genderErr = findViewById(R.id.genderErr);
        dateErr = findViewById(R.id.dateErr);
        heightErr = findViewById(R.id.heightErr);
        weightErr = findViewById(R.id.weightErr);

        View femaleBtn = findViewById(R.id.femaleBtn);
        View notSayBtn = findViewById(R.id.notSayBtn);
        View maleBtn = findViewById(R.id.maleBtn);

        notSayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "other";
                v.setSelected(true);
                femaleBtn.setSelected(false);
                maleBtn.setSelected(false);
            }
        });

        femaleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "female";
                v.setSelected(true);
                notSayBtn.setSelected(false);
                maleBtn.setSelected(false);
            }
        });

        maleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "male";
                v.setSelected(true);
                notSayBtn.setSelected(false);
                femaleBtn.setSelected(false);
            }
        });
    }

    public void submitAboutYou(View view){
         nameTxt = (EditText)findViewById(R.id.nameField);
         monthTxt = (EditText)findViewById(R.id.monthField);
         dayTxt = (EditText)findViewById(R.id.dayField);
         yearTtx = (EditText)findViewById(R.id.yearField);
         feetTxt = (EditText)findViewById(R.id.feetField);
         inchTxt = (EditText)findViewById(R.id.inchField);
         weightTxt = (EditText)findViewById(R.id.weightField);

//         validations
        name = nameTxt.getText().toString();
        if (monthTxt.length()>0)
            month = Integer.parseInt(monthTxt.getText().toString());
        else
            dateErr.setText("Please enter a date");
        if (dayTxt.length()>0)
            day = Integer.parseInt(dayTxt.getText().toString());
        else
            dateErr.setText("Please enter a date");
        if (yearTtx.length()>0)
            year = Integer.parseInt(yearTtx.getText().toString());
        else
            dateErr.setText("Please enter a date");
        if (feetTxt.length()>0)
            feet = Integer.parseInt(feetTxt.getText().toString());
        else
            dateErr.setText("Please enter a height");
        if (inchTxt.length()>0)
            inches = Integer.parseInt(inchTxt.getText().toString());
        else
            dateErr.setText("Please enter a height");
        if (weightTxt.length()>0)
            weight = Integer.parseInt(weightTxt.getText().toString());
        else
            dateErr.setText("Please enter a weight");

        boolean works = true;

        if (name.equals("")){
            works = false;
            nameErr.setText("Please enter a name");
        }else{
            nameErr.setText("");
        }

        if(gender.equals("")) {
            works = false;
            genderErr.setText("Please select a gender");
        } else
            genderErr.setText("");

        if (month < 1 || month>12){
            works = false;
            dateErr.setText("Please enter a valid date");
        }else{
            dateErr.setText("");
            if (day < 1 || day>31){
                works = false;
                dateErr.setText("Please enter a valid date");
            }else{
                dateErr.setText("");
                if (year < 1900 || year>2022){
                    works = false;
                    dateErr.setText("Please enter a valid date");
                }else{
                    dateErr.setText("");
                    if (year>2009){
                        works = false;
                        dateErr.setText("YOU ARE TOO YOUNG FOR AN OMNIFIT ACCOUNT");
                    }else{
                        dateErr.setText("");
                    }
                }
            }
        }

        if(feet < 3 || feet > 8){
            works = false;
            heightErr.setText("Please enter a valid height");
        }else{
            heightErr.setText("");
            if(inches < 0 || inches > 12){
                works = false;
                heightErr.setText("Please enter a valid height");
            }else{
                heightErr.setText("");
            }
        }

        if(weight < 50 || weight > 500) {
            works = false;
            weightErr.setText("Please enter a valid weight");
        }else{
            weightErr.setText("");
        }

        if(works){
            startActivity(new Intent(GetUserData.this, GetUserPreferences.class));
        }
    }
}