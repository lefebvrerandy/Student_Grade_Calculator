/*
*   NAME                        : FinalGradeCalculator.java
*   PROJECT                   : PROG3150 - A01
 *   PROGRAMMERS  : Bence Karner & Randy Lefebvre & Travis Roy & Felicia Neeb
*   DESCRIPTION          : This file contains all the logic and source code required to execute the
*       applications grade calculation activity.
*/


package com.example.randy_bence.student_grade_calculator;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


/*
 *  CLASS                   : FinalGradeCalculator
 *  DESCRIPTION     : This class is used to define the behaviour of the controls found in
 *      content_final_grade_calculator.xml. The class has multiple methods for responding to onClick events
 *      linked to the various  buttons.
 */
public class FinalGradeCalculator extends AppCompatActivity
{
    private Activity activityContext = FinalGradeCalculator.this;
    private static Student studentMarks = new Student ();

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
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_final_grade_calculator );


        //Get the toolbar we created from the layout
        Toolbar toolbar = findViewById ( R.id.toolbar );
        setSupportActionBar ( toolbar );


        //If we don't find the toolbar, lets not load this.. If we cannot
        //find the toolbar and try to load this, we will crash the program
        if ( getSupportActionBar () != null )
        {
            getSupportActionBar ().setDisplayShowHomeEnabled ( true );
            getSupportActionBar ().setDisplayHomeAsUpEnabled ( true );
        }


        // Set the Back Button to bring us back to the main menu page
        toolbar.setNavigationOnClickListener ( new View.OnClickListener ()
        {
            @Override
            public void onClick(View v)
            {
                startActivity ( new Intent ( getApplicationContext (), MainMenu.class ) );
            }
        } );

        // Set up the spinners for both semester and year
        Spinner spinnerSemester = (Spinner) findViewById ( R.id.spinnerSemester );
        ArrayAdapter<CharSequence> adapterSemester = ArrayAdapter.createFromResource ( this,
                R.array.semester_array, android.R.layout.simple_spinner_item );
        adapterSemester.setDropDownViewResource ( android.R.layout.simple_spinner_dropdown_item );
        spinnerSemester.setAdapter ( adapterSemester );

        Spinner spinnerYear = (Spinner) findViewById ( R.id.spinnerYear );
        ArrayAdapter<CharSequence> adapterYear = ArrayAdapter.createFromResource ( this,
                R.array.year_array, android.R.layout.simple_spinner_item );
        adapterYear.setDropDownViewResource ( android.R.layout.simple_spinner_dropdown_item );
        spinnerYear.setAdapter ( adapterYear );
    }


    /*
     *  METHOD            : MyAsyncTask
     *  PARAMETERS    : Void : Takes no arguments
     *  DESCRIPTION     : This method/class is used to asynchronously call the file writing
     *  functionality of the application. Once the user clicks on the calculate button, the method
     *  creates an instance of the fileIO class, and performs runtime permission checks
     *  before writing the marks to a PDF file.
     *  RETURN             :  void : Has no return value
     */
    private class MyAsyncTask extends AsyncTask<Void, Void, Void>
    {
        public Void doInBackground(Void... params)
        {
            //Ensure the app has all required permissions before continuing
            FileIO fileManager = new FileIO ();
            if ( fileManager.CheckWritePermissions ( activityContext ) )
            {

                //Permission granted -> Proceed to save the marks as a PDF file in the
                // external storage location
                TextView result = findViewById ( R.id.tvFinalResultRow1 );
                fileManager.SaveMarks ( activityContext, studentMarks );
            }
            return null;
        }
    }


    /*
     *  METHOD        : CalculateButton
     *  PARAMETERS    : Vew view : Contains data on the view which triggered the event
     *  DESCRIPTION   : This method is used to grab the numerical values in the weight and mark
     *       sections of the page, and calculate the final result before displaying the number
     *  RETURN        : void : Has no return value
     */
    public void onClickCalculate(View calculateButton)
    {
        EditText etWeight = findViewById ( R.id.etInputWeightRow1 );
        EditText etGradeField = findViewById ( R.id.etInputGradeRow1 );
        int etWeightValue = 0;
        int etGradeFieldValue = 0;
        int calculatedResult = 0;
        float percentValue = 0;


        //Get the integer value of the weight, and grade fields if they are not empty
        if ( (etWeight.getText ().toString ().trim ().length ()) > 0 )
        {
            etWeightValue = Integer.parseInt ( etWeight.getText ().toString () );
        }
        if ( (etGradeField.getText ().toString ().trim ().length ()) > 0 )
        {
            etGradeFieldValue = Integer.parseInt ( etGradeField.getText ().toString () );
            percentValue = ((float) etGradeFieldValue / 100);
        }


        //Calculate the final weighted value of the assigned work
        if ( (etWeightValue > 0) && (etGradeFieldValue > 0) )
        {
            calculatedResult = (int) ((float) etWeightValue * percentValue);
        }


        //Print the calculate value into the textview
        TextView result = findViewById ( R.id.tvFinalResultRow1 );
        result.setText ( String.valueOf ( calculatedResult ) );

        // Database Object
        GradesDatabase database = new GradesDatabase(this);

        // Store the information into the Semester table
        Semester semester = new Semester();

        // Get the Semester
        Spinner stuSemester = findViewById ( R.id.spinnerSemester );
        String SemesterTime = stuSemester.getSelectedItem ().toString ();


        // Get the Year
        Spinner stuYear = findViewById ( R.id.spinnerYear );
        String SemesterYear = stuYear.getSelectedItem ().toString ();

        String semesterName = SemesterTime + " " + SemesterYear;
        semester.setName(semesterName);

        long insertID = database.insertIntoSemester(semester);

        // Store the information into the Grades table
        Grade grade = new Grade();

        grade.setSemesterId(semester.getId());

        String courseName;

        // Get Course Name
        TextView stuCourse = findViewById ( R.id.evCourseName );
        if ( stuCourse.getText ().toString ().isEmpty () )
        {
            courseName = "Mobile Application Development";
        } else
        {
            courseName = stuCourse.getText ().toString ();
        }

        grade.setClassNAme(courseName);

        // Get Weight
        TextView stuWeight = findViewById ( R.id.etInputGradeRow1 );
        String weightString = stuWeight.getText ().toString ();
        int weight = (weightString.equals ( "" )) ? -1 : Integer.parseInt ( weightString );

        grade.setClassWeight(weight);

        // Get the Mark
        TextView stuMark = findViewById ( R.id.etInputWeightRow1 );
        String markString = stuMark.getText ().toString ();
        double finalGrade = (markString.equals ( "" )) ? -1 : Double.parseDouble ( markString );

        grade.setFinalGrade(finalGrade);

        long insertId = database.insertIntoGrade(grade);

        // Get the Final mark
        TextView stuFinal = findViewById ( R.id.tvFinalResultRow1 );
        String finalString = stuFinal.getText ().toString ();
        double SemesterGPA = (finalString.equals ( "" )) ? -1 : Double.parseDouble ( finalString );

        semester.setGPA(SemesterGPA);
        long updateId = database.updateSemester(semester);
        //Save the file asynchronously
        MyAsyncTask AsyncPrint = new MyAsyncTask ();
        AsyncPrint.doInBackground ();
    }
}