package com.skillstorm.controllers;

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

    @PostMapping
    public ResponseEntity<FormW2> createFormW2(@RequestBody FormW2 formW2) {
        FormW2 createdFormW2 = formW2Service.save(formW2);
        return new ResponseEntity<>(createdFormW2, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FormW2>> getAllFormW2s() {
        List<FormW2> formW2s = formW2Service.findAll();
        return new ResponseEntity<>(formW2s, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormW2> getFormW2ById(@PathVariable int id) {
        FormW2 formW2 = formW2Service.findById(id);
        return new ResponseEntity<>(formW2, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<FormW2> updateFormW2(@RequestBody FormW2 formW2) {
        FormW2 updatedFormW2 = formW2Service.update(formW2);
        return new ResponseEntity<>(updatedFormW2, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteFormW2ById(@PathVariable int id) {
        boolean deleted = formW2Service.deleteById(id);
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }

}
