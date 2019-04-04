/*
*   NAME                  : FinalGradeCalculator.java
*   PROJECT              : PROG3150 - A01
*   PROGRAMMERS : Bence Karner 5307 & Randy Lefebvre 2256
*   DESCRIPTION      : This file contains all the logic and source code required to execute the
*       applications grade calculation activity.
*/


package com.example.randy_bence.student_grade_calculator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


/*
 *  CLASS                   : FinalGradeCalculator
 *  DESCRIPTION     : This class is used to define the behaviour of the controls found in
 *      content_final_grade_calculator.xml. The class has multiple methods for responding to onClick events
 *      linked to the various  buttons.
 */
public class FinalGradeCalculator extends AppCompatActivity
{
    private static final String FILE_NAME = "savedGrades.txt";


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
        setContentView(R.layout.activity_final_grade_calculator);


        //Get the toolbar we created from the layout
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //If we don't find the toolbar, lets not load this.. If we cannot
        //find the toolbar and try to load this, we will crash the program
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        // Set the Back Button to bring us back to the main menu page
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), MainMenu.class));
            }
        });

        // Set up the spinners for both semester and year
        Spinner spinnerSemester = (Spinner) findViewById(R.id.spinnerSemester);
        ArrayAdapter<CharSequence> adapterSemester = ArrayAdapter.createFromResource(this,
                R.array.semester_array, android.R.layout.simple_spinner_item);
        adapterSemester.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSemester.setAdapter(adapterSemester);

        Spinner spinnerYear = (Spinner) findViewById(R.id.spinnerYear);
        ArrayAdapter<CharSequence> adapterYear = ArrayAdapter.createFromResource(this,
                R.array.year_array, android.R.layout.simple_spinner_item);
        adapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(adapterYear);
    }


    /*
     *  METHOD            : CalculateButton
     *  PARAMETERS    : Vew view : Contains data on the view which triggered the event
     *  DESCRIPTION     : This method is used to grab the numerical values in the weight and mark
     *       sections of the page, and calculate the final result before displaying the number
     *  RETURN             :  void : Has no return value
     */
    public void onClickCalculate(View calculateButton)
    {
        EditText etWeight = findViewById(R.id.etInputWeightRow1);
        EditText etGradeField = findViewById(R.id.etInputGradeRow1);
        int etWeightValue = 0;
        int etGradeFieldValue = 0;
        int calculatedResult = 0;
        float percentValue = 0;


        //Get the integer value of the weight, and grade fields if they are not empty
        if ((etWeight.getText().toString().trim().length()) > 0)
        {
            etWeightValue = Integer.parseInt(etWeight.getText().toString());
        }
        if ((etGradeField.getText().toString().trim().length()) > 0)
        {
            etGradeFieldValue = Integer.parseInt(etGradeField.getText().toString());
            percentValue = ((float)etGradeFieldValue / 100);
        }


        //Calculate the final weighted value of the assigned work
        if((etWeightValue > 0) && (etGradeFieldValue > 0))
        {
            calculatedResult = (int)((float)etWeightValue * percentValue);
        }


        //Print the calculate value into the textview
        TextView result = findViewById(R.id.tvFinalResultRow1);
        result.setText(String.valueOf(calculatedResult));



        /* *********************************************************/
        /*                  Create Student Object                  */
        /* *********************************************************/
        //Store the information into a Student Class
        Student newStudent = new Student();
        // Get Name
        TextView stuName = findViewById(R.id.etInputNameRow1);
        newStudent.name= stuName.getText().toString();
        // Get Weight
        TextView stuWeight = findViewById(R.id.etInputGradeRow1);
        String weightString = stuWeight.getText().toString();
        newStudent.weight= (weightString.equals("")) ? -1 : Double.parseDouble(weightString);
        // Get the Mark
        TextView stuMark = findViewById(R.id.etInputWeightRow1);
        String markString = stuMark.getText().toString();
        newStudent.mark= (markString.equals("")) ? -1 : Double.parseDouble(markString);
        // Get the Final mark
        TextView stuFinal = findViewById(R.id.tvFinalResultRow1);
        String finalString = stuFinal.getText().toString();
        newStudent.finalMark= (finalString.equals("")) ? -1 : Double.parseDouble(finalString);
        // Get Course Name
        TextView stuCourse = findViewById(R.id.evCourseName);
        newStudent.courseName = stuCourse.getText().toString();
        // Get the Semester
        Spinner stuSemester = findViewById(R.id.spinnerSemester);
        newStudent.semester = stuSemester.getSelectedItem().toString();
        // Get the Year
        Spinner stuYear = findViewById(R.id.spinnerYear);
        newStudent.year = stuYear.getSelectedItem().toString();
        /* *********************************************************/
        /*                  End Student Object                     */
        /* *********************************************************/

    }



    /*
     *  METHOD            : onClickSave
     *  PARAMETERS    :  View saveButton : Details on the view which triggered the event
     *  DESCRIPTION     : This method is used to save a string to a text file, in the devices internal storage
     *  RETURN             :  void : Has no return value
     *
     *  NOTE: Much of the code used in this method was taken directly from the following resource.
     *  CodingInFlow. (nd). Write Text File to Internal Storage. Retrieved Feb 6, 2019, from https://codinginflow
     *  .com/tutorials/android/write-text-file-to-internal-storage
     */
/*    public void onClickSave(View saveButton)
    {
        //Grab the content from each field, in each  row
        TextView result = findViewById(R.id.tvFinalResultRow1);
        String savedResult = result.getText().toString();
        FileOutputStream writeStream = null;

        try
        {
            writeStream = openFileOutput(FILE_NAME, MODE_PRIVATE);
            writeStream.write(savedResult.getBytes());
            Toast.makeText(this, "File Saved" , Toast.LENGTH_LONG).show();
        }
        catch (Exception exception)
        {
            Log.e("onClickSave", exception.toString());
        }
        finally
        {

            //Ensure the file stream is closed before returning
            if (writeStream != null)
            {
                try
                {
                    writeStream.close();
                }
                catch (IOException exception)
                {
                    Log.e("onClickSave", exception.toString());
                }
            }
        }
    }
*/


    /*
     *  METHOD            : onClickLoad
     *  PARAMETERS    : View loadButton : Details on the view which triggered the event
     *  DESCRIPTION     : Loads a string with the previously saved value stored from  tvFinalResultRow1
     *  RETURN             :  void : Has no return value
     *
     *  NOTE: Much of the code used in this method was taken directly from the following resource.
     *  CodingInFlow. (nd). Write Text File to Internal Storage. Retrieved Feb 6, 2019, from https://codinginflow
     *  .com/tutorials/android/write-text-file-to-internal-storage
     */
/*    public void onClickLoad(View loadButton)
    {
        FileInputStream readStream = null;
        try
        {

            //Open a stream for reading in the contents of the saved file
            readStream = openFileInput(FILE_NAME);
            InputStreamReader fileStreamReader = new InputStreamReader(readStream);
            BufferedReader textReader = new BufferedReader(fileStreamReader);
            StringBuilder displayString = new StringBuilder();


            //Read the file and append its contents to a string
            String text;
            while ((text = textReader.readLine()) != null)
            {
                displayString.append(text).append("\n");
            }


            //Print the file contents to the screen
            TextView result = findViewById(R.id.tvFinalResultRow1);
            result.setText(displayString.toString());
        }
        catch (Exception exception)
        {
            Log.e("onClickLoad", exception.toString());
        }
        finally
        {

            //Ensure the file stream is closed before returning
            if (readStream != null)
            {
                try
                {
                    readStream.close();
                }
                catch (IOException exception)
                {
                    Log.e("onClickLoad", exception.toString());
                }
            }
        }
    }*/
}
