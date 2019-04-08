package com.example.randy_bence.student_grade_calculator;

public class Semester {
    private int id;
    private String name;
    private double GPA;

    // Constructors
    public  Semester()
    {

    }

    public Semester(String name)
    {
        this.name = name;
    }

    public Semester(int id, String name)
    {
        this.name = name;
        this.id = id;
    }

    // Setters
    public void setId(int id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setGPA(double GPA)
    {
        this.GPA = GPA;
    }

    // Getters
    public int getId()
    {
        return id;
    }
    public String getName()
    {
        return name;
    }
    public double getGPA()
    {
        return GPA;
    }
}
