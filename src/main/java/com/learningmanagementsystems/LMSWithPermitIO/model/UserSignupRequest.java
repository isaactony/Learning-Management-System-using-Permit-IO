package com.learningmanagementsystems.LMSWithPermitIO.model;

import java.util.HashMap;
import java.util.Map;

public class UserSignupRequest {
    private String key;
    private String email;
    private String firstName;
    private String lastName;
    private Map<String, Object> attributes;

    // Default constructor
    public UserSignupRequest() {}

    // Constructor with all fields
    public UserSignupRequest(String key, String email, String firstName, String lastName, Map<String, Object> attributes) {
        this.key = key;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.attributes = attributes;
    }

    // Getters and setters
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public HashMap<String, Object> getAttributes() {
        return (HashMap<String, Object>) attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "UserSignupRequest{" +
                "key='" + key + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", attributes=" + attributes +
                '}';
    }
}
