package com.skillstorm.controllers;

import com.skillstorm.models.Employer;
import com.skillstorm.services.EmployerService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employers")
@CrossOrigin
public class EmployerController {

    @Autowired
    EmployerService employerService;

    @GetMapping
    public ResponseEntity<List<Employer>> findAllEmployers() {
        List<Employer> employers = employerService.findAllEmployers();
        return new ResponseEntity<List<Employer>>(employers, HttpStatus.OK);
    }

    @GetMapping("/employer/{id}")
    public ResponseEntity<Employer> getEmployerById(@PathVariable int id) {
        Employer employer = employerService.findEmployerById(id);
        return new ResponseEntity<Employer>(employer, HttpStatus.OK);
    }

    @PostMapping("/employer")
    public ResponseEntity<Employer> createEmployer(@RequestBody Employer employer) {
        Employer newEmployer = employerService.saveEmployer(employer);
        return new ResponseEntity<Employer>(newEmployer, HttpStatus.CREATED);
    }

    @PutMapping("/employer/{id}")
    public ResponseEntity<Employer> editEmployer(@PathVariable int id, @RequestBody Employer employer) {
        Employer updatedEmployer = employerService.editEmployer(id, employer);
        return new ResponseEntity<Employer>(updatedEmployer, HttpStatus.OK);
    }

    @DeleteMapping("/employer/{id}")
    public ResponseEntity<Employer> deleteEmployer(@PathVariable int id) {
        employerService.deleteEmployerById(id);
        return ResponseEntity.noContent().build();
    }

}
