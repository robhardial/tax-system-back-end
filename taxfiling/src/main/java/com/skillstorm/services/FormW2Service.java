package com.skillstorm.services;

import com.skillstorm.respositories.FormW2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormW2Service {

    @Autowired
    FormW2Repository formW2Repository;
}
