package com.skillstorm.services;

import com.skillstorm.respositories.Form1099Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skillstorm.respositories.Form1099Repository;

@Service
public class Form1099Service {

    @Autowired
    Form1099Repository form1099Repository;
}
