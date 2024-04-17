package com.skillstorm.services;

import com.skillstorm.respositories.TaxReturnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaxReturnService {

    @Autowired
    TaxReturnRepository taxReturnRepository;

}
