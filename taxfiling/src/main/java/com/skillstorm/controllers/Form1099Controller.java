package com.skillstorm.controllers;

import com.skillstorm.Exceptions.ResourceNotFoundException;
import com.skillstorm.models.Form1099;
import com.skillstorm.services.Form1099Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/form1099")
@CrossOrigin
public class Form1099Controller {

    @Autowired
    private Form1099Service form1099Service;

    @PostMapping
    public ResponseEntity<Form1099> createForm1099(@RequestBody Form1099 form1099) {
        Form1099 createdForm1099 = form1099Service.save(form1099);
        return new ResponseEntity<>(createdForm1099, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Form1099>> getAllForm1099s() {
        List<Form1099> form1099s = form1099Service.findAll();
        return new ResponseEntity<>(form1099s, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Form1099> getForm1099ById(@PathVariable int id) {
        Form1099 form1099 = form1099Service.findById(id);
        return new ResponseEntity<>(form1099, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Form1099> updateForm1099(@RequestBody Form1099 form1099) {
        Form1099 updatedForm1099 = form1099Service.update(form1099);
        return new ResponseEntity<>(updatedForm1099, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteForm1099ById(@PathVariable int id) {
        boolean deleted = form1099Service.deleteById(id);
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }
}
