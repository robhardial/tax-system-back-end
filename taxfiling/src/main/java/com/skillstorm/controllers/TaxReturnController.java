package com.skillstorm.controllers;


import com.skillstorm.services.TaxReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/returns")
@CrossOrigin
public class TaxReturnController {

    @Autowired
    private TaxReturnService taxReturnService;

}
