package com.skillstorm.controllers;

import com.skillstorm.models.TaxReturn;
import com.skillstorm.services.TaxReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/returns")
@CrossOrigin
public class TaxReturnController {

    @Autowired
    private TaxReturnService taxReturnService;

    @GetMapping("/{id}")
    public TaxReturn getTaxReturnById(@PathVariable("id") int id){
        return taxReturnService.getTaxReturnById(id);
    }
}