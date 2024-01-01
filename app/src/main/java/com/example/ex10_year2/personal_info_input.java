package com.example.ex10_year2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

public class personal_info_input extends AppCompatActivity {
    EditText student_name, address, phone_number, home_phone_number, parents_name, parents_phone_number;
    SQLiteDatabase db;
    HelperDB hlp;
    ContentValues cv = new ContentValues();

    Intent get_grade_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info_input);
        student_name = findViewById(R.id.student_name);
        address = findViewById(R.id.address);
        phone_number = findViewById(R.id.phone_number);
        home_phone_number = findViewById(R.id.home_phone_number);
        parents_name = findViewById(R.id.parents_name);
        parents_phone_number = findViewById(R.id.parents_phone_number);


        get_grade_info = new Intent(this, Grades_info_input.class);

    }
    public void insertData()
    {
        hlp = new HelperDB(this);
        cv.clear();
        cv.put(Users.NAME, student_name.getText().toString());
        cv.put(Users.ADDRESS, address.getText().toString());
        cv.put(Users.MOBILE_PHONE, phone_number.getText().toString());
        cv.put(Users.HOME_PHONE, home_phone_number.getText().toString());
        cv.put(Users.PARENTS_NAME, parents_name.getText().toString());
        cv.put(Users.PARENTS_NUMBER, parents_phone_number.getText().toString());
        db = hlp.getWritableDatabase();
        db.insert(Users.TABLE_USERS, null, cv);
        db.close();
    }

    public void next(View view) {
        insertData();
        startActivity(get_grade_info);
    }
}