package com.skillstorm.services;

import com.skillstorm.respositories.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployerService {

    @Autowired
    EmployerRepository employerRepository;
}
