package com.example.ex10_year2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Show_table extends AppCompatActivity implements AdapterView.OnItemClickListener{
    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;
    Cursor crsr2;
    ListView lvrecords;
    ArrayList<String> tbl = new ArrayList<>();
    ArrayAdapter adp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_table);
        lvrecords = findViewById(R.id.lvrecords);
        addToTable();
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

        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, tbl);
        lvrecords.setAdapter(adp);
    }



    public void removeFromTable(int position)
    {
        db = hlp.getWritableDatabase();
        db.delete(Users.TABLE_USERS, Users.KEY_ID+"=?", new String[]{Integer.toString(position + 1)});
        db.close();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {

    }
}