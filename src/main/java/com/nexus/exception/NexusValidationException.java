package com.nexus.exception;

public class NexusValidationException extends RuntimeException {
    public static int totalValidationErrors = 0;

    public NexusValidationException(String message) {
        super(message);
        totalValidationErrors++;
    }
}