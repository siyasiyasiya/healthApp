package com.example.healthapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class MainExcercises extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String[] workoutTypes = {"All", "Cardio", "Plyometrics", "Strength", "Powerlifting", "Stretching", "Olympic Weightlifting"};
    private static final String[] muscleGroups = {"All", "Glutes", "Quads", "Hamstrings", "Calves", "Adductors", "Abductors","Biceps", "Forearms", "Triceps", "Middle Back", "Lower Back", "Lats","Chest", "Traps", "Neck","Abdominals"};
    private static final String[] levels = {"All", "Beginner", "Intermediate", "Expert"};

    Spinner muscleSelect, typeSelect, levelSelect;

    String muscle = "all", type = "all", level = "all";

    public static FragmentManager fragmentManager;

    public boolean loaded = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_excercises);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            muscle = extras.getString("muscle");
            type = extras.getString("type");
            level = extras.getString("level");
        }

        muscleSelect = (Spinner)findViewById(R.id.muscleSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainExcercises.this,
                android.R.layout.simple_spinner_item, muscleGroups);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        muscleSelect.setAdapter(adapter);
        muscleSelect.setOnItemSelectedListener(this);

        typeSelect = (Spinner)findViewById(R.id.typeSpinner);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(MainExcercises.this,
                android.R.layout.simple_spinner_item, workoutTypes);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSelect.setAdapter(adapter2);
        typeSelect.setOnItemSelectedListener(this);

        levelSelect = (Spinner)findViewById(R.id.difficultySpinner);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(MainExcercises.this,
                android.R.layout.simple_spinner_item, levels);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        levelSelect.setAdapter(adapter3);
        levelSelect.setOnItemSelectedListener(this);

//        initialize exercise filters if already picked
        switch (level) {
            case "all":
                levelSelect.setSelection(0);
                break;
            case "beginner":
                levelSelect.setSelection(1);
                break;
            case "intermediate":
                levelSelect.setSelection(2);
                break;
            case "expert":
                levelSelect.setSelection(3);
                break;
        }

        switch (muscle) {
            case "all":
                muscleSelect.setSelection(0);
                break;
            case "glutes":
                muscleSelect.setSelection(1);
                break;
            case "quadriceps":
                muscleSelect.setSelection(2);
                break;
            case "hamstrings":
                muscleSelect.setSelection(3);
                break;
            case "calves":
                muscleSelect.setSelection(4);
                break;
            case "adductors":
                muscleSelect.setSelection(5);
                break;
            case "abductors":
                muscleSelect.setSelection(6);
                break;
            case "biceps":
                muscleSelect.setSelection(7);
                break;
            case "forearms":
                muscleSelect.setSelection(8);
                break;
            case "triceps":
                muscleSelect.setSelection(9);
                break;
            case "middle_back":
                muscleSelect.setSelection(10);
                break;
            case "lower_back":
                muscleSelect.setSelection(11);
                break;
            case "lats":
                muscleSelect.setSelection(12);
                break;
            case "chest":
                muscleSelect.setSelection(13);
                break;
            case "traps":
                muscleSelect.setSelection(14);
                break;
            case "neck":
                muscleSelect.setSelection(15);
                break;
            case "abdominals":
                muscleSelect.setSelection(16);
                break;
        }

        switch (type) {
            case "all":
                typeSelect.setSelection(0);
                break;
            case "cardio":
                typeSelect.setSelection(1);
                break;
            case "plyometrics":
                typeSelect.setSelection(2);
                break;
            case "strength":
                typeSelect.setSelection(3);
                break;
            case "powerlifting":
                typeSelect.setSelection(4);
                break;
            case "stretching":
                typeSelect.setSelection(5);
                break;
            case "olympic_weightlifting":
                typeSelect.setSelection(6);
                break;
        }

        LinearLayout ll = (LinearLayout) findViewById(R.id.exerciseTable);
        ll.removeAllViews();
        callApi();
    }

    public void navHome(View v){
        startActivity(new Intent(MainExcercises.this, HomePage.class));
    }

    public void navLog(View v){
        startActivity(new Intent(MainExcercises.this, CalendarLog.class));
    }

    public void navLogWorkout(View v){
        startActivity(new Intent(MainExcercises.this, LogWorkout.class));
    }

    public void navProfile(View v){
        startActivity(new Intent(MainExcercises.this, UserProfile.class));
    }

//get spinner selection item
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        if(parent==muscleSelect) {
            switch (position) {
                case 0:
                    muscle = "all";
                    break;
                case 1:
                    muscle = "glutes";
                    break;
                case 2:
                    muscle = "quadriceps";
                    break;
                case 3:
                    muscle = "hamstrings";
                    break;
                case 4:
                    muscle = "calves";
                    break;
                case 5:
                    muscle = "adductors";
                    break;
                case 6:
                    muscle = "abductors";
                    break;
                case 7:
                    muscle = "biceps";
                    break;
                case 8:
                    muscle = "forearms";
                    break;
                case 9:
                    muscle = "triceps";
                    break;
                case 10:
                    muscle = "middle_back";
                    break;
                case 11:
                    muscle = "lower_back";
                    break;
                case 12:
                    muscle = "lats";
                    break;
                case 13:
                    muscle = "chest";
                    break;
                case 14:
                    muscle = "traps";
                    break;
                case 15:
                    muscle = "neck";
                    break;
                case 16:
                    muscle = "abdominals";
                    break;
            }
        }

        if(parent == typeSelect){
            switch (position) {
                case 0:
                    type = "all";
                    break;
                case 1:
                    type = "cardio";
                    break;
                case 2:
                    type = "plyometrics";
                    break;
                case 3:
                    type = "strength";
                    break;
                case 4:
                    type = "powerlifting";
                    break;
                case 5:
                    type = "stretching";
                    break;
                case 6:
                    type = "olympic_weightlifting";
                    break;

            }
        }

        if(parent==levelSelect){
            switch (position) {
                case 0:
                    level = "all";
                    break;
                case 1:
                    level = "beginner";
                    break;
                case 2:
                    level = "intermediate";
                    break;
                case 3:
                    level = "expert";
                    break;
            }
        }
        if(loaded) {
////        empty layout with views every time a filter is changed
            LinearLayout ll = (LinearLayout) findViewById(R.id.exerciseTable);
            ll.removeAllViews();
            callApi();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

//    make call to api
    public void callApi(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://exercises-by-api-ninjas.p.rapidapi.com/v1/exercises?";

//        add values to api url based on user filter
        boolean paramAdded = false;
        if(!Objects.equals(type, "all")){
            url+="type="+type;
            paramAdded = true;
        }
        if(!Objects.equals(muscle, "all")){
            if (paramAdded)
                url+="&muscle="+muscle;
            else {
                url += "muscle=" + muscle;
                paramAdded = true;
            }
        }
        if(!Objects.equals(level, "all")){
            if (paramAdded)
                url+="&difficulty="+level;
            else {
                url += "difficulty=" + level;
            }
        }

//        send req to API
        JsonArrayRequest
                jsonObjReq
                = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
//                            send data to fragment
                                fragmentManager = getSupportFragmentManager();
                                if(findViewById(R.id.exerciseTable)!=null){
                                    loaded= true;
                                    if(response.length()==0){
                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                        NoExercisesFound noExerciseFoundFragment = new NoExercisesFound();
                                        fragmentTransaction.add(R.id.exerciseTable, noExerciseFoundFragment).commit();
                                    }else{
                                        for (int i = 0; i < response.length(); i++) {
                                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                            ExerciseDisplayFragment exerciseDisplayFragment = new ExerciseDisplayFragment();
                                            Bundle b = new Bundle();
                                            b.putString("name", response.getJSONObject(i).getString("name"));
                                            b.putString("type", response.getJSONObject(i).getString("type"));
                                            b.putString("muscle", response.getJSONObject(i).getString("muscle"));
                                            b.putString("equipment", response.getJSONObject(i).getString("equipment"));
                                            b.putString("difficulty", response.getJSONObject(i).getString("difficulty"));
                                            b.putString("instructions", response.getJSONObject(i).getString("instructions"));
                                            b.putString("muscle2", muscle);
                                            b.putString("type2",type);
                                            b.putString("level2", level);
                                            exerciseDisplayFragment.setArguments(b);
                                            fragmentTransaction.add(R.id.exerciseTable, exerciseDisplayFragment).commit();
                                        }
                                    }
                                    findViewById(R.id.loadingPanel).setVisibility(View.INVISIBLE);
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
}