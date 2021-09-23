package com.example.schoolproject.Model;

public class User {

    String id;
    String gender;
    String first_Name;
    String last_Name;
    String regNo;
    String level;
    String faculty;
    String department;
    String post;
    String sport;
    String imageURL;

    public User() {
    }

    public User(String id, String gender, String first_Name, String last_Name, String regNo, String level, String faculty, String department, String post, String sport, String imageURL) {
        this.id = id;
        this.gender = gender;
        this.first_Name = first_Name;
        this.last_Name = last_Name;
        this.regNo = regNo;
        this.level = level;
        this.faculty = faculty;
        this.department = department;
        this.post = post;
        this.sport = sport;
        this.imageURL = imageURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFirst_Name() {
        return first_Name;
    }

    public void setFirst_Name(String first_Name) {
        this.first_Name = first_Name;
    }

    public String getLast_Name() {
        return last_Name;
    }

    public void setLast_Name(String last_Name) {
        this.last_Name = last_Name;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
