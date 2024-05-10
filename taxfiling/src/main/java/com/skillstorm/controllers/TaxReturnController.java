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

    /**
     * Creates a new tax return for a person with the specified ID.
     *
     * @param personId   the ID of the person
     * @param taxReturn  the tax return object to be created
     * @return a ResponseEntity object containing the created tax return and the HTTP status code
     *          HttpStatus.CREATED if successful, ResponseEntity.notFound().build() if the person ID is not found
     */
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

    /**
     * Retrieves the tax forms associated with a tax return.
     *
     * @param id The ID of the tax return.
     * @return A ResponseEntity containing a map of tax forms. The map keys are "w2s" and "form1099s" and the values are lists of FormW2 and Form1099 objects respectively.
     *         Returns ResponseEntity.notFound().build() if no tax forms are found.
     */
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

    /**
     * Updates the filing status of a TaxReturn object in the database.
     *
     * @param id         The ID of the TaxReturn object to be updated.
     * @param taxReturn  The updated TaxReturn object.
     * @return The updated TaxReturn object wrapped in a ResponseEntity with HTTP status OK.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TaxReturn> updateTaxReturn(@PathVariable int id, @RequestBody TaxReturn taxReturn) {
        TaxReturn updatedTaxReturn = taxReturnService.updateFilingStatus(taxReturn);
        return new ResponseEntity<>(updatedTaxReturn, HttpStatus.OK);
    }


}