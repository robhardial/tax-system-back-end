package com.skillstorm.controllers;

import com.skillstorm.models.Person;
import com.skillstorm.models.TaxReturn;
import com.skillstorm.services.PersonService;

import java.util.List;

import com.skillstorm.services.TaxReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/persons")
@CrossOrigin("http://localhost:5173")
public class PersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    private TaxReturnService taxReturnService;

    /**
     * Creates a new person with the given authentication principal.
     *
     * @param person the Person object to be created
     * @param user the authentication principal of the user
     * @return the ResponseEntity<Person> object containing the created person and the HTTP status code
     */
    @PostMapping("/tokenPerson")
    public ResponseEntity<Person> createPersonWithToken(@RequestBody Person person,
            @AuthenticationPrincipal OAuth2User user) {

        Person newPerson = personService.createPersonWithToken(user);
        return new ResponseEntity<Person>(newPerson, HttpStatus.CREATED);
    }

    /**
     * Retrieves the Person associated with the given OAuth2User token.
     *
     * @param user the OAuth2User token representing the authenticated user
     * @return the Person object associated with the user token
     */
    @GetMapping("/tokenPerson")
    public ResponseEntity<Person> getPersonWithToken(@AuthenticationPrincipal OAuth2User user) {

        Person retrievedPerson = personService.getPersonWithToken(user);
        return new ResponseEntity<Person>(retrievedPerson, HttpStatus.CREATED);
    }

    /**
     * Retrieves all the persons from the person repository.
     *
     * @return a ResponseEntity with a list of Person objects representing all the persons found and HttpStatus.OK
     */
    @GetMapping
    public ResponseEntity<List<Person>> findAllPersons() {
        List<Person> persons = personService.findAllPersons();
        return new ResponseEntity<List<Person>>(persons, HttpStatus.OK);
    }

    /**
     * Retrieves a person from the database by their ID.
     *
     * @param id the ID of the person to retrieve
     * @return a ResponseEntity containing the person with the specified ID and the HTTP status code
     */
    @GetMapping("/person/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable int id) {
        Person person = personService.findPersonById(id);
        return new ResponseEntity<Person>(person, HttpStatus.OK);
    }

    /**
     * Retrieves the tax returns associated with a person based on their ID.
     *
     * @param id the ID of the person
     * @return a ResponseEntity containing a list of TaxReturn objects associated with the person,
     *         or a ResponseEntity with HttpStatus.NOT_FOUND if the person is not found
     */
    @GetMapping("/{id}/tax-returns")
    public ResponseEntity<List<TaxReturn>> getPersonTaxReturns(@PathVariable int id) {
        List<TaxReturn> taxReturns = personService.findTaxReturnsByPersonId(id);
        if (taxReturns != null && !taxReturns.isEmpty()) {
            return new ResponseEntity<>(taxReturns, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Retrieves the person associated with a given tax return ID.
     *
     * @param taxReturnId the ID of the tax return
     * @return the person associated with the tax return, or null if not found
     */
    @GetMapping("/{taxReturnId}/person")
    public ResponseEntity<Person> getPersonByTaxReturnId(@PathVariable int taxReturnId){
        Person person = taxReturnService.getPersonByTaxReturnId(taxReturnId);

        if(person != null){
            return ResponseEntity.ok(person);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Creates a new person by saving the Person object in the person repository.
     *
     * @param person The Person object to be created.
     * @return A ResponseEntity object containing the created Person object and the HTTP status CREATED.
     */
    @PostMapping("/person")
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        Person newPerson = personService.savePerson(person);
        return new ResponseEntity<Person>(newPerson, HttpStatus.CREATED);
    }

    /**
     * Edits the details of a person with the given ID.
     *
     * @param id the ID of the person to edit
     * @param person the updated Person object containing the new details
     * @return the updated Person object
     */
    @PutMapping("/person/{id}")
    public ResponseEntity<Person> editPerson(@PathVariable int id, @RequestBody Person person) {
        Person updatedPerson = personService.editPerson(id, person);
        return new ResponseEntity<Person>(updatedPerson, HttpStatus.OK);
    }

    /**
     * Deletes a person from the database by their ID.
     *
     * @param id the ID of the person to be deleted
     * @return an empty response entity indicating a successful deletion, or an error response entity if the person is not found
     */
    @DeleteMapping("/person/{id}")
    public ResponseEntity<Person> deletePerson(@PathVariable int id) {
        personService.deletePersonById(id);
        return ResponseEntity.noContent().build();
    }

}
