package com.example.healthapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GetUserPreferences extends AppCompatActivity{
    private String fitnessGoal = "";
    private TextView goalErr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_user_preferences);

        View strengthBtn = findViewById(R.id.strengthBtn);
        View plyoBtn = findViewById(R.id.plyoBtn);
        View cardioBtn = findViewById(R.id.cardioBtn);
        View flexibilityBtn = findViewById(R.id.flexibilityBtn);
        View powerliftingBtn = findViewById(R.id.powerliftiingBtn);
        View weightliftingBtn = findViewById(R.id.weightliftingBtn);
        goalErr = findViewById(R.id.goalErr);

        TextView dragLeg = (TextView) findViewById(R.id.dragLegs);
        dragLeg.setOnLongClickListener(new LongClickListener());

        TextView dragChoice = (TextView) findViewById(R.id.dragChoice);
        dragChoice.setOnLongClickListener(new LongClickListener());

        TextView dragArms = (TextView) findViewById(R.id.dragArms);
        dragArms.setOnLongClickListener(new LongClickListener());

        TextView dragBack = (TextView) findViewById(R.id.dragBack);
        dragBack.setOnLongClickListener(new LongClickListener());

        TextView dragChest = (TextView) findViewById(R.id.dragChest);
        dragChest.setOnLongClickListener(new LongClickListener());

        TextView dragRest = (TextView) findViewById(R.id.dragRest);
        dragRest.setOnLongClickListener(new LongClickListener());

        TextView dragSho = (TextView) findViewById(R.id.dragSho);
        dragSho.setOnLongClickListener(new LongClickListener());

        LinearLayout topLayerMon = (LinearLayout) findViewById(R.id.mon);
        LinearLayout topLayerTues = (LinearLayout) findViewById(R.id.tues);
        LinearLayout topLayerWed = (LinearLayout) findViewById(R.id.wed);
        LinearLayout topLayerThurs = (LinearLayout) findViewById(R.id.thurs);
        LinearLayout topLayerFri = (LinearLayout) findViewById(R.id.fri);
        LinearLayout topLayerSat = (LinearLayout) findViewById(R.id.sat);
        LinearLayout topLayerSun = (LinearLayout) findViewById(R.id.sun);

        LinearLayout bottomLayer = (LinearLayout) findViewById(R.id.bottomLinLayout);

        topLayerMon.setOnDragListener(new DragListener());
        topLayerTues.setOnDragListener(new DragListener());
        topLayerWed.setOnDragListener(new DragListener());
        topLayerThurs.setOnDragListener(new DragListener());
        topLayerFri.setOnDragListener(new DragListener());
        topLayerSat.setOnDragListener(new DragListener());
        topLayerSun.setOnDragListener(new DragListener());
        bottomLayer.setOnDragListener(new DragListener());


        strengthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strengthBtn.setSelected(true);
                plyoBtn.setSelected(false);
                cardioBtn.setSelected(false);
                flexibilityBtn.setSelected(false);
                powerliftingBtn.setSelected(false);
                weightliftingBtn.setSelected(false);
                fitnessGoal = "strength";
            }
        });

        plyoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strengthBtn.setSelected(false);
                plyoBtn.setSelected(true);
                cardioBtn.setSelected(false);
                flexibilityBtn.setSelected(false);
                powerliftingBtn.setSelected(false);
                weightliftingBtn.setSelected(false);
                fitnessGoal = "plyometrics";
            }
        });

        cardioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strengthBtn.setSelected(false);
                plyoBtn.setSelected(false);
                cardioBtn.setSelected(true);
                flexibilityBtn.setSelected(false);
                powerliftingBtn.setSelected(false);
                weightliftingBtn.setSelected(false);
                fitnessGoal = "cardio";
            }
        });

        flexibilityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strengthBtn.setSelected(false);
                plyoBtn.setSelected(false);
                cardioBtn.setSelected(false);
                flexibilityBtn.setSelected(true);
                powerliftingBtn.setSelected(false);
                weightliftingBtn.setSelected(false);
                fitnessGoal = "flexibility";
            }
        });

        powerliftingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strengthBtn.setSelected(false);
                plyoBtn.setSelected(false);
                cardioBtn.setSelected(false);
                flexibilityBtn.setSelected(false);
                powerliftingBtn.setSelected(true);
                weightliftingBtn.setSelected(false);
                fitnessGoal = "powerlifting";
            }
        });

        weightliftingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strengthBtn.setSelected(false);
                plyoBtn.setSelected(false);
                cardioBtn.setSelected(false);
                flexibilityBtn.setSelected(false);
                powerliftingBtn.setSelected(false);
                weightliftingBtn.setSelected(true);
                fitnessGoal = "weightlifting";
            }
        });
    }

    public void completeSetUp(View view){
        boolean check = true;
        if (fitnessGoal.equals("")){
            check=false;
            goalErr.setText("Please select your workout goal");
        }else
            goalErr.setText("");

        if(check) {
            startActivity(new Intent(GetUserPreferences.this, HomePage.class));
        }
    }

    private class LongClickListener implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View v) {
            ClipData.Item item = new ClipData.Item((String)v.getTag());
            String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
            ClipData data = new ClipData((String) v.getTag(), mimeTypes, item);
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDrag(data, shadowBuilder, v, 0);
            return true;
        }
    }
    private class DragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch(event.getAction()) {
                case DragEvent.ACTION_DROP:
                    TextView textDropped = (TextView) event.getLocalState();
                    textDropped.setVisibility(View.VISIBLE);
                    ViewGroup viewDroppedAt = (ViewGroup) v;
                    ((ViewGroup) textDropped.getParent()).removeView(textDropped);
                    viewDroppedAt.addView(textDropped);
            }

            return true;
        }
    }
}