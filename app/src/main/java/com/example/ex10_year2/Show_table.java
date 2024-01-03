package com.example.ex10_year2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class Show_table extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnCreateContextMenuListener, AdapterView.OnItemSelectedListener{
    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;
    ListView lvrecords;
    ArrayList<String> tbl = new ArrayList<>();

    ArrayAdapter<String> adp;
    ArrayAdapter<String> adp_spinner;
    Spinner spinner;
    Intent get_student_info;
    Intent editUserIntent;
    Intent sortingIntent;
    Intent personal_input;
    Intent grades_input;
    Intent show_table;
    Intent sorting;
    Intent show_details;
    Intent credit;
    int position;
    String[] options = {"choice", "sort grade", "sort name", "sort quarter"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_table);
        lvrecords = findViewById(R.id.lvrecords);
        spinner = findViewById(R.id.spinner);
        lvrecords.setOnCreateContextMenuListener(this);
        setSpinner();
        addToTable();

        get_student_info = new Intent(this, show_user_details.class);
        editUserIntent = new Intent(this, edit_user.class);
        sortingIntent = new Intent(this, sorting.class);
        personal_input = new Intent(this, personal_info_input.class);
        grades_input = new Intent(this, Grades_info_input.class);
        show_table = new Intent(this, Show_table.class);
        sorting = new Intent(this, sorting.class);
        show_details = new Intent(this, show_user_details.class);
        credit = new Intent(this, credits.class);
    }


    public void addToTable() {
        hlp = new HelperDB(this);
        db = hlp.getReadableDatabase();

        crsr = db.query(Users.TABLE_USERS, null, null, null, null, null, null);
        int name = crsr.getColumnIndex(Users.NAME);

        String user_name;

        crsr.moveToFirst();
        while (!crsr.isAfterLast()) {
            user_name = crsr.getString(name);
            String tmp = "" + user_name;
            tbl.add(tmp);
            crsr.moveToNext();
        }

        adp = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, tbl);
        lvrecords.setAdapter(adp);
        crsr.close();
        db.close();
    }

    public void setSpinner()
    {
        spinner.setOnItemSelectedListener(this);
        adp_spinner = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, options);
        spinner.setAdapter(adp_spinner);

    }



    public void removeFromTable(int position) {
        db = hlp.getWritableDatabase();
        db.delete(Users.TABLE_USERS, Users.KEY_ID + "=?", new String[]{Integer.toString(position + 1)});
        db.close();

        // Remove the item from the ArrayList
        tbl.remove(position);

        // Notify the adapter that the dataset has changed
        adp.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        openContextMenu(view);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        menu.setHeaderTitle("Options");
        menu.add("Show details");
        menu.add("Edit user");
        menu.add("Delete user");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        position = info.position;

        String oper = item.getTitle().toString();
        if (oper.equals("Show details")) {
            get_student_info.putExtra("position", position);
            startActivity(get_student_info);
        }
        if (oper.equals("Edit user")) {
            editUserIntent.putExtra("position", position);
            startActivity(editUserIntent);
        } else if (oper.equals("Delete user")) {
            removeFromTable(position);
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {
        if(i == 0)
        {

        }
        if(i == 1)
        {
            // sorting grade
            sortingIntent.putExtra("option", 0);
            startActivity(sortingIntent);
        }
        else if(i == 2)
        {
            // sort name
            sortingIntent.putExtra("option", 1);
            startActivity(sortingIntent);
        }
        else if(i == 3)
        {
            // sort quarter
            sortingIntent.putExtra("option", 2);
            startActivity(sortingIntent);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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