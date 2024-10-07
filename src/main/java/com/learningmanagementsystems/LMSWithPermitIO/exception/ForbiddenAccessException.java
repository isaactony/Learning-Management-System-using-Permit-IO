package com.learningmanagementsystems.LMSWithPermitIO.exception;


public class ForbiddenAccessException extends RuntimeException {
    public ForbiddenAccessException(String message) {
        super(message);
    }
}

