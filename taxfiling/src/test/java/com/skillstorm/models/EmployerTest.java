package com.skillstorm.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.Collections;
import java.util.List;

class EmployerTest {

    @Test
    void testNoArgsConstructor() {
        // Using no-args constructor
        Employer employer = new Employer();
        assertAll("Default constructor should initialize fields to default",
                () -> assertEquals(0, employer.getId(), "Default id should be 0"),
                () -> assertNull(employer.getEin(), "Default EIN should be null"),
                () -> assertNull(employer.getName(), "Default name should be null"),
                () -> assertNull(employer.getAddress(), "Default address should be null"),
                () -> assertNull(employer.getFormW2s(), "Default formW2s should be null")
        );
    }

    @Test
    void testAllArgsConstructor() {
        // Using all-args constructor
        List<FormW2> formW2s = Collections.emptyList();  // Assuming empty list for simplicity
        Employer employer = new Employer(1, "123456789", "Skillstorm", "123 Main St", formW2s);

        assertAll("All-args constructor should set fields correctly",
                () -> assertEquals(1, employer.getId()),
                () -> assertEquals("123456789", employer.getEin()),
                () -> assertEquals("Skillstorm", employer.getName()),
                () -> assertEquals("123 Main St", employer.getAddress()),
                () -> assertSame(formW2s, employer.getFormW2s(), "Should reference the same list object")
        );
    }

    @Test
    void testSettersAndGetters() {
        // Testing setters and getters
        Employer employer = new Employer();
        employer.setId(2);
        employer.setEin("987654321");
        employer.setName("Stormskills");
        employer.setAddress("321 Side St");
        List<FormW2> formW2s = Collections.singletonList(new FormW2());
        employer.setFormW2s(formW2s);

        assertAll("Setters and getters should function correctly",
                () -> assertEquals(2, employer.getId()),
                () -> assertEquals("987654321", employer.getEin()),
                () -> assertEquals("Stormskills", employer.getName()),
                () -> assertEquals("321 Side St", employer.getAddress()),
                () -> assertSame(formW2s, employer.getFormW2s(), "Should reference the same list object")
        );
    }
}
