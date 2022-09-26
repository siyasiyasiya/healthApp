package com.example.healthapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{
    public DBHelper(Context context) {
        super(context,"UserWorkoutLog.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table WorkoutDay(date TEXT primary key, minutes TEXT, rating TEXT, exerciseList TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists WorkoutDay");
    }

    public Boolean insertWorkoutDay(String date, String minutes, String exerciseList, String rating){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("date",date);
        contentValues.put("minutes",minutes);
        contentValues.put("rating",rating);
        contentValues.put("exerciseList",exerciseList);
//        contentValues.put("day",day);
        long result = DB.insert("WorkoutDay", null, contentValues);
        if (result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Boolean updateWorkoutDay(String date, String minutes, String exerciseList, String rating){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("minutes",minutes);
        contentValues.put("exerciseList",exerciseList);
        contentValues.put("rating",rating);
//        contentValues.put("day",day);

        Cursor cursor = DB.rawQuery("Select * from WorkoutDay where date = ?",new String[]{date});
        if (cursor.getCount()>0){
            long result = DB.update("WorkoutDay",contentValues,"date=?",new String[] {date});
            if (result == -1){
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }

    }

    public Boolean deleteData(String date){
        SQLiteDatabase DB = this.getWritableDatabase();

        Cursor cursor = DB.rawQuery("Select * from WorkoutDay where date = ?",new String[]{date});
        if (cursor.getCount()>0){

            long result = DB.delete("WorkoutDay","date=?",new String[] {date});
            if (result == -1){
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }
    }

    public Cursor getData (){
        SQLiteDatabase DB = this.getReadableDatabase();

        Cursor cursor = DB.rawQuery("Select * from WorkoutDay",null);
        return cursor;

    }
}
