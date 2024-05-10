package com.skillstorm.controllers;

import com.skillstorm.DTO.Form1099Dto;
import com.skillstorm.Exceptions.ResourceNotFoundException;
import com.skillstorm.models.Form1099;
import com.skillstorm.models.FormW2;
import com.skillstorm.services.Form1099Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/form1099s")
@CrossOrigin
public class Form1099Controller {

    @Autowired
    private Form1099Service form1099Service;

    /**
     * Creates a new Form1099 object and saves it to the database.
     *
     * @param form1099Dto the Form1099Dto object containing the data for the new Form1099
     * @return a ResponseEntity containing the created Form1099 object and the HTTP status code CREATED
     */
    @PostMapping
    public ResponseEntity<Form1099> createForm1099(@RequestBody Form1099Dto form1099Dto) {
        Form1099 createdForm1099 = form1099Service.save(form1099Dto);
        return new ResponseEntity<>(createdForm1099, HttpStatus.CREATED);
    }

    /**
     * Retrieves all Form1099s from the database.
     *
     * @return a ResponseEntity containing a list of Form1099 objects with status OK
     */
    @GetMapping
    public ResponseEntity<List<Form1099>> getAllForm1099s() {
        List<Form1099> form1099s = form1099Service.findAll();
        return new ResponseEntity<>(form1099s, HttpStatus.OK);
    }

    /**
     * Retrieves a Form1099 object by its ID.
     *
     * @param id the ID of the Form1099 object to be retrieved
     * @return the Form1099 object with the specified ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Form1099> getForm1099ById(@PathVariable int id) {
        Form1099 form1099 = form1099Service.findById(id);
        return new ResponseEntity<>(form1099, HttpStatus.OK);
    }

    /**
     * Retrieves a list of Form1099 objects based on the tax return ID.
     *
     * @param taxReturnId the ID of the tax return
     * @return a ResponseEntity containing a list of Form1099 objects found with HTTP status OK
     */
    @GetMapping("/tax-return/{taxReturnId}")
    public ResponseEntity<List<Form1099>> getForm1099sByTaxReturnId(@PathVariable int taxReturnId) {
        List<Form1099> form1099s = form1099Service.findAllByTaxReturnId(taxReturnId);
        return new ResponseEntity<>(form1099s, HttpStatus.OK);
    }

    /**
     * Updates a Form1099 object in the database based on the provided Form1099Dto.
     *
     * @param form1099Dto the Form1099Dto object containing the updated values
     * @return the updated Form1099 object
     * @throws ResourceNotFoundException if no Form1099 object is found with the specified ID
     */
    @PutMapping
    public ResponseEntity<Form1099> updateForm1099(@RequestBody Form1099Dto form1099Dto) {
        Form1099 updatedForm1099 = form1099Service.update(form1099Dto);
        return new ResponseEntity<>(updatedForm1099, HttpStatus.OK);
    }

    /**
     * Deletes a Form1099 object from the database by its ID.
     *
     * @param id the ID of the Form1099 object to be deleted
     * @return true if the Form1099 object is deleted successfully, false otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteForm1099ById(@PathVariable int id) {
        boolean deleted = form1099Service.deleteById(id);
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }
}
