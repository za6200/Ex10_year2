package com.example.ex10_year2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class credits extends AppCompatActivity {
    TextView textView;
    Intent personal_input;
    Intent grades_input;
    Intent show_table;
    Intent sorting;
    Intent show_details;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
        textView = findViewById(R.id.textView2); // Initialize the textView
        textView.setText("thanks to my parents for getting me to who I am :)");

        personal_input = new Intent(this, personal_info_input.class);
        grades_input = new Intent(this, Grades_info_input.class);
        show_table = new Intent(this, Show_table.class);
        sorting = new Intent(this, sorting.class);
        show_details = new Intent(this, show_user_details.class);
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