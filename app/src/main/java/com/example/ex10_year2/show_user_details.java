package com.example.ex10_year2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.ArrayList;

public class show_user_details extends AppCompatActivity {

    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;
    ArrayList<String> tbl = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user_details);
    }

    public void addToTable() {
        hlp = new HelperDB(this);
        db = hlp.getReadableDatabase();

        crsr = db.query(Users.TABLE_USERS, null, null, null, null, null, null);
        int name = crsr.getColumnIndex(Users.NAME);
        int address = crsr.getColumnIndex(Users.ADDRESS);
        int mobile_phone = crsr.getColumnIndex(Users.MOBILE_PHONE);
        int home_phone = crsr.getColumnIndex(Users.HOME_PHONE);
        int parents_name = crsr.getColumnIndex(Users.PARENTS_NAME);
        int parents_number = crsr.getColumnIndex(Users.PARENTS_NUMBER);

        String user_name, user_address, user_phone, user_home_phone, users_parents_name, users_parents_number;

        while (!crsr.isAfterLast()) {
            user_name = crsr.getString(name);
            user_address = crsr.getString(address);
            user_phone = crsr.getString(mobile_phone);
            user_home_phone = crsr.getString(home_phone);
            users_parents_name = crsr.getString(parents_name);
            users_parents_number = crsr.getString(parents_number);

            String tmp = "" + user_name + ", " + user_address + ", " + user_phone + ", " + user_home_phone + ", " + users_parents_name + ", " + users_parents_number;
            tbl.add(tmp);

            crsr.moveToNext();
        }

        crsr = db.query(Grades.TABLE_GRADES, null, null, null, null, null, null);
        int key_col = crsr.getColumnIndex(Grades.KEY_ID);
        int grade_col = crsr.getColumnIndex(Grades.GRADE);
        int subject_col = crsr.getColumnIndex(Grades.SUBJECTS);
        int task_col = crsr.getColumnIndex(Grades.TASK_TYPE);
        int quarter_col = crsr.getColumnIndex(Grades.QUARTER);

        int key, grade, quarter;  // Declare these variables
        String subject, task;

        crsr.moveToFirst();
        while (!crsr.isAfterLast()) {
            key = crsr.getInt(key_col);
            grade = crsr.getInt(grade_col);
            subject = crsr.getString(subject_col);
            task = crsr.getString(task_col);
            quarter = crsr.getInt(quarter_col);

            String tmp = "" + key + ", " + grade + ", " + subject + ", " + task + ", " + quarter;
            tbl.add(tmp);

            crsr.moveToNext();
        }
        crsr.close();
        db.close();
    }
}

/*
crsr = db.query(Grades.TABLE_GRADES, null, null, null, null, null, null);
        int key_col = crsr.getColumnIndex(Grades.KEY_ID);
        int grade_col = crsr.getColumnIndex(Grades.GRADE);
        int subject_col = crsr.getColumnIndex(Grades.SUBJECTS);
        int task_col = crsr.getColumnIndex(Grades.TASK_TYPE);
        int quarter_col = crsr.getColumnIndex(Grades.QUARTER);

        int key, grade, quarter;  // Declare these variables
        String subject, task;

        crsr.moveToFirst();
        while (!crsr.isAfterLast()) {
        key = crsr.getInt(key_col);
        grade = crsr.getInt(grade_col);
        subject = crsr.getString(subject_col);
        task = crsr.getString(task_col);
        quarter = crsr.getInt(quarter_col);

        String tmp = "" + grade;// + ", " + grade + ", " + subject + ", " + task + ", " + quarter;
        tbl.add(tmp);
        crsr.moveToNext();
        }
        crsr.close();
        db.close();
        adp = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, tbl);
        lvrecords.setAdapter(adp);*/
