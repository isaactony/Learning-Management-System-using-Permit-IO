package com.learningmanagementsystems.LMSWithPermitIO.service;

import com.learningmanagementsystems.LMSWithPermitIO.model.Grade;
import io.permit.sdk.Permit;
import io.permit.sdk.api.PermitApiError;
import io.permit.sdk.enforcement.Resource;
import io.permit.sdk.enforcement.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GradeService {
    private final List<Grade> grades = new ArrayList<>();
    private final Permit permit;

    public GradeService(Permit permit) {
        this.permit = permit;
    }

    public Grade submitGrade(User user, Grade grade) throws PermitApiError, IOException {
        permit.check(user, "grade", new Resource.Builder("grade").build());
        grades.add(grade);
        return grade;
    }
}

