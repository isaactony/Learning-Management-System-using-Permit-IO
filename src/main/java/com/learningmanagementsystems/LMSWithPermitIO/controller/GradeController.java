package com.learningmanagementsystems.LMSWithPermitIO.controller;

import com.learningmanagementsystems.LMSWithPermitIO.model.Grade;
import com.learningmanagementsystems.LMSWithPermitIO.service.GradeService;
import io.permit.sdk.api.PermitApiError;
import io.permit.sdk.enforcement.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/grades")
public class GradeController {
    private final GradeService gradeService;

    @Autowired
    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @PostMapping
    public Grade submitGrade(HttpServletRequest request, @RequestBody Grade grade) throws PermitApiError, IOException {
        User currentUser = (User) request.getAttribute("user");
        return gradeService.submitGrade(currentUser, grade);
    }
}

