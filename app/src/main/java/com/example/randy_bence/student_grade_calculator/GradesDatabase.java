package com.example.randy_bence.student_grade_calculator;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class GradesDatabase{
    // Database Constants
    public static final String Database_Name = "GradesDatabase.db";
    public static final int Database_Version = 1;

    //Semester table constants
    public static final String Semester_Table = "Semester";

    public static final String Semester_ID = "_id";
    public static final int Semester_ID_Column = 0;

    public static final String Semester_Name = "SemseterName";
    public static final int Semester_Name_Column = 1;

    // Classes Table constants
    public static final String Class_Table = "Class";

    public static final String Class_ID = "_id";
    public static final int Class_ID_Column = 0;

    public static final String Class_Name = "ClassName";
    public static final int Class_Name_Column = 1;

    public static final String Class_weight = "ClassWeight";
    public static final int Class_Weight_Column = 2;

    // Grades table constants
    public static final String Grades_Table = "Grades";

    public static final String Grades_ID = "_id";
    public static final int Grades_ID_Column = 0;

    public static final String Grades_Semester_ID = "SemesterID";
    public static final int Grades_Semester_ID_Column = 1;

    public static final String Grades_Class_ID = "ClassID";
    public static final int Grades_Class_ID_Column = 2;

    public static final String Final_Grade = "FinalGrade";
    public static final int Final_Grade_Column = 3;

    // CREATE TABLE Statements
    public static final String Create_Semester_Table =
            "CREATE TABLE " + Semester_Table  + " (" +
                    Semester_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Semester_Name + " TEXT NOT NULL UNIQUE);";

    public static final String Create_Class_Table =
            "CREATE TABLE " + Class_Table + " (" +
                    Class_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Class_Name + " TEXT NOT NULL UNIQUE," +
                    Class_weight + "INTEGER NOT NULL);";

    public static final String Create_Grades_Table =
            "CREATE TABLE " + Grades_Table + " (" +
                    Grades_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Grades_Semester_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Grades_Class_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Final_Grade + " REAL NOT NULL);";

    // DROP TABLE Statments
    public static final String Drop_Semester_Table =
            "DROP TABLE IF EXISTS " + Semester_Table;

    public static final String Drop_Class_Table =
            "DROP TABLE IF EXISTS " + Class_Table;

    public static final String Drop_Grade_Table =
            "DROP TABLE IF EXISTS " + Grades_Table;


    public class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            // Create Tables
            sqLiteDatabase.execSQL(Create_Semester_Table);
            sqLiteDatabase.execSQL(Create_Class_Table);
            sqLiteDatabase.execSQL(Create_Grades_Table);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            Log.d("Student Grade Calculator", "Upgrading database from version "
                    + oldVersion + " to " + newVersion);
            sqLiteDatabase.execSQL(GradesDatabase.Drop_Semester_Table);
            sqLiteDatabase.execSQL(GradesDatabase.Drop_Class_Table);
            sqLiteDatabase.execSQL(GradesDatabase.Drop_Grade_Table);
            onCreate(sqLiteDatabase);
        }
    }

    // database and database helper objects
    private SQLiteDatabase SQLiteDatabase;
    private DatabaseHelper dbHelper;

    // Contstuctor
    public GradesDatabase(Context context) {
        dbHelper = new DatabaseHelper(context, Database_Name, null, Database_Version);
    }

    // Private methods
    private void openReadableDB()
    {
        SQLiteDatabase = dbHelper.getReadableDatabase();
    }

    private void openWriteableDB() {
        SQLiteDatabase = dbHelper.getWritableDatabase();
    }

    private void closeDatabase(){
        if (dbHelper != null)
        {
            dbHelper.close();
        }
    }

    private void closeCursor(Cursor cursor){
        if (cursor != null)
        {
            cursor.close();
        }
    }

    // Public methods
    public ArrayList<Semester> getSemesters()
    {
        ArrayList<Semester> semesters  = new ArrayList <Semester>();
        openReadableDB();
        Cursor cursor = SQLiteDatabase.query(Semester_Table, null, null, null, null, null,null);
        while(cursor.moveToNext())
        {
            Semester semester = new Semester();
            semester.setId(cursor.getInt(Semester_ID_Column));
            semester.setName(cursor.getString(Semester_Name_Column));
            semesters.add(semester);
        }
        closeCursor(cursor);
        closeDatabase();
        return semesters;
    }

    public Semester getSemester(String name)
    {
        String whereStatement = Semester_Name + "= ?";
        String[] whereArguments = {name};

        openReadableDB();
        Cursor cursor = SQLiteDatabase.query(Semester_Table, null, whereStatement, whereArguments, null, null, null);
        Semester semester = null;
        cursor.moveToFirst();
        semester = new Semester(cursor.getInt(Semester_ID_Column), cursor.getString(Semester_Name_Column));
        this.closeCursor(cursor);
        this.closeDatabase();
        return semester;
    }

    public ArrayList<Classes> getClasses()
    {
        ArrayList<Classes> classesList = new ArrayList<Classes>();
        openReadableDB();
        Cursor cursor = SQLiteDatabase.query(Class_Table, null, null, null, null, null, null);
        while(cursor.moveToNext())
        {
            Classes classses = new Classes();
            classses.setClassId(cursor.getInt(Class_ID_Column));
            classses.setName(cursor.getString(Class_Name_Column));
            classses.setClassWeight(cursor.getInt(Class_Weight_Column));
            classesList.add(classses);
        }
        closeCursor(cursor);
        closeDatabase();
        return classesList;
    }

    public Classes getClasses(String name)
    {
        String whereStatement = Class_Name + "= ?";
        String[] whereArguments = {name};

        openReadableDB();
        Cursor cursor = SQLiteDatabase.query(Class_Table, null, whereStatement, whereArguments, null, null, null);
        Classes classes = null;
        cursor.moveToFirst();
        classes = new Classes(cursor.getInt(Class_ID_Column), cursor.getString(Class_Name_Column), cursor.getInt(Class_Weight_Column));
        this.closeCursor(cursor);
        this.closeDatabase();
        return classes;
    }

    // Create tables
    public long createSemester(Semester semester)
    {
        ContentValues cv = new ContentValues();
        cv.put(Semester_ID, semester.getId());
        cv.put(Semester_Name, semester.getName());

        this.openWriteableDB();
        long rowID = SQLiteDatabase.insert(Semester_Table, null, cv);
        this.closeDatabase();

        return rowID;
    }

    public long createClass(Classes classesObject)
    {
        ContentValues cv = new ContentValues();
        cv.put(Class_ID, classesObject.getClassId());
        cv.put(Class_Name, classesObject.getName());
        cv.put(Class_weight, classesObject.getClassWeight());

        this.openWriteableDB();
        long rowID = SQLiteDatabase.insert(Class_Table, null, cv);
        this.closeDatabase();

        return rowID;
    }

    public long createGrade(Grade grade)
    {
        ContentValues cv = new ContentValues();
        cv.put(Grades_ID, grade.getGradeId());
        cv.put(Grades_Semester_ID, grade.getSemesterId());
        cv.put(Grades_Class_ID, grade.getClassId());
        cv.put(Final_Grade, grade.getFinalGrade());

        this.openWriteableDB();
        long rowID = SQLiteDatabase.insert(Grades_Table, null , cv);
        this.closeDatabase();

        return  rowID;
    }


}
