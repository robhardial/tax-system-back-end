package com.skillstorm.Exceptions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

class ResourceNotFoundExceptionTest {

    @Test
    void testExceptionMessage() {
        // Define the exception message
        String expectedMessage = "Resource not found";

        // Assert that the exception has the correct message
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            throw new ResourceNotFoundException(expectedMessage);
        });

        // Verify that the message is as expected
        assertEquals(expectedMessage, exception.getMessage(), "The exception message should match the expected message.");
    }
}
