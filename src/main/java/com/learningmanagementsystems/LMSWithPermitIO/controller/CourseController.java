package com.learningmanagementsystems.LMSWithPermitIO.controller;
import com.learningmanagementsystems.LMSWithPermitIO.model.Course;
import com.learningmanagementsystems.LMSWithPermitIO.service.CourseService;
import io.permit.sdk.api.PermitApiError;
import io.permit.sdk.enforcement.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // Get all courses
    @GetMapping
    public List<Course> getAllCourses(HttpServletRequest request) throws PermitApiError, IOException {
        User currentUser = (User) request.getAttribute("user");
        return courseService.getAllCourses(currentUser);
    }

    // Get a specific course by ID
    @GetMapping("/{id}")
    public Course getCourse(HttpServletRequest request, @PathVariable int id) throws PermitApiError, IOException {
        User currentUser = (User) request.getAttribute("user");
        return courseService.getCourse(currentUser, id);
    }

    // Create a new course
    @PostMapping
    public Course createCourse(HttpServletRequest request, @RequestBody Course course) throws PermitApiError, IOException {
        User currentUser = (User) request.getAttribute("user");
        return courseService.createCourse(currentUser, course);
    }
}
