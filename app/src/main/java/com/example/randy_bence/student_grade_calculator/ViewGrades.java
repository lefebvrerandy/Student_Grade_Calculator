package com.example.randy_bence.student_grade_calculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


public class ViewGrades extends AppCompatActivity {

    /*
     *  METHOD            : onCreate
     *  PARAMETERS     : Bundle savedInstanceState : Take the saved state data when re-creating
     *   controls on the screen
     *  DESCRIPTION      : Database code adapted from module 7 example
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
        String title = semester + " " + year;
        getSupportActionBar().setTitle(title);

        /* ***************************************/
        /*  We now have semester and year that   */
        /*  we will use to search the database   */
        /*  in the variables semester, year      */
        /* ***************************************/

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


        // Database Object
        GradesDatabase database = new GradesDatabase(this);

        // Temp grade object to check if the semester has data in it
        Semester semesterTemp = new Semester();
        if(database.CheckSemesterRecord(semesterTemp.getName())) {
            // Add rows to the table layout
            TableLayout tl = findViewById(R.id.grades_table);
            TableRow tr = new TableRow(this);
            TextView course = new TextView(this);

            TextView weight = new TextView(this);

            TextView finalMark = new TextView(this);

            ArrayList<Grade> grades = database.getGrades(title);
            for (Grade grade : grades) {
                course.setText(grade.getClassNAme());
                weight.setText(grade.getClassWeight());
                finalMark.setText(String.valueOf(grade.getFinalGrade()));

                // Add the textviews to the row
                tr.addView(course);
                tr.addView(weight);
                tr.addView(finalMark);

                // Add the row to the table layout
                tl.addView(tr);
            }
        }
    }
}