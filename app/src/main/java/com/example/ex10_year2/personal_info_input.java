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
import android.widget.Switch;

public class personal_info_input extends AppCompatActivity {
    EditText student_name, address, phone_number, home_phone_number, parents_name, parents_phone_number;
    SQLiteDatabase db;
    HelperDB hlp;
    ContentValues cv = new ContentValues();

    Intent get_grade_info;
    Intent personal_input;
    Intent grades_input;
    Intent show_table;
    Intent sorting;
    Intent show_details;
    Intent credit;

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
        personal_input = new Intent(this, personal_info_input.class);
        grades_input = new Intent(this, Grades_info_input.class);
        show_table = new Intent(this, Show_table.class);
        sorting = new Intent(this, sorting.class);
        show_details = new Intent(this, show_user_details.class);
        credit = new Intent(this, credits.class);

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