package com.learningmanagementsystems.LMSWithPermitIO.controller;

import com.learningmanagementsystems.LMSWithPermitIO.model.Enrollment;
import com.learningmanagementsystems.LMSWithPermitIO.service.EnrollmentService;
import io.permit.sdk.api.PermitApiError;
import io.permit.sdk.enforcement.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/{courseId}")
    public Enrollment enrollStudent(HttpServletRequest request, @PathVariable Long courseId, @RequestBody Enrollment enrollment) throws PermitApiError, IOException {
        User currentUser = (User) request.getAttribute("user");
        return enrollmentService.enrollStudent(currentUser, courseId, enrollment);
    }
}

