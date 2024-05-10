package com.skillstorm.Exceptions;

public class ResourceNotFoundException extends RuntimeException {
    /**
     * Exception thrown when a resource is not found.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}