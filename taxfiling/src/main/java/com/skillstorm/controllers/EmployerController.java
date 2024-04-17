package com.skillstorm.controllers;

import com.skillstorm.models.Employer;
import com.skillstorm.services.EmployerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employers")
@CrossOrigin
public class EmployerController {

    @Autowired
    EmployerService employerService;


}
