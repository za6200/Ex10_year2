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

        crsr2 = db.query(Grades.TABLE_GRADES, null, null, null, null, null, null);
        int grade_col = crsr2.getColumnIndex(Grades.GRADE);

        int grade = 0;
        int grade1 = 0;
        crsr2.moveToFirst();
        grade = crsr2.getInt(grade_col);
        int gradeArr[];
        int count = 0;

        crsr.moveToFirst();
        int best_grade = crsr2.getInt(grade_col);
        while (!crsr.isAfterLast())
        {
            grade = crsr2.getInt(grade_col);
            while (!crsr.isAfterLast())
            {
                grade1 = crsr2.getInt(grade_col);
                if(grade > grade1)
                {
                    best_grade = grade;
                }
                else
                {
                    best_grade = grade1;
                }


                crsr2.moveToNext();
                get_student_info.putExtra("position", count);
                startActivity(get_student_info);
                count++;
            }

        }



        crsr.close();
        crsr2.close();
        db.close();
    }
    public void sortByName()
    {
        hlp = new HelperDB(this);
        db = hlp.getReadableDatabase();
        crsr = db.query(Users.TABLE_USERS, null, null, null, null, null, null);
        int name = crsr.getColumnIndex(Users.NAME);
        String user_name_input = nameET.getText().toString();
        String user_name = "";
        int count = 0, check = 0;
        crsr.moveToFirst();
        while (!crsr.isAfterLast())
        {
            user_name = crsr.getString(name);
            if(user_name.equals(user_name_input))
            {
                check = 1;
                get_student_info.putExtra("position", count);
            }
            count++;
            crsr.moveToNext();
        }
        if(check == 0)
        {
            Toast.makeText(getApplicationContext(), "The name provided is not in the list!!", Toast.LENGTH_SHORT).show();
        }
        crsr.close();

    }
    public void sortByQuarter() {
        hlp = new HelperDB(this);
        db = hlp.getReadableDatabase();
        crsr2 = db.query(Grades.TABLE_GRADES, null, null, null, null, null, null);
        int quarter_col = crsr2.getColumnIndex(Grades.QUARTER);

        try {
            int quarter2 = Integer.parseInt(nameET.getText().toString());
            int quarter = 0;
            int count = 0;
            int check = 0;

            crsr2.moveToFirst();
            while (!crsr2.isAfterLast()) {
                quarter = crsr2.getInt(quarter_col);
                if (quarter == quarter2) {
                    check = 1;
                    get_student_info.putExtra("position", count);
                    startActivity(get_student_info);
                }
                count++;
                crsr2.moveToNext();
            }

            if (check == 0) {
                Toast.makeText(getApplicationContext(), "The quarter provided is not in the list!!", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e)
        {
        }

        db.close();
        crsr2.close();
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