package com.learningmanagementsystems.LMSWithPermitIO.model;

public class Assignment {
    private Integer id;
    private Integer courseId;
    private String student;
    private String content;

    public Assignment(Integer id, Integer courseId, String student, String content) {
        this.id = id;
        this.courseId = courseId;
        this.student = student;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

