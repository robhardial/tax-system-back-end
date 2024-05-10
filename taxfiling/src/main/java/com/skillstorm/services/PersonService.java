package com.skillstorm.services;

import com.skillstorm.models.Person;
import com.skillstorm.models.TaxReturn;
import com.skillstorm.models.User;
import com.skillstorm.respositories.PersonRepository;
import com.skillstorm.respositories.UserRepository;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    /**
     * Creates a new person with the given authentication principal.
     *
     * @param user the authentication principal of the user
     * @return the created person object
     */
    public Person createPersonWithToken(@AuthenticationPrincipal OAuth2User user) {

        User retrievedUser = userService.createUser(user);
        Person retrievedPerson = personRepository.findByUser(retrievedUser);

        if (retrievedPerson != null) {
            return retrievedPerson;
        }

        Person newPerson = new Person();
        Map<String, Object> userInfo = user.getAttributes();
        System.out.println(userInfo);
        String userFirstName = (String) userInfo.get("given_name");
        String userLastName = (String) userInfo.get("family_name");
        int userSsn = 00000000;

        newPerson.setUser(retrievedUser);
        newPerson.setFirstName(userFirstName);
        newPerson.setLastName(userLastName);
        newPerson.setSsn(userSsn);

        return personRepository.save(newPerson);
    }

    /**
     * Retrieves the Person associated with the given OAuth2User token.
     *
     * @param user the OAuth2User token representing the authenticated user
     * @return the Person object associated with the user token
     */
    public Person getPersonWithToken(@AuthenticationPrincipal OAuth2User user) {

        User retrievedUser = userService.createUser(user);

        return personRepository.findByUser(retrievedUser);
    }

    /**
     * Retrieves all the persons from the person repository.
     *
     * @return a list of Person objects representing all the persons found
     */
    public List<Person> findAllPersons() {
        return personRepository.findAll();
    }

    /**
     * Finds a person with the specified ID.
     *
     * @param id the ID of the person to find
     * @return the person with the specified ID, or null if not found
     */
    public Person findPersonById(int id) {
        Optional<Person> person = personRepository.findById(id);

        if (person.isPresent()) {
            return person.get();
        }

        return null;
    }

    /**
     * Retrieves the tax returns associated with a person based on their ID.
     *
     * @param personId the ID of the person
     * @return a list of TaxReturn objects associated with the person, or an empty list if the person is not found
     */
    public List<TaxReturn> findTaxReturnsByPersonId(int personId) {
        Optional<Person> person = personRepository.findById(personId);
        if (person.isPresent()) {
            return person.get().getTaxReturns();
        }
        return new ArrayList<>();
    }

    /**
     * Saves a Person object by creating or updating the associated User object.
     *
     * @param person The Person object to be saved.
     * @return The saved Person object.
     */
    public Person savePerson(Person person) {

        User retrievedUser = person.getUser();
        User existingUserOptional = userRepository.findByEmail(retrievedUser.getEmail());

        if (existingUserOptional == null) {
            // If the user doesn't exist, create and save it
            User newUser = new User();
            newUser.setEmail(retrievedUser.getEmail());
            newUser.setPassword(retrievedUser.getPassword());

            // Save the new User object
            existingUserOptional = userRepository.save(newUser);
        }

        person.setUser(existingUserOptional);

        return personRepository.save(person);
    }

    /**
     * Edits the details of a person with the given ID.
     *
     * @param id the ID of the person to edit
     * @param person the updated Person object containing the new details
     * @return the updated Person object
     */
    public Person editPerson(int id, Person person) {
        System.out.println("Received person for edit: " + person);
        Optional<Person> existingPersonOptional = personRepository.findById(id);

        if (existingPersonOptional.isPresent()) {
            Person existingPerson = existingPersonOptional.get();
            System.out.println("Existing person found: " + existingPerson);

            if (person.getSsn() != 0) {
                existingPerson.setSsn(person.getSsn());
            }

            if (person.getFirstName() != null) {
                existingPerson.setFirstName(person.getFirstName());
            }

            if (person.getMiddleName() != null) {
                existingPerson.setMiddleName(person.getMiddleName());
            }

            if (person.getLastName() != null) {
                existingPerson.setLastName(person.getLastName());
            }

            if (person.getAddress() != null) {
                existingPerson.setAddress(person.getAddress());
            }
            if (person.getPhoneNumber() != null) {
                existingPerson.setPhoneNumber(person.getPhoneNumber());
            }
            if (person.getDateOfBirth() != null) {
                existingPerson.setDateOfBirth(person.getDateOfBirth());
            }

            return personRepository.save(existingPerson);
        } else {
            System.out.println("No existing person found with ID: " + id);
            return personRepository.save(person);
        }
    }

    /**
     * Deletes a person from the database by their ID.
     *
     * @param id the ID of the person to be deleted
     */
    public void deletePersonById(int id) {
        personRepository.deleteByPersonId(id);
    }
}
