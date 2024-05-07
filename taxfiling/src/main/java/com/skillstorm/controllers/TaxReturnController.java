package com.skillstorm.controllers;

import com.skillstorm.models.FormW2;
import com.skillstorm.models.Person;
import com.skillstorm.models.TaxReturn;
import com.skillstorm.models.Form1099;
import com.skillstorm.services.FormW2Service;
import com.skillstorm.services.PersonService;
import com.skillstorm.services.TaxReturnService;
import com.skillstorm.services.Form1099Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/returns")
@CrossOrigin
public class TaxReturnController {

    @Autowired
    private TaxReturnService taxReturnService;

    @Autowired
    private PersonService personService;

    @Autowired
    private FormW2Service formW2Service;

    @Autowired
    private Form1099Service form1099Service;

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

    @GetMapping("/{id}/forms")
    public ResponseEntity<Map<String, List<? extends Object>>> getTaxForms(@PathVariable int id){
        List<FormW2> w2s = formW2Service.findAllByTaxReturnId(id);
        List<Form1099> form1099s = form1099Service.findAllByTaxReturnId(id);

        Map<String, List<? extends Object>> forms = Map.of(
                "w2s", w2s,
                "form1099s", form1099s);

        if (w2s == null && form1099s == null) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(forms, HttpStatus.OK);
    }
}