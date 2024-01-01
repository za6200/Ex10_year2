package com.example.ex10_year2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Grades_info_input extends AppCompatActivity {
    EditText subject, task_type, quarter;
    EditText grade;
    SQLiteDatabase db;
    HelperDB hlp;
    ContentValues cv = new ContentValues();
    Intent get_show_table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades_info_input);
        subject = findViewById(R.id.subject);
        grade = findViewById(R.id.grade);
        task_type = findViewById(R.id.task_type);
        quarter = findViewById(R.id.quarter);
        get_show_table = new Intent(this, Show_table.class);
    }

    public void next(View view) {
        insertData();
        startActivity(get_show_table);
    }

    public void insertData() {
        hlp = new HelperDB(this);
        cv.clear();
        cv.put(Grades.SUBJECTS, subject.getText().toString());
        cv.put(Grades.GRADE, grade.getText().toString());
        cv.put(Grades.TASK_TYPE, task_type.getText().toString());
        cv.put(Grades.QUARTER, quarter.getText().toString());
        db = hlp.getWritableDatabase();
        db.insert(Grades.TABLE_GRADES, null, cv);
        db.close();
    }
}
