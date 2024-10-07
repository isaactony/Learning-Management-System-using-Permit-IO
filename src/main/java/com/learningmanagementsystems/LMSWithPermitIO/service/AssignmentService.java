package com.learningmanagementsystems.LMSWithPermitIO.service;

import com.learningmanagementsystems.LMSWithPermitIO.exception.ForbiddenAccessException;
import com.learningmanagementsystems.LMSWithPermitIO.model.Assignment;
import io.permit.sdk.Permit;
import io.permit.sdk.api.PermitApiError;
import io.permit.sdk.enforcement.Resource;
import io.permit.sdk.enforcement.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AssignmentService {
    private final List<Assignment> assignments = new ArrayList<>();
    private final Permit permit;

    public AssignmentService(Permit permit) {
        this.permit = permit;
    }

    public Assignment submitAssignment(User user, Assignment assignment) throws PermitApiError, IOException {
        // Use Permit.io to check if the student is allowed to submit for this course
        HashMap<String, Object> resourceAttributes = new HashMap<>();
        resourceAttributes.put("courseId", assignment.getCourseId());

        // Check with Permit if user is allowed to submit assignment
        boolean isPermitted = permit.check(user, "submit", new Resource.Builder("assignment").withAttributes(resourceAttributes).build());

        if (!isPermitted) {
            throw new ForbiddenAccessException("You are not enrolled in this course.");
        }

        assignments.add(assignment);
        return assignment;
    }
}


