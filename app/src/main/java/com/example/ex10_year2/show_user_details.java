package com.example.ex10_year2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class show_user_details extends AppCompatActivity {

    SQLiteDatabase db;
    ArrayAdapter<String> adp;
    HelperDB hlp;
    Cursor crsr;
    Cursor crsr2;
    ArrayList<String> tbl = new ArrayList<>();
    ListView lvrecords;
    Intent personal_input;
    Intent grades_input;
    Intent show_table;
    Intent sorting;
    Intent show_details;
    Intent credit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user_details);
        lvrecords = findViewById(R.id.lvrecords);
        personal_input = new Intent(this, personal_info_input.class);
        grades_input = new Intent(this, Grades_info_input.class);
        show_table = new Intent(this, Show_table.class);
        sorting = new Intent(this, sorting.class);
        show_details = new Intent(this, show_user_details.class);
        credit = new Intent(this, credits.class);
        addToTable();

    }

    public void addToTable() {
        hlp = new HelperDB(this);
        db = hlp.getReadableDatabase();
        Intent intent = getIntent();

        // Extract the position from the intent
        int position = intent.getIntExtra("position", 1);

        crsr = db.query(Users.TABLE_USERS, null, null, null, null, null, null);
        int name = crsr.getColumnIndex(Users.NAME);
        int address = crsr.getColumnIndex(Users.ADDRESS);
        int mobile_phone = crsr.getColumnIndex(Users.MOBILE_PHONE);
        int home_phone = crsr.getColumnIndex(Users.HOME_PHONE);
        int parents_name = crsr.getColumnIndex(Users.PARENTS_NAME);
        int parents_number = crsr.getColumnIndex(Users.PARENTS_NUMBER);

        String user_name, user_address, user_phone, user_home_phone, users_parents_name, users_parents_number;
        crsr.moveToPosition(position);
        user_name = crsr.getString(name);
        user_address = crsr.getString(address);
        user_phone = crsr.getString(mobile_phone);
        user_home_phone = crsr.getString(home_phone);
        users_parents_name = crsr.getString(parents_name);
        users_parents_number = crsr.getString(parents_number);

        String tmp = "user_name: " + user_name;
        tbl.add(tmp);
        tmp = "user_address: " + user_address;
        tbl.add(tmp);
        tmp = "user_phone: " + user_phone;
        tbl.add(tmp);
        tmp = "user_home_phone: " + user_home_phone;
        tbl.add(tmp);
        tmp = "users_parents_name: " + users_parents_name;
        tbl.add(tmp);
        tmp = "users_parents_number: " + users_parents_number;
        tbl.add(tmp);

        crsr2 = db.query(Grades.TABLE_GRADES, null, null, null, null, null, null);
        int subject_col = crsr2.getColumnIndex(Grades.SUBJECTS);
        int grade_col = crsr2.getColumnIndex(Grades.GRADE);

        int task_col = crsr2.getColumnIndex(Grades.TASK_TYPE);
        int quarter_col = crsr2.getColumnIndex(Grades.QUARTER);

        int grade, quarter;  // Declare these variables
        String subject, task;
        crsr2.moveToPosition(position);
        subject = crsr2.getString(subject_col);
        grade = crsr2.getInt(grade_col);
        task = crsr2.getString(task_col);
        quarter = crsr2.getInt(quarter_col);

        tmp = "subject: " + subject;
        tbl.add(tmp);
        tmp = "grade: " + grade;
        tbl.add(tmp);
        tmp = "task: " + task;
        tbl.add(tmp);
        tmp = "quarter: " + quarter;
        tbl.add(tmp);
        adp = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, tbl);
        lvrecords.setAdapter(adp);
        crsr2.close();
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
