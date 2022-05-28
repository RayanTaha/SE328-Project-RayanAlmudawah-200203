package com.example.myapplication;

public class Student {
    private String ID;
    private String name;
    private String surname;
    private String fatherName;
    private String nationalID;
    private String dateOfBirth;
    private String Gender;

    public Student(String ID, String name, String surname, String fatherName, String nationalID, String dateOfBirth, String gender) {
        this.ID = ID;
        this.name = name;
        this.surname = surname;
        this.fatherName = fatherName;
        this.nationalID = nationalID;
        this.dateOfBirth = dateOfBirth;
        this.Gender = gender;
    }
    public Student() {
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public void setNationalID(String nationalID) {
        this.nationalID = nationalID;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getID() {
        return ID;
    }
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public String getFatherName() {
        return fatherName;
    }
    public String getNationalID() {
        return nationalID;
    }
    public String getDateOfBirth() {
        return dateOfBirth;
    }
    public String getGender() {
        return Gender;
    }


}
