package com.learningmanagementsystems.LMSWithPermitIO.model;

public class Grade {
    private Integer id;
    private Integer assignmentId;
    private String student;
    private String grade;

    public Grade(Integer id, Integer assignmentId, String student, String grade) {
        this.id = id;
        this.assignmentId = assignmentId;
        this.student = student;
        this.grade = grade;
    }

    public Integer getId() {
        return id;
    }

    public Integer getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Integer assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
