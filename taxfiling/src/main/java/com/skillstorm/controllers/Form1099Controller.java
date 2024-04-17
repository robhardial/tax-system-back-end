package com.skillstorm.controllers;

import com.skillstorm.services.Form1099Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/form1099")
@CrossOrigin
public class Form1099Controller {

    @Autowired
    Form1099Service form1099Service;
}
