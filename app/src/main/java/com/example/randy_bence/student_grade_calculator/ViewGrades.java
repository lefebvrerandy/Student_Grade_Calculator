package com.example.randy_bence.student_grade_calculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


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

        /* ***************************************/
        /*  We now need to find out how many       */
        /*  rows we have in the database linking   */
        /*  the grades that were stored  .         */
        /*  We can then iterating through each     */
        /*  row and place them into the tablelayout*/
        /* *****************************************/

        // Add rows to the table layout
        TableLayout tl = findViewById(R.id.grades_table);
        TableRow tr = new TableRow(this);
        TextView course = new TextView(this);;
        TextView weight = new TextView(this);;
        TextView finalMark = new TextView(this);;

        /*  This is debug to show how to dynamically add a row          */
        /*  Here we should check the database for each row stored       */
        /*      Then store that information into the student class      */
        /*      Then place that stored information into a new row       */
        // Set labels to their values
        course.setText(" SEF ");
        weight.setText(" 80 ");
        finalMark.setText(" 100 ");

        // Add the textviews to the row
        tr.addView(course);
        tr.addView(weight);
        tr.addView(finalMark);

        // Add the row to the table layout
        tl.addView(tr);
    }
}