package com.example.randy_bence.student_grade_calculator;

public class Grade {

    private int gradeId;
    private int semesterId;
    private int classId;
    private double finalGrade;

    // Constructor
    public Grade(int gradeId, int semesterId, int classId, double finalGrade)
    {
        this.gradeId = gradeId;
        this.semesterId = semesterId;
        this.classId = classId;
        this.finalGrade = finalGrade;
    }

    // Getter
    public int getGradeId()
    {
        return  gradeId;
    }

    public int getSemesterId()
    {
        return semesterId;
    }

    public int getClassId()
    {
        return  classId;
    }

    public double getFinalGrade()
    {
        return finalGrade;
    }

    // Setters
    public void setGradeId(int gradeId)
    {
        this.gradeId = gradeId;
    }

    public void setSemesterId(int semesterId)
    {
        this.semesterId = semesterId;
    }

    public void setClassId(int classId)
    {
        this.classId = classId;
    }

    public void setFinalGrade(double finalGrade)
    {
        this.finalGrade = finalGrade;
    }
}
