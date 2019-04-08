package com.example.randy_bence.student_grade_calculator;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.os.strictmode.SqliteObjectLeakedViolation;
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

    public static final String GPA = "GAP";
    public static final int GPA_Column = 2;

    // Grades table constants
    public static final String Grades_Table = "Grades";

    public static final String Grade_ID = "GradeID";
    public static final int Grade_ID_Column = 0;

    public static final String Grades_Semester_ID = "SemesterID";
    public static final int Grades_Semester_ID_Column = 1;

    public static final String Class_Name= "ClassName";
    public static final int Class_Name_Column = 2;

    public static final String Class_Weight = "ClassWeight";
    public static final int Class_Weight_Column = 3;

    public static final String Final_Grade = "FinalGrade";
    public static final int Final_Grade_Column = 4;

    // CREATE TABLE Statements
    public static final String Create_Semester_Table =
            "CREATE TABLE " + Semester_Table  + " (" +
                    Semester_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Semester_Name + " TEXT NOT NULL UNIQUE, " +
                    GPA + "REAL); ";

    public static final String Create_Grades_Table =
            "CREATE TABLE " + Grades_Table + " (" +
                    Grade_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                   Grades_Semester_ID + " INTEGER NOT NULL, " +
                    Class_Name + " TEXT, " +
                    Class_Weight + " INTEGER NOT NULL, " +
                    Final_Grade + " REAL NOT NULL);";

    // DROP TABLE Statments
    public static final String Drop_Semester_Table =
            "DROP TABLE IF EXISTS " + Semester_Table;


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
            sqLiteDatabase.execSQL(Create_Grades_Table);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            Log.d("Student Grade Calculator", "Upgrading database from version "
                    + oldVersion + " to " + newVersion);
            sqLiteDatabase.execSQL(GradesDatabase.Drop_Semester_Table);
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
            semester.setGPA(cursor.getDouble(GPA_Column));
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


    public Cursor getGradeCursor (String SemesterName)
    {
        String whereStatement = Grades_Semester_ID + " = ?";
        int semesterID = getSemester(SemesterName).getId();

        String[] whereArguments = {Integer.toString(semesterID)};

        this.openReadableDB();
        Cursor cursor = SQLiteDatabase.query(Grades_Table, null, whereStatement, whereArguments, null, null, null);
        return cursor;
    }

    public ArrayList<Grade> getGrades(String SemesterName)
    {
        String whereStatement = Grades_Semester_ID + " = ?";
        int semesterID = getSemester(SemesterName).getId();

        String[] whereArguments = {Integer.toString(semesterID)};

        this.openReadableDB();
        Cursor cursor = SQLiteDatabase.query(Grades_Table, null, whereStatement, whereArguments, null, null, null);

        ArrayList<Grade> grades = new ArrayList<Grade>();
        while(cursor.moveToNext())
        {
            grades.add(getGradeFromCursor(cursor));
        }
        this.closeCursor(cursor);
        this.closeDatabase();

        return grades;
    }

    public Grade getGrade(int id)
    {
        String whereStatement = Grade_ID + " = ?";
        String[] whereArguments = {Integer.toString(id)};

        this.openReadableDB();
        Cursor cursor = SQLiteDatabase.query(Grades_Table, null, whereStatement, whereArguments, null, null, null);
        cursor.moveToFirst();
        Grade grade = getGradeFromCursor(cursor);
        this.closeCursor(cursor);
        this.closeDatabase();
        return grade;
    }

    private static Grade getGradeFromCursor (Cursor cursor)
    {
        if (cursor == null || cursor.getCount() == 0)
        {
            return null;
        }
        else {
            try {
                Grade grade = new Grade(cursor.getInt(Grade_ID_Column),
                        cursor.getInt(Grades_Semester_ID_Column),
                        cursor.getString(Class_Name_Column),
                        cursor.getInt(Class_Weight_Column),
                        cursor.getDouble(Final_Grade_Column));
                return grade;
            }
            catch (Exception e) {
                return null;
            }
        }
    }

    // INSERT INTO tables
    public long insertIntoSemester(Semester semester)
    {
        long rowID;
        if (CheckSemesterRecord(semester.getName()))
        {
            rowID = updateSemester(semester);
        }
        else
            {
            ContentValues cv = new ContentValues();
            cv.put(Semester_ID, semester.getId());
            cv.put(Semester_Name, semester.getName());
            cv.put(GPA, semester.getGPA());

            this.openWriteableDB();
           rowID = SQLiteDatabase.insert(Semester_Table, null, cv);
            this.closeDatabase();
        }

        return rowID;
    }


    public long insertIntoGrade(Grade grade)
    {
        long rowID;
        if(CheckGradeRecord(grade.getClassNAme()))
        {
            rowID = updateGrade(grade);
        }
        else {
            ContentValues cv = new ContentValues();
            cv.put(Grade_ID, grade.getGradeId());
            cv.put(Grades_Semester_ID, grade.getSemesterId());
            cv.put(Class_Name, grade.getClassNAme());
            cv.put(Class_Weight, grade.getClassWeight());
            cv.put(Final_Grade, grade.getFinalGrade());

            this.openWriteableDB();
            rowID = SQLiteDatabase.insert(Grades_Table, null, cv);
            this.closeDatabase();
        }
        return  rowID;
    }

    public int updateSemester (Semester semester)
    {
        ContentValues cv = new ContentValues();
        cv.put(Semester_ID, semester.getId());
        cv.put(Semester_Name, semester.getName());
        cv.put(GPA, semester.getGPA());

        String whereStatement = Semester_Table + " = ?";
        String[] whereArguments = {String.valueOf(semester.getId())};

        this.openWriteableDB();
        int rowCount = SQLiteDatabase.update(Semester_Table, cv, whereStatement, whereArguments);
        this.closeDatabase();

        return rowCount;
    }

    public int updateGrade (Grade grade)
    {
        ContentValues cv = new ContentValues();
        cv.put(Grade_ID, grade.getGradeId());
        cv.put(Grades_Semester_ID, grade.getSemesterId());
        cv.put(Class_Name, grade.getClassNAme());
        cv.put(Class_Weight, grade.getClassWeight());
        cv.put(Final_Grade, grade.getFinalGrade());

        String whereStatement = Grades_Table + " = ? ";
        String[] whereArguments = {String.valueOf(grade.getGradeId())};

        this.openWriteableDB();
        int rowCount = SQLiteDatabase.update(Grades_Table, cv, whereStatement, whereArguments);
        this.closeDatabase();

        return rowCount;
    }

    public int deleteSemester(int id)
    {
        String whereStatement = Semester_ID + " = ? ";
        String[] whereArguments = { String.valueOf(id)};

        this.openWriteableDB();
        int rowCount = SQLiteDatabase.delete(Semester_Table, whereStatement, whereArguments);
        this.closeDatabase();

        return rowCount;
    }

    public int deleteGrade(int id)
    {
        String whereStatement = Grade_ID + " = ? ";
        String[] whereArguments = {String.valueOf(id)};

        this.openWriteableDB();
        int rowCount = SQLiteDatabase.delete(Grades_Table, whereStatement, whereArguments);
        this.closeDatabase();

        return rowCount;
    }

    public boolean CheckSemesterRecord(String semesterName)
    {
        String whereStatement = Semester_Name + " = ?";
        String[] whereArguments = { semesterName};

        this.openWriteableDB();
        Cursor cursor = SQLiteDatabase.query(Semester_Table, null, whereStatement, whereArguments, null, null, null);
        boolean exists = (cursor.getCount() > 0);
        this.closeCursor(cursor);
        this.closeDatabase();

        return exists;
    }

    public  boolean CheckGradeRecord(String className)
    {
        String whereStatement = Grade_ID + " = ?";
        String[] whereArguments = {className};

        this.openWriteableDB();
        Cursor cursor = SQLiteDatabase.query(Grades_Table, null, whereStatement, whereArguments, null, null, null);
        boolean exists = (cursor.getCount() > 0);
        this.closeCursor(cursor);
        this.closeDatabase();

        return exists;
    }
}
