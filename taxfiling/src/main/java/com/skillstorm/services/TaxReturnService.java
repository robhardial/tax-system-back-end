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

    public TaxReturn updateFilingStatus(TaxReturn taxReturn) {
        Optional<TaxReturn> existingTaxReturnOptional = taxReturnRepository.findById(taxReturn.getId());

        if (existingTaxReturnOptional.isPresent()) {
            TaxReturn existingTaxReturn = existingTaxReturnOptional.get();

            if(taxReturn.getFilingStatus()!=null){
                existingTaxReturn.setFilingStatus(taxReturn.getFilingStatus());
            }

            return taxReturnRepository.save(existingTaxReturn);
        } else {
            throw new EntityNotFoundException("Tax Return not found");
        }
    }


    public TaxReturn updateTaxReturn(TaxReturn taxReturn) {
        TaxReturn existingTaxReturn = taxReturnRepository.findById(taxReturn.getId())
                .orElseThrow(() -> new EntityNotFoundException("Tax Return not found with ID: " + taxReturn.getId()));
        existingTaxReturn.setTotalRefundDue(taxReturn.getTotalRefundDue());
        existingTaxReturn.setCompleted(taxReturn.isCompleted());
        return taxReturnRepository.save(existingTaxReturn);
    }


    public boolean deleteById(int id){
        taxReturnRepository.deleteById(id);
        return !taxReturnRepository.existsById(id);
    }

    public Person getPersonByTaxReturnId(int taxReturnId) {
        TaxReturn taxReturn = getTaxReturnById(taxReturnId);
        return taxReturn.getPerson();
    }

}