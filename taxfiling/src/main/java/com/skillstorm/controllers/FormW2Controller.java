package com.skillstorm.controllers;

import com.skillstorm.services.FormW2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/w2s")
@CrossOrigin
public class FormW2Controller {

    @Autowired
    private FormW2Service formW2Service;
}
