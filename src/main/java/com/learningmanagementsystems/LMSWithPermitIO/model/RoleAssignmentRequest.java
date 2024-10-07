package com.learningmanagementsystems.LMSWithPermitIO.model;

import io.permit.sdk.enforcement.User;

public class RoleAssignmentRequest {
    private String userKey;
    private String role;

    // Getters and setters
    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
