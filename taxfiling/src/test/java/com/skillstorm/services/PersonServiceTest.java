package com.skillstorm.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.skillstorm.models.Person;
import com.skillstorm.models.TaxReturn;
import com.skillstorm.models.User;
import java.util.Collections;
import java.util.Optional;
import java.util.List;
import java.util.Map;

import com.skillstorm.respositories.PersonRepository;
import com.skillstorm.respositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private PersonService personService;


    @Test
    void testCreatePersonWithToken_NewUser() {
        OAuth2User oAuth2User = mock(DefaultOAuth2User.class);
        User user = new User();
        Person newPerson = new Person();

        when(oAuth2User.getAttributes()).thenReturn(Map.of("given_name", "Jane", "family_name", "Doe"));
        when(userService.createUser(oAuth2User)).thenReturn(user);
        when(personRepository.findByUser(user)).thenReturn(null);
        when(personRepository.save(any(Person.class))).thenReturn(newPerson);

        Person result = personService.createPersonWithToken(oAuth2User);

        assertNotNull(result, "Should create and return a new person");
    }

    @Test
    void testGetPersonWithToken() {
        OAuth2User oAuth2User = mock(DefaultOAuth2User.class);
        User user = new User();
        Person person = new Person();

        when(userService.createUser(oAuth2User)).thenReturn(user);
        when(personRepository.findByUser(user)).thenReturn(person);

        Person result = personService.getPersonWithToken(oAuth2User);

        assertSame(person, result, "Should return the person associated with the user");
    }

    @Test
    void testFindPersonById_Found() {
        int id = 1;
        Person person = new Person();
        when(personRepository.findById(id)).thenReturn(Optional.of(person));

        Person result = personService.findPersonById(id);

        assertSame(person, result, "Should return the found person");
    }

    @Test
    void testFindPersonById_NotFound() {
        int id = 1;
        when(personRepository.findById(id)).thenReturn(Optional.empty());

        Person result = personService.findPersonById(id);

        assertNull(result, "Should return null when no person is found");
    }

    // Additional tests for other methods would be similarly structured...
}
