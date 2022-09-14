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

public class GetUserData extends AppCompatActivity {

    String name ="";
    String gender="";
    int month=0;
    int day=0;
    int year=0;
    int feet=0;
    int inches =0;
    int weight = 0;

    TextView nameErr, genderErr, dateErr, heightErr, weightErr;

    EditText nameTxt , monthTxt,dayTxt,yearTtx,feetTxt ,inchTxt,weightTxt;

    Button maleBtn, femaleBtn, notSayBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_user_data);

        nameErr = findViewById(R.id.nameErr);
        genderErr = findViewById(R.id.genderErr);
        dateErr = findViewById(R.id.dateErr);
        heightErr = findViewById(R.id.heightErr);
        weightErr = findViewById(R.id.weightErr);
    }

    public void femaleBtnClick(View view){
        gender = "female";
//        femaleBtn.setBackgroundColor(R.color.purple_200);
//        maleBtn.setBackgroundColor(Color.parseColor("#E3E3E3"));
//        notSayBtn.setBackgroundColor(Color.parseColor("#E3E3E3"));
    }

    public void maleBtnClick(View view){
        gender = "male";
//        femaleBtn.setBackgroundColor(Color.parseColor("#E3E3E3"));
//        maleBtn.setBackgroundColor(Color.parseColor("#F9245B"));
//        notSayBtn.setBackgroundColor(Color.parseColor("#E3E3E3"));
    }

    public void notSayClick(View view){
        gender = "not say";
//        femaleBtn.setBackgroundColor(Color.parseColor("#E3E3E3"));
//        maleBtn.setBackgroundColor(Color.parseColor("#E3E3E3"));
//        notSayBtn.setBackgroundColor(Color.parseColor("#F9245B"));
    }

    public void submitAboutYou(View view){
         nameTxt = (EditText)findViewById(R.id.nameField);
         monthTxt = (EditText)findViewById(R.id.monthField);
         dayTxt = (EditText)findViewById(R.id.dayField);
         yearTtx = (EditText)findViewById(R.id.yearField);
         feetTxt = (EditText)findViewById(R.id.feetField);
         inchTxt = (EditText)findViewById(R.id.inchField);
         weightTxt = (EditText)findViewById(R.id.weightField);

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
            dateErr.setText("");
            if(inches < 0 || inches > 12){
                works = false;
                heightErr.setText("Please enter a valid height");
            }else{
                dateErr.setText("");
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