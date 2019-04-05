/*
 *   NAME                  : MainMenu.java
 *   PROJECT              : PROG3150 - A01
 *   PROGRAMMERS : Bence Karner 5307 & Randy Lefebvre 2256
 *   DESCRIPTION      : This file contains all the logic and source code required to being the application, and
 *   acts as the entry point for the program.
 */



package com.example.randy_bence.student_grade_calculator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;



/*
 *  CLASS                   : MainMenu
 *  DESCRIPTION     : This class is used to control the application as the main menu, and allows the user to
 *  selected between two activities.
 */
public class MainMenu extends AppCompatActivity
{


    /*
     *  METHOD            : onCreate
     *  PARAMETERS     : Bundle savedInstanceState : Take the saved state data when re-creating
     *   controls on the screen
     *  DESCRIPTION      : This method is used as the constructor of the activity, and is
     *       responsible for re-creating each element when the onCreate stage is executed
     *  RETURN              : void : Has no return value
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_manu);

        // Set the tool bar we created in layouts and change the title
        ListView listView;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Grade Calculator Main Menu");


        listView = findViewById(R.id.listview);


        // Add items into the array. Selectable activities
        // The first one is empty because it is hidden behind the toolbar
        final ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("");
        arrayList.add("Final Grade Calculator");
        arrayList.add("Grade Conversion Chart");
        arrayList.add("Previous Semester View");




        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);




        // Create onclick listeners with a switch statement to send them to the right activity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {


                //Toast.makeText(MainMenu.this, "Clicked item: "+position+" "+arrayList.get(position), Toast.LENGTH_SHORT).show();
                Toast.makeText(MainMenu.this, "Loading "+arrayList.get(position)+"...", Toast.LENGTH_SHORT).show();
                switch (position)
                {
                    case 1:
                        Intent startNewActivity = new Intent(MainMenu.this, FinalGradeCalculator.class);
                        startActivity(startNewActivity);
                        break;

                    case 2:
                        Intent startAnotherActivity = new Intent(MainMenu.this, ConvertGrade.class);
                        startActivity(startAnotherActivity);
                        break;

                    case 3:
                        Intent startThirdActivity = new Intent(MainMenu.this, PreviousSemesterView.class);
                        startActivity(startThirdActivity);
                        break;


                    default:
                        break;
                }
            }
        });
    }
}
