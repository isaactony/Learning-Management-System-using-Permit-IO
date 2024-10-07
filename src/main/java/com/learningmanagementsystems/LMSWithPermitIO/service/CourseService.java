package com.learningmanagementsystems.LMSWithPermitIO.service;

import com.learningmanagementsystems.LMSWithPermitIO.exception.ResourceNotFoundException;
import com.learningmanagementsystems.LMSWithPermitIO.model.Course;
import com.learningmanagementsystems.LMSWithPermitIO.repository.CourseRepository;
import io.permit.sdk.Permit;
import io.permit.sdk.api.PermitApiError;
import io.permit.sdk.enforcement.Resource;
import io.permit.sdk.enforcement.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;  // Use the CourseRepository for DB operations
    private final Permit permit;
    private final UserService userService;

    public CourseService(Permit permit, UserService userService, CourseRepository courseRepository) {
        this.permit = permit;
        this.userService = userService;
        this.courseRepository = courseRepository;
    }

    // Authorization method
    private void authorize(User user, String action, Resource resource) {
        userService.authorize(user, action, resource);
    }

    // Method to get all courses
    public List<Course> getAllCourses(User user) {
        authorize(user, "read", new Resource.Builder("course").build());
        return courseRepository.findAll();  // Retrieve all courses from the database
    }

    // Method to get a specific course by ID
    public Course getCourse(User user, int id) {
        Course course = courseRepository.findById((long) id)
                .orElseThrow(() -> new ResourceNotFoundException("Course with id " + id + " not found"));

        // Authorize user to read this specific course
        var attributes = new HashMap<String, Object>();
        attributes.put("id", id);
        authorize(user, "read", new Resource.Builder("course").withKey(String.valueOf(id)).withAttributes(attributes).build());

        return course;
    }

    // Course creation method
    public Course createCourse(User user, Course course) throws PermitApiError, IOException {
        // Check if the user has permission to create a course
        permit.check(user, "create", new Resource.Builder("course").build());

        // If the user has permission, proceed with saving the course to the database
        return courseRepository.save(course);
    }
}


