package com.learningmanagementsystems.LMSWithPermitIO.model;


public class Enrollment {
    private Integer id;
    private String student;
    private Integer courseId;

    public Enrollment(Integer id, String student, Integer courseId) {
        this.id = id;
        this.student = student;
        this.courseId = courseId;
    }

    public Integer getId() {
        return id;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    private Course course;  // Add this field

    // Existing fields (e.g., student, date, etc.)

    // Getter for course
    public Course getCourse() {
        return course;
    }

    // Setter for course
    public void setCourse(Course course) {
        this.course = course;
    }
}

