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
import android.widget.TextView;


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

        // Get values from semester_array and store on screen
        String[] strArray = getResources().getStringArray(R.array.semester_array);
        TextView tv = findViewById(R.id.textView2);
        tv.setText("");
        for (String s: strArray) {
            tv.append(s + "\n");
        }

        //  Get values from year_array and store on screen
        strArray = getResources().getStringArray(R.array.year_array);
        tv = findViewById(R.id.textView5);
        tv.setText("");
        for (String s: strArray) {
            tv.append(s + "\n");
        }

    }
}
