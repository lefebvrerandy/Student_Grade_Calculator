/*
 *   NAME                  : ConvertGrade.java
 *   PROJECT              : PROG3150 - A01
 *   PROGRAMMERS : Bence Karner 5307 & Randy Lefebvre 2256
 *   DESCRIPTION      : This file contains all the logic and source code required to display the grade
 *   conversion chart as part of the second activity.
 */



package com.example.randy_bence.student_grade_calculator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;



/*
 *  CLASS                   : ConvertGrade
 *  DESCRIPTION     : This class is used to contain the elements required to create the grade conversion chart.
 */
public class ConvertGrade extends AppCompatActivity {



    /*
     *  METHOD            : onCreate
     *  PARAMETERS     : Bundle savedInstanceState : Take the saved state data when re-creating
     *   controls on the screen
     *  DESCRIPTION      : This method is used as the constructor of the activity, and is
     *       responsible for re-creating each element when the onCreate stage is executed
     *  RETURN              : void : Has no return value
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_grade);

        // Set the tool bar we created in layouts and change the title
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Grade Conversion Chart");

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
    }
}
