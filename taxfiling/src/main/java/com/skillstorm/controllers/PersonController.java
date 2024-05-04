package com.skillstorm.controllers;

import com.skillstorm.models.Person;
import com.skillstorm.models.TaxReturn;
import com.skillstorm.services.PersonService;

import java.util.List;

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

    @PostMapping("/tokenPerson")
    public ResponseEntity<Person> createPersonWithToken(@RequestBody Person person,
            @AuthenticationPrincipal OAuth2User user) {

        Person newPerson = personService.createPersonWithToken(person, user);
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

    //Gets all the tax returns associated with the person
    @GetMapping("/{id}/tax-returns")
    public ResponseEntity<List<TaxReturn>> getPersonTaxReturns(@PathVariable int id) {
        List<TaxReturn> taxReturns = personService.findTaxReturnsByPersonId(id);
        if (taxReturns != null && !taxReturns.isEmpty()) {
            return new ResponseEntity<>(taxReturns, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
