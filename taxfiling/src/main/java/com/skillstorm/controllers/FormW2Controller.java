package com.skillstorm.controllers;

import com.skillstorm.DTO.FormW2Dto;
import com.skillstorm.models.Form1099;
import com.skillstorm.models.FormW2;
import com.skillstorm.services.FormW2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/w2s")
@CrossOrigin
public class FormW2Controller {

    @Autowired
    private FormW2Service formW2Service;

    /**
     * Creates a new FormW2 object and saves it in the database.
     *
     * @param formW2Dto The FormW2Dto object containing the data to be saved.
     * @return The ResponseEntity with the created FormW2 object and HTTP status code CREATED (201).
     */
    @PostMapping
    public ResponseEntity<FormW2> createFormW2(@RequestBody FormW2Dto formW2Dto) {
        FormW2 createdFormW2 = formW2Service.save(formW2Dto);
        return new ResponseEntity<>(createdFormW2, HttpStatus.CREATED);
    }

    /**
     * Retrieves all the FormW2 objects from the database.
     *
     * @return A ResponseEntity with a List of FormW2 objects and the HTTP status code indicating the success of the request.
     */
    @GetMapping
    public ResponseEntity<List<FormW2>> getAllFormW2s() {
        List<FormW2> formW2s = formW2Service.findAll();
        return new ResponseEntity<>(formW2s, HttpStatus.OK);
    }

    /**
     * Retrieves a FormW2 object by its ID.
     *
     * @param id The ID of the FormW2 object to retrieve.
     * @return ResponseEntity containing the FormW2 object with the specified ID and the HTTP status code.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FormW2> getFormW2ById(@PathVariable int id) {
        FormW2 formW2 = formW2Service.findById(id);
        return new ResponseEntity<>(formW2, HttpStatus.OK);
    }

    /**
     * Retrieves all the FormW2 objects with the specified taxReturnId from the database.
     *
     * @param taxReturnId The ID of the tax return to filter the FormW2 objects.
     * @return A ResponseEntity object with a list of FormW2 objects and HttpStatus.OK.
     */
    @GetMapping("/tax-return/{taxReturnId}")
    public ResponseEntity<List<FormW2>> getFormW2sByTaxReturnId(@PathVariable int taxReturnId) {
        List<FormW2> formW2s = formW2Service.findAllByTaxReturnId(taxReturnId);
        return new ResponseEntity<>(formW2s, HttpStatus.OK);
    }

    /**
     * Updates a FormW2 object in the database.
     *
     * @param formW2Dto The FormW2 object to be updated.
     * @return The updated FormW2 object.
     */
    @PutMapping
    public ResponseEntity<FormW2> updateFormW2(@RequestBody FormW2Dto formW2Dto) {
        FormW2 updatedFormW2 = formW2Service.update(formW2Dto);
        return new ResponseEntity<>(updatedFormW2, HttpStatus.OK);
    }

    /**
     * Deletes a FormW2 object from the database based on its ID.
     *
     * @param id The ID of the FormW2 object to delete.
     * @return True if the FormW2 object is deleted successfully, false otherwise.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteFormW2ById(@PathVariable int id) {
        boolean deleted = formW2Service.deleteById(id);
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }

}
