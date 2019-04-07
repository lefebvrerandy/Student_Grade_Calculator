package com.example.randy_bence.student_grade_calculator;

public class Class {
    private int classId;
    private String name;
    private int classWeight;

    // Constructors
    public Class(int classId, int classWeight)
    {
      this.classId = classId;
      this.classWeight = classWeight;
    }

    public Class (int classId, String name, int classWeight)
    {
        this.classId = classId;
        this.classWeight = classWeight;
        this.name = name;
    }

    // Getters
    public int getClassId()
    {
        return classId;
    }

    public int getClassWeight()
    {
        return classWeight;
    }

    public String getName()
    {
        return name;
    }

    // Setters
    public void setClassId(int classId)
    {
        this.classId = classId;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public void setClassWeight(int classWeight)
    {
        this.classWeight = classWeight;
    }
}
