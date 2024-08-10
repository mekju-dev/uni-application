package com.example.c196.ui.courses;

public class Course {
    String title;
    String status;
    String instructor;
    String cIPhone;
    String cIEmail;
    String id;
    String termAssociated;
    String startDate;
    String endDate;
    String note;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getcIPhone() {
        return cIPhone;
    }

    public void setcIPhone(String cIPhone) {
        this.cIPhone = cIPhone;
    }

    public String getcIEmail() {
        return cIEmail;
    }

    public void setcIEmail(String cIEmail) {
        this.cIEmail = cIEmail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTermAssociated() {
        return termAssociated;
    }

    public void setTermAssociated(String termAssociated) {
        this.termAssociated = termAssociated;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Course(String id, String title, String status, String instructor, String cIPhone, String cIEmail, String termAssociated, String startDate, String endDate, String note) {
        this.title = title;
        this.status = status;
        this.instructor = instructor;
        this.cIPhone = cIPhone;
        this.cIEmail = cIEmail;
        this.id = id;
        this.termAssociated = termAssociated;
        this.startDate = startDate;
        this.endDate = endDate;
        this.note = note;
    }
}
