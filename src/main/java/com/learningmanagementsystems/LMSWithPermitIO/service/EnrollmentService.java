package com.learningmanagementsystems.LMSWithPermitIO.service;

import com.learningmanagementsystems.LMSWithPermitIO.exception.ResourceNotFoundException;
import com.learningmanagementsystems.LMSWithPermitIO.model.Course;
import com.learningmanagementsystems.LMSWithPermitIO.model.Enrollment;
import com.learningmanagementsystems.LMSWithPermitIO.repository.CourseRepository;
import io.permit.sdk.Permit;
import io.permit.sdk.api.PermitApiError;
import io.permit.sdk.enforcement.Resource;
import io.permit.sdk.enforcement.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {

    private final List<Enrollment> enrollments = new ArrayList<>();
    private final Permit permit;
    private final CourseRepository courseRepository;  // Inject the course repository

    @Autowired
    public EnrollmentService(Permit permit, CourseRepository courseRepository) {
        this.permit = permit;
        this.courseRepository = courseRepository;
    }

    public Enrollment enrollStudent(User user, Long courseId, Enrollment enrollment) throws PermitApiError, IOException {
        // Check if the course exists
        Optional<Course> course = courseRepository.findById(courseId);
        if (!course.isPresent()) {
            throw new ResourceNotFoundException("Course not found with ID: " + courseId);
        }

        // Check if the user has permission to create an enrollment
        permit.check(user, "create", new Resource.Builder("enrollment").build());

        // Add course to enrollment and save the enrollment
        enrollment.setCourse(course.get());  // Assign the course to the enrollment
        enrollments.add(enrollment);
        return enrollment;
    }
}

