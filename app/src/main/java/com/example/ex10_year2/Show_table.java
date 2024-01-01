package com.example.ex10_year2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Show_table extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnCreateContextMenuListener{
    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;
    ListView lvrecords;
    ArrayList<String> tbl = new ArrayList<>();

    ArrayAdapter<String> adp;
    Intent get_student_info;
    Intent editUserIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_table);
        lvrecords = findViewById(R.id.lvrecords);
        lvrecords.setOnCreateContextMenuListener(this);

        addToTable();

        get_student_info = new Intent(this, show_user_details.class);
        editUserIntent = new Intent(this, edit_user.class);
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
        int position = info.position;

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
}