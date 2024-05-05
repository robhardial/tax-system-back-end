package com.skillstorm.services;

import com.skillstorm.DTO.TaxReturnDto;
import com.skillstorm.models.Person;
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

    public TaxReturn save(TaxReturn taxReturn){
        return taxReturnRepository.save(taxReturn);
    }


    public TaxReturn getTaxReturnById(int id) {
        TaxReturn taxReturn = taxReturnRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Tax Return not found"));

        return taxReturn;
    }

    public List<TaxReturn> findAll(){return taxReturnRepository.findAll();}

    public boolean deleteById(int id){
        taxReturnRepository.deleteById(id);
        return !taxReturnRepository.existsById(id);
    }

    public Person getPersonByTaxReturnId(int taxReturnId) {
        TaxReturn taxReturn = getTaxReturnById(taxReturnId);
        return taxReturn.getPerson();
    }

}