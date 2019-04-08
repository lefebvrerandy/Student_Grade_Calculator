package com.example.randy_bence.student_grade_calculator;
/*
 *   NAME                  : FileIO.java
 *   PROJECT              : PROG3150 - A02
 *   PROGRAMMERS : Bence Karner & Randy Lefebvre & Travis Roy & Felicia Neeb
 *   DESCRIPTION      : This file contains all the logic and source code required for the
 *                                 application to save files to the device using the FileIO class.
 */


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;


/*
 *      CLASS                 : FileIO
 *      DESCRIPTION     : This class is used to ensure the user has granted the applicaiton the
 *      correct permissions, before each of the final marks, and writing them to a pdf files.
 *
 *      NOTE:  Much of the source code required to create the PDF file was taken difrectly from
 *      the official Android documentation. For more information, please see,
 *
 *      Android Developers. (n.d.). PrintedPdfDocument. Retrieved on April 5, 2019, from
 *      https://developer.android.com/reference/android/print/pdf/PrintedPdfDocument
 *
 *
 *      NOTE: Much of the runtime permission checking source code originates from an online
 *      tutorial. For more information, please see,
 *
 *       Coding in Flow. (n.d.). Run Time Permission Request. Retrieved on April 5, 2019, from
 *       https://codinginflow.com/tutorials/android/run-time-permission-request
 */
public class FileIO
{

    private int STORAGE_PERMISSION_CODE = 1;     //Denotes if the user granted permission to write to the device
    private static final String TAG = "FileIO";             //TAG used in Logcat calls


    /*
     *  METHOD            : CheckWritePermissions
     *  PARAMETERS    : Activity activityContext : Activity from which the method is called
     *  DESCRIPTION     : This method is used to check if the user has granted the app write
     *  permissions. if true, then it also checks if the external storage location is writable
     *  before returning the result.
     *  RETURN             :  void : Has no return value
     */
    public boolean CheckWritePermissions(Activity activityContext)
    {

        //Check if the proper permissions have been declared in the android manifest
        boolean permissionGranted = false;
        if ( ContextCompat.checkSelfPermission ( activityContext, Manifest.permission.WRITE_EXTERNAL_STORAGE ) == PackageManager.PERMISSION_GRANTED )
        {

            //Check if the external storage is writable (i.e. it exists and we can access it)
            if ( isExternalStorageWritable () )
            {
                permissionGranted = true;
            }
            else
            {
                Toast.makeText ( activityContext, "ERROR: Storage Unavailable", Toast.LENGTH_LONG ).show ();
            }
        }
        else
        {
            requestStoragePermission (activityContext);
        }
        return permissionGranted;
    }


    /*
     *  METHOD            : requestStoragePermission
     *  PARAMETERS    : Activity activityContext : Activity from which the method is called
     *  DESCRIPTION     : This method is used to prompt the user with a dialogue window
     *  used to request write permissions, if they havent't already granted it.
     *  RETURN             :  void : Has no return value
     */
    private void requestStoragePermission(final Activity activityContext)
    {
        if ( ActivityCompat.shouldShowRequestPermissionRationale ( activityContext, Manifest.permission.WRITE_EXTERNAL_STORAGE ) )
        {


            //Create a new alert dialogue windows, and ask the user to grant permission to save
            // the pdf file
            new AlertDialog.Builder ( activityContext )
                    .setTitle ( "Permission Required" )
                    .setMessage ( "This permission is needed to save the file" )
                    .setPositiveButton ( "OK", new DialogInterface.OnClickListener ()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            ActivityCompat.requestPermissions ( activityContext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE );
                        }
                    } )
                    .setNegativeButton ( "Cancel", new DialogInterface.OnClickListener ()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.dismiss ();
                        }
                    } )
                    .create ().show ();
        }
        else
        {
            ActivityCompat.requestPermissions ( activityContext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE );
        }
    }


    /*
     *  METHOD            : onRequestPermissionsResult
     *  DESCRIPTION     : This method is used to respond to the users action once they choose to grant or deny access for the app to write to their device.
     *  RETURN             :  void : Has no return value
     */
    private boolean  onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, Context activityContext)
    {
        boolean permissionGranted = false;

        //If the user granted the request, set to true, and return
        if ( requestCode == STORAGE_PERMISSION_CODE )
        {
            if ( grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED )
            {
                if ( isExternalStorageWritable () )
                {
                    permissionGranted = true;
                    Toast.makeText ( activityContext, "Permission Granted, Please click Save again",
                            Toast.LENGTH_LONG ).show ();
                }
                else
                {
                    //Permission was granted, but the external storage location was not found, or
                    // is unavailable
                    Toast.makeText ( activityContext, "Storage Unavailable", Toast.LENGTH_LONG ).show ();
                }
            }
            else
            {
                //User denied the write permission
                Toast.makeText ( activityContext, "Permission DENIED", Toast.LENGTH_LONG ).show ();
            }
        }

        return permissionGranted;
    }


    /*
     *  METHOD            : isExternalStorageWritable
     *  PARAMETERS    : void : Takes not arguments
     *  DESCRIPTION     : This method is used to check if the external storage location is
     *  writable (i.e. its available, and not read only)
     *  RETURN             :  void : Has no return value
     */
    private boolean isExternalStorageWritable()
    {
        String state = Environment.getExternalStorageState ();
        return (Environment.MEDIA_MOUNTED.equals ( state ));
    }


    /*
     *  METHOD            : SaveMarks
     *  PARAMETERS    : Parameters are as follows,
     *      Activity currentActivity : Activity from which the method is called
     *      Student studentMarks : student object used to contain the users name, year, semester,
     *      marks, course name, and weight. The information held in the object will be written
     *      to the PDF.
     *  DESCRIPTION     : This method is used to write the data held in the Student object, to a
     *  PDF file, in there external drive(sd card)
     *  RETURN             :  void : Has no return value
     */
    public void SaveMarks(Activity currentActivity, Student studentMarks)
    {

        //Create the PDF with with the marks taken from the acitivity screen
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawText("Year: " + "\t" + studentMarks.year, 10, 10, paint);
        canvas.drawText("Semester: " + "\t" + studentMarks.semester, 10, 21, paint);
        canvas.drawText("Student Name: " + "\t" + studentMarks.name, 10, 32, paint);
        canvas.drawText("Course: " + "\t" + studentMarks.courseName, 10, 43, paint);
        canvas.drawText( "Weight: " + "\t" + Double.toString(studentMarks.weight), 10, 54, paint);
        canvas.drawText( "Grade: " + "\t" + Double.toString(studentMarks.mark), 10, 65, paint);
        canvas.drawText( "Final Mark: " + "\t" + Double.toString(studentMarks.finalMark), 10, 76, paint);
        document.finishPage(page);



        //Set the write location to the external drive/SD card
        String directory = Environment.getExternalStorageDirectory().getAbsolutePath();
        File filepath = new File(directory, "/FinalMarkCalculator");
        if (!filepath.exists ())
        {
            filepath.mkdirs ();
        }
        File file = new File(directory + "/FinalMarkCalculator", "savedMarks.pdf");
        FileOutputStream fos = null;
        try
        {

            //Close the PDF, and save it to the device
            fos = new FileOutputStream(file);
            document.writeTo(new FileOutputStream(file));
            document.close();
            fos.close();
            Toast.makeText(currentActivity, "File Saved" , Toast.LENGTH_LONG).show();
        }
        catch (IOException error)
        {
            Log.e(TAG, error.toString());
        }
    }
}
