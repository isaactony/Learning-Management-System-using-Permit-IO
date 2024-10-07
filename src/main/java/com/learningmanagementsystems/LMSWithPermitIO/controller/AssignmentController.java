package com.learningmanagementsystems.LMSWithPermitIO.controller;

import com.learningmanagementsystems.LMSWithPermitIO.model.Assignment;
import com.learningmanagementsystems.LMSWithPermitIO.service.AssignmentService;
import io.permit.sdk.api.PermitApiError;
import io.permit.sdk.enforcement.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {
    private final AssignmentService assignmentService;

    @Autowired
    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @PostMapping
    public Assignment submitAssignment(HttpServletRequest request, @RequestBody Assignment assignment) throws PermitApiError, IOException {
        User currentUser = (User) request.getAttribute("user");
        return assignmentService.submitAssignment(currentUser, assignment);
    }
}


