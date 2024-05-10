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

    /**
     * Retrieves a list of all employers.
     *
     * @return The list of all employers as a ResponseEntity with HttpStatus OK
     */
    @GetMapping
    public ResponseEntity<List<Employer>> findAllEmployers() {
        List<Employer> employers = employerService.findAllEmployers();
        return new ResponseEntity<List<Employer>>(employers, HttpStatus.OK);
    }

    /**
     * Retrieves an employer by its ID.
     *
     * @param id the ID of the employer to retrieve
     * @return a ResponseEntity containing the employer with the specified ID, or a 404 Not Found response if no employer is found
     */
    @GetMapping("/employer/{id}")
    public ResponseEntity<Employer> getEmployerById(@PathVariable int id) {
        Employer employer = employerService.findEmployerById(id);
        return new ResponseEntity<Employer>(employer, HttpStatus.OK);
    }

    /**
     * Creates a new employer by saving it to the database.
     *
     * @param employer The employer to create.
     * @return The created employer.
     */
    @PostMapping("/employer")
    public ResponseEntity<Employer> createEmployer(@RequestBody Employer employer) {
        Employer newEmployer = employerService.saveEmployer(employer);
        return new ResponseEntity<Employer>(newEmployer, HttpStatus.CREATED);
    }

    /**
     * Edits the details of an employer.
     *
     * @param id       the ID of the employer to edit
     * @param employer the updated employer information
     * @return the edited employer
     */
    @PutMapping("/employer/{id}")
    public ResponseEntity<Employer> editEmployer(@PathVariable int id, @RequestBody Employer employer) {
        Employer updatedEmployer = employerService.editEmployer(id, employer);
        return new ResponseEntity<Employer>(updatedEmployer, HttpStatus.OK);
    }

    /**
     * Deletes an employer with the specified ID.
     *
     * @param id the ID of the employer to delete
     * @return a ResponseEntity with no content
     */
    @DeleteMapping("/employer/{id}")
    public ResponseEntity<Employer> deleteEmployer(@PathVariable int id) {
        employerService.deleteEmployerById(id);
        return ResponseEntity.noContent().build();
    }

}
