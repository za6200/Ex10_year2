package com.example.ex10_year2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class edit_user extends AppCompatActivity {

    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;
    int position;

    EditText studentNameEditText;
    EditText addressEditText;
    EditText phoneNumberEditText;
    EditText homePhoneNumberEditText;
    EditText parentsNameEditText;
    EditText parentsPhoneNumberEditText;

    EditText subjectEditText;
    EditText gradeEditText;
    EditText taskTypeEditText;
    EditText quarterEditText;
    Intent get_show_table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        hlp = new HelperDB(this);
        db = hlp.getReadableDatabase();

        // Retrieve position from intent
        position = getIntent().getIntExtra("position", -1);

        // Initialize EditText fields for user details
        studentNameEditText = findViewById(R.id.student_name);
        addressEditText = findViewById(R.id.address);
        phoneNumberEditText = findViewById(R.id.phone_number);
        homePhoneNumberEditText = findViewById(R.id.home_phone_number);
        parentsNameEditText = findViewById(R.id.parents_name);
        parentsPhoneNumberEditText = findViewById(R.id.parents_phone_number);

        // Initialize EditText fields for grade details
        subjectEditText = findViewById(R.id.subject);
        gradeEditText = findViewById(R.id.grade);
        taskTypeEditText = findViewById(R.id.task_type);
        quarterEditText = findViewById(R.id.quarter);

        // Load user details
        loadUserDetails();
        get_show_table = new Intent(this, Show_table.class);
    }

    private void loadUserDetails() {
        db = hlp.getReadableDatabase();

        // Load user details
        Cursor userCursor = db.query(Users.TABLE_USERS, null, null, null, null, null, null);
        int nameCol = userCursor.getColumnIndex(Users.NAME);
        int addressCol = userCursor.getColumnIndex(Users.ADDRESS);
        int phoneNumberCol = userCursor.getColumnIndex(Users.MOBILE_PHONE);
        int homePhoneNumberCol = userCursor.getColumnIndex(Users.HOME_PHONE);
        int parentsNameCol = userCursor.getColumnIndex(Users.PARENTS_NAME);
        int parentsPhoneNumberCol = userCursor.getColumnIndex(Users.PARENTS_NUMBER);

        userCursor.moveToPosition(position);

        // Set user details in the respective EditText fields
        studentNameEditText.setText(userCursor.getString(nameCol));
        addressEditText.setText(userCursor.getString(addressCol));
        phoneNumberEditText.setText(userCursor.getString(phoneNumberCol));
        homePhoneNumberEditText.setText(userCursor.getString(homePhoneNumberCol));
        parentsNameEditText.setText(userCursor.getString(parentsNameCol));
        parentsPhoneNumberEditText.setText(userCursor.getString(parentsPhoneNumberCol));

        userCursor.close();

        // Load grade details
        crsr = db.query(Grades.TABLE_GRADES, null, null, null, null, null, null);
        int keyCol = crsr.getColumnIndex(Grades.KEY_ID);
        int gradeCol = crsr.getColumnIndex(Grades.GRADE);
        int subjectCol = crsr.getColumnIndex(Grades.SUBJECTS);
        int taskCol = crsr.getColumnIndex(Grades.TASK_TYPE);
        int quarterCol = crsr.getColumnIndex(Grades.QUARTER);

        crsr.moveToPosition(position);

        // Set grade details in the respective EditText fields
        subjectEditText.setText(crsr.getString(subjectCol));
        gradeEditText.setText(String.valueOf(crsr.getInt(gradeCol)));
        taskTypeEditText.setText(crsr.getString(taskCol));
        quarterEditText.setText(String.valueOf(crsr.getInt(quarterCol)));

        crsr.close();
        db.close();
    }


    private void saveUserDetails() {
        // Get the edited values from EditText fields
        String editedName = studentNameEditText.getText().toString();
        String editedAddress = addressEditText.getText().toString();
        String editedPhoneNumber = phoneNumberEditText.getText().toString();
        String editedHomePhoneNumber = homePhoneNumberEditText.getText().toString();
        String editedParentsName = parentsNameEditText.getText().toString();
        String editedParentsPhoneNumber = parentsPhoneNumberEditText.getText().toString();

        ContentValues userCV = new ContentValues();
        userCV.put(Users.NAME, editedName);
        userCV.put(Users.ADDRESS, editedAddress);
        userCV.put(Users.MOBILE_PHONE, editedPhoneNumber);
        userCV.put(Users.HOME_PHONE, editedHomePhoneNumber);
        userCV.put(Users.PARENTS_NAME, editedParentsName);
        userCV.put(Users.PARENTS_NUMBER, editedParentsPhoneNumber);

        // Open the database in write mode
        db = hlp.getWritableDatabase();

        // Update the user details in the database
        db.update(Users.TABLE_USERS, userCV, Users.KEY_ID + "=?", new String[]{String.valueOf(position + 1)});

        db.close();
    }

    private void saveGradeDetails() {
        // Get the edited values from EditText fields
        String editedSubject = subjectEditText.getText().toString();
        int editedGrade = Integer.parseInt(gradeEditText.getText().toString());
        String editedTaskType = taskTypeEditText.getText().toString();
        int editedQuarter = Integer.parseInt(quarterEditText.getText().toString());

        // Open the database in write mode
        db = hlp.getWritableDatabase();

        // Prepare ContentValues for updating grade details
        ContentValues gradeCV = new ContentValues();
        gradeCV.put(Grades.SUBJECTS, editedSubject);
        gradeCV.put(Grades.GRADE, editedGrade);
        gradeCV.put(Grades.TASK_TYPE, editedTaskType);
        gradeCV.put(Grades.QUARTER, editedQuarter);

        db.update(Grades.TABLE_GRADES, gradeCV, Grades.KEY_ID + "=?", new String[]{String.valueOf(position + 1)});

        // Close the database
        db.close();
    }

    public void confirm(View view) {
        saveUserDetails();
        saveGradeDetails();
        startActivity(get_show_table);
    }
}
