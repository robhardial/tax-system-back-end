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

    public Person getPersonWithToken(@AuthenticationPrincipal OAuth2User user) {

        User retrievedUser = userService.createUser(user);

        return personRepository.findByUser(retrievedUser);
    }

    public List<Person> findAllPersons() {
        return personRepository.findAll();
    }

    public Person findPersonById(int id) {
        Optional<Person> person = personRepository.findById(id);

        if (person.isPresent()) {
            return person.get();
        }

        return null;
    }

    /**
     * Finds the tax returns associated with a person based on their ID.
     *
     * @param personId the ID of the person
     * @return a list of TaxReturn objects associated with the person, or an empty
     *         list if the person is not found
     */
    public List<TaxReturn> findTaxReturnsByPersonId(int personId) {
        Optional<Person> person = personRepository.findById(personId);
        if (person.isPresent()) {
            return person.get().getTaxReturns();
        }
        return new ArrayList<>();
    }

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

    public Person editPerson(int id, Person person) {
        Optional<Person> existingPersonOptional = personRepository.findById(id);

        if (existingPersonOptional.isPresent()) {
            Person existingPerson = existingPersonOptional.get();

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

            return personRepository.save(existingPerson);
        } else {
            return personRepository.save(person);
        }
    }

    public void deletePersonById(int id) {
        personRepository.deleteByPersonId(id);
    }
}
