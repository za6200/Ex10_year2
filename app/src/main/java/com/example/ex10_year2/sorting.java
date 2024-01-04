package com.example.ex10_year2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class sorting extends AppCompatActivity {

    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;
    Cursor crsr2;
    EditText nameET;
    ArrayList<String> tbl = new ArrayList<>();
    ArrayAdapter<String> adp;
    Intent get_student_info;
    int position = 0;
    Intent personal_input;
    Intent grades_input;
    Intent show_table;
    Intent sorting;
    Intent show_details;
    Intent credit;
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorting);
        nameET = findViewById(R.id.nameET);
        get_student_info = new Intent(this, show_user_details.class);
        personal_input = new Intent(this, personal_info_input.class);
        grades_input = new Intent(this, Grades_info_input.class);
        show_table = new Intent(this, Show_table.class);
        sorting = new Intent(this, sorting.class);
        show_details = new Intent(this, show_user_details.class);
        credit = new Intent(this, credits.class);
        Intent intent = getIntent();
        int option = intent.getIntExtra("option", -1);
        if(option == -1)
        {
            Toast.makeText(getApplicationContext(), "pressed wrong one", Toast.LENGTH_SHORT).show();
        }
        if(option == 0)
        {
            sortByGrade();
        }
        else if(option == 1)
        {
            sortByName();
        }
        else if(option == 2)
        {
            sortByQuarter();
        }
    }

    public void sortByGrade() {
        hlp = new HelperDB(this);
        db = hlp.getReadableDatabase();

        try {
            crsr2 = db.query(Grades.TABLE_GRADES, null, null, null, null, null, Grades.GRADE + " DESC");

            int count = 0;

            crsr2.moveToFirst();
            while (!crsr2.isAfterLast()) {
                int grade_col = crsr2.getColumnIndex(Grades.GRADE);
                int grade = crsr2.getInt(grade_col);
                int grade_input = Integer.parseInt(nameET.getText().toString());

                // Filter students with the specified grade
                if (grade == grade_input) {
                    get_student_info.putExtra("position", count);
                    startActivity(get_student_info);
                    break; // Exit the loop once a match is found
                }

                crsr2.moveToNext();
                count++;
            }
        } catch (NumberFormatException e) {
            // Handle the exception
            Toast.makeText(getApplicationContext(), "Invalid input for grade!", Toast.LENGTH_SHORT).show();
        } finally {
            crsr2.close();
            db.close();
        }
    }


    public void sortByName() {
        hlp = new HelperDB(this);
        db = hlp.getReadableDatabase();
        crsr = db.query(Users.TABLE_USERS, null, null, null, null, null, null);
        int name = crsr.getColumnIndex(Users.NAME);
        String user_name_input = nameET.getText().toString();
        String user_name = "";
        int count = 0, check = 0;

        crsr.moveToFirst();
        while (!crsr.isAfterLast()) {
            user_name = crsr.getString(name);

            // Filter students with the specified name
            if (user_name.equals(user_name_input)) {
                get_student_info.putExtra("position", count);
                startActivity(get_student_info);
                check = 1; // Set the check flag
                break; // Exit the loop once a match is found
            }

            count++;
            crsr.moveToNext();
        }

        if (check == 0) {
            Toast.makeText(getApplicationContext(), "The name provided is not in the list!!", Toast.LENGTH_SHORT).show();
        }

        crsr.close();
        db.close();
    }

    public void sortByQuarter() {
        hlp = new HelperDB(this);
        db = hlp.getReadableDatabase();

        try {
            int quarter2 = Integer.parseInt(nameET.getText().toString());
            int count = 0;
            int check = 0;

            crsr2 = db.query(Grades.TABLE_GRADES, null, Grades.QUARTER + "=?", new String[]{String.valueOf(quarter2)}, null, null, null);

            crsr2.moveToFirst();
            while (!crsr2.isAfterLast()) {
                get_student_info.putExtra("position", count);
                startActivity(get_student_info);
                check = 1;
                count++;
                crsr2.moveToNext();
            }

            if (check == 0) {
                Toast.makeText(getApplicationContext(), "No records found for the specified quarter!!", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            // Handle the exception
            Toast.makeText(getApplicationContext(), "Invalid input for quarter!", Toast.LENGTH_SHORT).show();
        } finally {
            crsr2.close();
            db.close();
        }
    }



    public void show_user_details(View view)
    {
        startActivity(get_student_info);
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