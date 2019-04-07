package com.example.randy_bence.student_grade_calculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;


/*
 *  CLASS            :
 *  DESCRIPTION     :
 */
public class SupportPage extends AppCompatActivity {



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
        getSupportActionBar().setTitle("Support");

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