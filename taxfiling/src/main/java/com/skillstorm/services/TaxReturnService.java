package com.skillstorm.services;

import com.skillstorm.DTO.TaxReturnDto;
import com.skillstorm.models.TaxReturn;
import com.skillstorm.respositories.TaxReturnRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaxReturnService {

    @Autowired
    TaxReturnRepository taxReturnRepository;

    public TaxReturn getTaxReturnById(int id) {
        TaxReturn taxReturn = taxReturnRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Tax Return not found"));

        return taxReturn;
    }

}