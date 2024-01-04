package com.example.ex10_year2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class Grades_info_input extends AppCompatActivity {
    EditText subject, task_type, quarter;
    EditText grade;
    SQLiteDatabase db;
    HelperDB hlp;
    Intent get_show_table;
    Intent personal_input;
    Intent grades_input;
    Intent show_table;
    Intent sorting;
    Intent show_details;
    Intent credit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades_info_input);
        subject = findViewById(R.id.subject);
        grade = findViewById(R.id.grade);
        task_type = findViewById(R.id.task_type);
        quarter = findViewById(R.id.quarter);
        get_show_table = new Intent(this, Show_table.class);
        personal_input = new Intent(this, personal_info_input.class);
        grades_input = new Intent(this, Grades_info_input.class);
        show_table = new Intent(this, Show_table.class);
        sorting = new Intent(this, sorting.class);
        show_details = new Intent(this, show_user_details.class);
        credit = new Intent(this, credits.class);
    }

    public void next(View view) {
        insertData();
        startActivity(get_show_table);
    }

    public void insertData() {
        hlp = new HelperDB(this);
        ContentValues cv = new ContentValues();
        cv.clear();
        cv.put(Grades.SUBJECTS, subject.getText().toString());
        cv.put(Grades.GRADE, grade.getText().toString());
        cv.put(Grades.TASK_TYPE, task_type.getText().toString());
        cv.put(Grades.QUARTER, quarter.getText().toString());
        db = hlp.getWritableDatabase();
        db.insert(Grades.TABLE_GRADES, null, cv);
        db.close();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String st = item.getTitle().toString();
        if (st.equals("credit")) {
            startActivity(credit);
        }
        else if(st.equals("personal input"))
        {
            startActivity(personal_input);
        }
        else if(st.equals("grade input"))
        {
            startActivity(grades_input);
        }
        else if(st.equals("show table"))
        {
            startActivity(show_table);
        }
        else if(st.equals("sorting"))
        {
            startActivity(sorting);
        }
        else if(st.equals("show details"))
        {
            startActivity(show_details);
        }
        return true;
    }
}
