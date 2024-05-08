package com.skillstorm.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class UserTest {

    private User user;

    @Mock
    private Person person;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setEmail("test@example.com");
        user.setPassword("123456");
        user.setPerson(person);
    }

    @Test
    public void testGetEmail() {
        assertEquals("test@example.com", user.getEmail());
    }

    @Test
    public void testSetEmail() {
        user.setEmail("change@example.com");
        assertEquals("change@example.com", user.getEmail());
    }

    @Test
    public void testGetPassword() {
        assertEquals("123456", user.getPassword());
    }

    @Test
    public void testSetPassword() {
        user.setPassword("newpassword");
        assertEquals("newpassword", user.getPassword());
    }

    @Test
    public void testGetPerson() {
        assertEquals(person, user.getPerson());
    }

    @Test
    public void testSetPerson() {
        Person newPerson = mock(Person.class);
        user.setPerson(newPerson);
        assertEquals(newPerson, user.getPerson());
    }
}