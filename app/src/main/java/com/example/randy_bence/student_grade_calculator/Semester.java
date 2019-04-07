package com.example.randy_bence.student_grade_calculator;

public class Semester {
    private int id;
    private String name;

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

    // Getters
    public int getId()
    {
        return id;
    }
    public String getName()
    {
        return name;
    }
}
