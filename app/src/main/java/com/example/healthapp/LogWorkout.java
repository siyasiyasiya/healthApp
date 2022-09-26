package com.example.healthapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.DownloadManager;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class LogWorkout extends AppCompatActivity {
    public static FragmentManager fragmentManager;
    ArrayList <String> todayWorkout = new ArrayList<>();
    int month=0;
    int day=0;
    int year=0;
    String min;
    String selection = "";
    int num = 0;
    DBHelper DB;

    ArrayList<ImageView> stars = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_workout);
        DB = new DBHelper(this);

        //fragment example
//        for (int i = 0; i < 10; i++) {
//            fragmentManager = getSupportFragmentManager();
//            if(findViewById(R.id.exerciseDisplay)!=null){
//                if(savedInstanceState!=null){
//                    return;
//                }
//
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                ExerciseName exerciseName = new ExerciseName();
//                Button button = findViewById(R.id.viewExerciseBtn);
//                Bundle b = new Bundle();
////                b.putString("exercise", exercises[i]);
////                ExerciseName.setArguments(b);
//                fragmentTransaction.add(R.id.exerciseDisplay, exerciseName).commit();
//            }
//        }

        EditText monthField = findViewById(R.id.monthField);
        EditText dayField = findViewById(R.id.dayField);
        EditText yearField = findViewById(R.id.yearField);
        EditText minField = findViewById(R.id.minLbl);

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DATE);

        yearField.setText(String.valueOf(year));
        monthField.setText(String.valueOf(month));
        dayField.setText(String.valueOf(day));

        ImageView star1 = findViewById(R.id.star1);
        ImageView star2 = findViewById(R.id.star2);
        ImageView star3 = findViewById(R.id.star3);
        ImageView star4 = findViewById(R.id.star4);
        ImageView star5 = findViewById(R.id.star5);

        stars.add(star1);
        stars.add(star2);
        stars.add(star3);
        stars.add(star4);
        stars.add(star5);

        for(ImageView x: stars){
            x.setOnClickListener(view -> {
                //button changes color when clicked
                colorStars(stars.indexOf((ImageView) view));
            });
        }

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                selection = s;
                callApi(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(s.length() == 0){
                    setChosen();
                    selection = "";
                } else {
                    selection = s;
                    callApi(s);
                }
                return false;
            }
        });
    }

    public void setChosen(){
        LinearLayout ll = (LinearLayout) findViewById(R.id.exerciseDisplay);
        ll.removeAllViews();

        if(todayWorkout.size()>0){
            for (int i = 0; i < todayWorkout.size(); i++) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ExerciseName exerciseName = new ExerciseName();
                Bundle b = new Bundle();
                b.putString("name", todayWorkout.get(i));
                b.putString("image", "trash");
                exerciseName.setArguments(b);
                fragmentTransaction.add(R.id.exerciseDisplay, exerciseName).commit();
            }
        }
    }

    public void colorStars(int place){
        for (ImageView star: stars) {
            star.setImageResource(R.drawable.gold_star);
        }

        for (int i = 0; i <= place; i++) {
            stars.get(i).setImageResource(R.drawable.gold_star_full);
        }

        num = place+1;
    }

    public void submitLog(View v){
        EditText monthField = findViewById(R.id.monthField);
        EditText dayField = findViewById(R.id.dayField);
        EditText yearField = findViewById(R.id.yearField);
        EditText minutesField = findViewById(R.id.minLbl);
        TextView errorLbl = findViewById(R.id.errorLbl);
        boolean noError = true;

        if (monthField.length()>0 && Integer.parseInt(monthField.getText().toString()) >= 1 && Integer.parseInt(monthField.getText().toString()) <= 12)
            month = Integer.parseInt(monthField.getText().toString());
        else {
            errorLbl.setText("Please enter a valid date");
            noError = false;
        }
        if (dayField.length()>0 && Integer.parseInt(dayField.getText().toString()) >= 1 && Integer.parseInt(dayField.getText().toString()) <= 31)
            day = Integer.parseInt(dayField.getText().toString());
        else {
            errorLbl.setText("Please enter a valid date");
            noError = false;
        }
        if (yearField.length()>0)
            year = Integer.parseInt(yearField.getText().toString());
        else {
            errorLbl.setText("Please enter a valid date");
            noError = false;
        }
        if (minutesField.length()>0 && Integer.parseInt(minutesField.getText().toString()) > 0)
            min = minutesField.getText().toString();
        else{
            errorLbl.setText("Please enter the duration");
            noError = false;
        }
        if(todayWorkout.size() == 0){
            errorLbl.setText("Please log exercises");
            noError = false;
        }
        if(num == 0){
            errorLbl.setText("Please rate your workout");
            noError = false;
        }

        if(noError){
            String date = month + "/" + day + "/" + year;
            String exerciseList = "";
            for (String x: todayWorkout) {
                exerciseList += x;
                exerciseList += " ";
            }
            String starsNum = String.valueOf(num);

            exerciseList = exerciseList.substring(0, exerciseList.length() - 1);
            System.out.println(exerciseList);

            Boolean checkInsertData = DB.insertWorkoutDay(date, min, exerciseList,starsNum);

            if (checkInsertData){
                Toast.makeText(LogWorkout.this,"Entry Updated",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(LogWorkout.this,"New Entry Not Updated",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void callApi(String name) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://exercises-by-api-ninjas.p.rapidapi.com/v1/exercises?name=" + name;

        System.out.println("running");

        LinearLayout ll = (LinearLayout) findViewById(R.id.exerciseDisplay);
        ll.removeAllViews();

        JsonArrayRequest
                jsonObjReq
                = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println("running2");
                        try {
//                            send data to fragment
                            fragmentManager = getSupportFragmentManager();
                            if (findViewById(R.id.exerciseDisplay) != null) {
                                if (response.length() == 0) {
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    NoExercisesFound noExerciseFoundFragment = new NoExercisesFound();
                                    fragmentTransaction.add(R.id.exerciseDisplay, noExerciseFoundFragment).commit();
                                } else {
                                    for (int i = 0; i < response.length(); i++) {
                                        System.out.println(response.length());
                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                        ExerciseName exerciseName = new ExerciseName();
                                        Bundle b = new Bundle();
                                        b.putString("name", response.getJSONObject(i).getString("name"));

                                        boolean has = false;
                                        for (String x: todayWorkout) {
                                            if(response.getJSONObject(i).getString("name").equals(x)){
                                                has = true;
                                            }
                                        }
                                        if(has){
                                            b.putString("image", "check");
                                        } else {
                                            b.putString("image", "plus");
                                        }

                                        System.out.println(response.getJSONObject(i).getString("name"));
                                        exerciseName.setArguments(b);
                                        fragmentTransaction.add(R.id.exerciseDisplay, exerciseName).commit();
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.e("My error",error.getMessage());

                    }
                }) {

            //            headers for api
            @Override
            public Map getHeaders() throws AuthFailureError
            {
                HashMap mHeaders = new HashMap();
                //mHeaders.put("Content-Type", "application/json");

                mHeaders.put("X-RapidAPI-Key", "247582ae51msh56290d9b7f6a437p1ab9a1jsnf3636db8cad3");
                mHeaders.put("X-RapidAPI-Host", "exercises-by-api-ninjas.p.rapidapi.com");


                return mHeaders;
            }

        };
        // Add the request to the RequestQueue.
        queue.add(jsonObjReq);
        Log.d("TAG",  jsonObjReq.toString());
    }

    public void addExercise(String exercise){
        todayWorkout.add(exercise);
        callApi(selection);
        System.out.println(todayWorkout);
    }

    public void removeExercise(String exercise){
        todayWorkout.remove(exercise);
        setChosen();
        System.out.println(todayWorkout);
    }

    public void navProfile(View v){
        startActivity(new Intent(LogWorkout.this, UserProfile.class));
    }

    public void navExercises(View v){
        startActivity(new Intent(LogWorkout.this, MainExcercises.class));
    }

    public void navHome(View v){
        startActivity(new Intent(LogWorkout.this, HomePage.class));
    }

    public void navLog(View v){
        startActivity(new Intent(LogWorkout.this, CalendarLog.class));
    }
}