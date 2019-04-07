package com.example.randy_bence.student_grade_calculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;


public class ViewGrades extends AppCompatActivity {

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
        setContentView(R.layout.activity_view_grades);

        // retrieve the string passed from the activity before
        // this will hold our semester name and year name
        Intent intent = getIntent();
        String semesterYear = intent.getStringExtra("message");

        // parse the string and store into individual string variables
        String[] splitMessage = semesterYear.split("\\s");
        String semester = splitMessage[1];
        String year = splitMessage[0];

        // Set the tool bar we created in layouts and change the title
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(semester + " " + year);

        // If we dont find the toolbar, lets not load this.. If we cannot
        // find the toolbar and try to load this, we will crash the program
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Set the Back Button to bring us back to the main menu page
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PreviousSemesterView.class));
            }
        });


        /* ***************************************/
        /*  We now have semester and year that   */
        /*  we will use to search the database   */
        /*  in the variables semester, year      */
        /* ***************************************/
    }
}