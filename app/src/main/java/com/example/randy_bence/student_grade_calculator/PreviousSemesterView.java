/*
 *   NAME             : ConvertGrade.java
 *   PROJECT          : PROG3150 - A01
 *   PROGRAMMERS      : Bence Karner 5307 & Randy Lefebvre 2256
 *   DESCRIPTION      :
 */



package com.example.randy_bence.student_grade_calculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/*
 *  CLASS            :
 *  DESCRIPTION     :
 */
public class PreviousSemesterView extends AppCompatActivity {



    /*
     *  METHOD            : onCreate
     *  PARAMETERS     : Bundle savedInstanceState : Take the saved state data when re-creating
     *   controls on the screen
     *  DESCRIPTION      :
     *  RETURN              : void : Has no return value
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_semester);

        // Set the tool bar we created in layouts and change the title
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Previous Semester View");

        // If we dont find the toolbar, lets not load this.. If we cannot
        // find the toolbar and try to load this, we will crash the program
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Set the Back Button to bring us back to the main menu page
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainMenu.class));
            }
        });

        // Get to string arrays. The year and semester
        String[] array_Semester = getResources().getStringArray(R.array.semester_array);
        String[] array_Year = getResources().getStringArray(R.array.year_array);

        // Append to the list each semester for each year
        final ArrayList<String> arrayList = new ArrayList<>();
        for (String year: array_Year) {
            for (String semester: array_Semester) {
                arrayList.add(year + " " + semester);
            }
        }

        // Create a list view
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        ListView listview = findViewById(R.id.lv_Semester_List);
        listview.setAdapter(arrayAdapter);


        // Create onclick listeners with to switch to a new activity once a decision has been made
        // we will also send over their selection to the new activity so that we can use that string
        // to search the database for that information
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {
                Intent startNewActivity = new Intent(PreviousSemesterView.this, ViewGrades.class);
                String selection = arrayList.get(position);
                startNewActivity.putExtra("message", selection);
                startActivity(startNewActivity);
            }
        });


    }
}
