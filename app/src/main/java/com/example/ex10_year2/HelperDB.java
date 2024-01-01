package com.example.ex10_year2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.SQLClientInfoException;

public class HelperDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dbexam.db";
    private static final int DATABASE_VERSION = 1;
    String strCreate, strDelete;
    public HelperDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        strCreate="CREATE TABLE "+Users.TABLE_USERS;
        strCreate+=" ("+ Users.KEY_ID+" INTEGER PRIMARY KEY,";
        strCreate+=" "+ Users.NAME+" TEXT,";
        strCreate+=" "+Users.ADDRESS+" TEXT,";
        strCreate+=" "+Users.MOBILE_PHONE+" INTEGER,";
        strCreate+=" "+Users.HOME_PHONE+" INTEGER,";
        strCreate+=" "+Users.PARENTS_NAME+" TEXT,";
        strCreate+=" "+Users.PARENTS_NUMBER+" INTEGER";
        strCreate+=");";
        db.execSQL(strCreate);
        strCreate="CREATE TABLE "+Grades.TABLE_GRADES;
        strCreate+=" ("+Grades.KEY_ID+" INTEGER,";
        strCreate+=" "+Grades.SUBJECTS+" TEXT,";
        strCreate+=" "+Grades.GRADE+" INTEGER,";
        strCreate+=" "+Grades.TASK_TYPE+" TEXT,";
        strCreate+=" "+Grades.QUARTER+" INTEGER";
        strCreate+=");";
        db.execSQL(strCreate);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        strDelete="DROP TABLE IF EXISTS "+Users.TABLE_USERS;
        db.execSQL(strDelete);
        strDelete="DROP TABLE IF EXISTS "+Grades.TABLE_GRADES;
        db.execSQL(strDelete);

        onCreate(db);
    }
}
