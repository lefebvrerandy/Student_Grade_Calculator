package com.example.randy_bence.student_grade_calculator;

public class Grade {

    private  int gradeId;
    private int semesterId;
    private String classNAme;
    private int classWeight;
    private double finalGrade;

    // Constructor
    public Grade()
    {

    }
    public Grade(int gradeId, int semesterId, String classNAme, int classWeight, double finalGrade)
    {
        this.gradeId = gradeId;
        this.semesterId = semesterId;
        this.classNAme = classNAme;
        this.classWeight = classWeight;
        this.finalGrade = finalGrade;
    }

    // Getters
    public int getGradeId()
    {
        return gradeId;
    }

    public int getSemesterId()
    {
        return semesterId;
    }

    public String getClassNAme()
    {
        return classNAme;
    }

    public int getClassWeight()
    {
        return classWeight;
    }

    public double getFinalGrade()
    {
        return  finalGrade;
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

    public void setClassNAme (String ClassName)
    {
        this.classNAme = classNAme;
    }

    public void setClassWeight (int classWeight)
    {
        this.classWeight = classWeight;
    }

    public void setFinalGrade(double finalGrade)
    {
        this.finalGrade = finalGrade;
    }
}
