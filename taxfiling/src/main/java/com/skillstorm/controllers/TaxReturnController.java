package com.skillstorm.controllers;

import com.skillstorm.models.Person;
import com.skillstorm.models.TaxReturn;
import com.skillstorm.services.PersonService;
import com.skillstorm.services.TaxReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/returns")
@CrossOrigin
public class TaxReturnController {

    @Autowired
    private TaxReturnService taxReturnService;

    @Autowired
    private PersonService personService;

    @GetMapping("/{id}")
    public TaxReturn getTaxReturnById(@PathVariable("id") int id){
        return taxReturnService.getTaxReturnById(id);
    }

    @PostMapping("/{personId}")
    public ResponseEntity<TaxReturn> createTaxReturn(@PathVariable int personId, @RequestBody TaxReturn taxReturn){
        Person person = personService.findPersonById(personId);
        if(person == null){
            return ResponseEntity.notFound().build();
        }

        taxReturn.setPerson(person);
        TaxReturn createdTaxReturn = taxReturnService.save(taxReturn);
        return new ResponseEntity<>(createdTaxReturn, HttpStatus.CREATED);
    }

}