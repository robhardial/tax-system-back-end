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

    @PostMapping("/tokenPerson")
    public ResponseEntity<Person> createPersonWithToken(@RequestBody Person person,
            @AuthenticationPrincipal OAuth2User user) {

        Person newPerson = personService.createPersonWithToken(user);
        return new ResponseEntity<Person>(newPerson, HttpStatus.CREATED);
    }

    @GetMapping("/tokenPerson")
    public ResponseEntity<Person> getPersonWithToken(@AuthenticationPrincipal OAuth2User user) {

        Person retrievedPerson = personService.getPersonWithToken(user);
        return new ResponseEntity<Person>(retrievedPerson, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Person>> findAllPersons() {
        List<Person> persons = personService.findAllPersons();
        return new ResponseEntity<List<Person>>(persons, HttpStatus.OK);
    }

    @GetMapping("/person/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable int id) {
        Person person = personService.findPersonById(id);
        return new ResponseEntity<Person>(person, HttpStatus.OK);
    }

    /**
     * Retrieves all the tax returns associated with a person based on their ID.
     *
     * @param id the ID of the person
     * @return a ResponseEntity object with a list of TaxReturn objects if tax returns are found, otherwise returns a ResponseEntity object with a not found response
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
     * Retrieves a person by their tax return ID.
     *
     * @param taxReturnId the ID of the tax return
     * @return ResponseEntity<Person> the person associated with the tax return if found, otherwise returns a not found response
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

    @PostMapping("/person")
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        Person newPerson = personService.savePerson(person);
        return new ResponseEntity<Person>(newPerson, HttpStatus.CREATED);
    }

    @PutMapping("/person/{id}")
    public ResponseEntity<Person> editPerson(@PathVariable int id, @RequestBody Person person) {
        Person updatedPerson = personService.editPerson(id, person);
        return new ResponseEntity<Person>(updatedPerson, HttpStatus.OK);
    }

    @DeleteMapping("/person/{id}")
    public ResponseEntity<Person> deletePerson(@PathVariable int id) {
        personService.deletePersonById(id);
        return ResponseEntity.noContent().build();
    }

}
