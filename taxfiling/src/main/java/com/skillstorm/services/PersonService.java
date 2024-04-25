package com.skillstorm.services;

import com.skillstorm.models.Person;
import com.skillstorm.models.User;
import com.skillstorm.respositories.PersonRepository;
import com.skillstorm.respositories.UserRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    UserRepository userRepository;

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
