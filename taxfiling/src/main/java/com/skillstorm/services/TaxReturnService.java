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

    /**
     * Saves a TaxReturn object to the database.
     *
     * @param taxReturn the TaxReturn object to be saved
     * @return the saved TaxReturn object
     */
    public TaxReturn save(TaxReturn taxReturn){
        return taxReturnRepository.save(taxReturn);
    }

    /**
     * Retrieves a {@link TaxReturn} object by its ID.
     *
     * @param id the ID of the tax return to retrieve.
     * @return the {@link TaxReturn} object.
     * @throws EntityNotFoundException if the tax return with the given ID is not found.
     */
    public TaxReturn getTaxReturnById(int id) {
        TaxReturn taxReturn = taxReturnRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Tax Return not found"));

        return taxReturn;
    }

    /**
     * Finds all tax returns.
     *
     * @return the list of tax returns
     */
    public List<TaxReturn> findAll(){return taxReturnRepository.findAll();}

    /**
     * Updates the filing status of a TaxReturn object in the database.
     *
     * @param taxReturn The TaxReturn object to be updated.
     * @return The updated TaxReturn object.
     * @throws EntityNotFoundException if the TaxReturn cannot be found in the database.
     */
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


    /**
     * Updates the details of a TaxReturn object and saves it to the repository.
     *
     * @param taxReturn the TaxReturn object to update
     * @return the updated TaxReturn object
     * @throws EntityNotFoundException if the TaxReturn object with the given ID is not found in the repository
     */
    public TaxReturn updateTaxReturn(TaxReturn taxReturn) {
        TaxReturn existingTaxReturn = taxReturnRepository.findById(taxReturn.getId())
                .orElseThrow(() -> new EntityNotFoundException("Tax Return not found with ID: " + taxReturn.getId()));
        existingTaxReturn.setTotalRefundDue(taxReturn.getTotalRefundDue());
        existingTaxReturn.setCompleted(taxReturn.isCompleted());
        return taxReturnRepository.save(existingTaxReturn);
    }


    /**
     * Deletes a tax return record by the specified ID.
     *
     * @param id the ID of the tax return to be deleted
     * @return true if the tax return is successfully deleted, false otherwise
     */
    public boolean deleteById(int id){
        taxReturnRepository.deleteById(id);
        return !taxReturnRepository.existsById(id);
    }

    /**
     * Retrieves the person associated with a given tax return ID.
     *
     * @param taxReturnId the ID of the tax return
     * @return the person associated with the tax return, or null if not found
     */
    public Person getPersonByTaxReturnId(int taxReturnId) {
        TaxReturn taxReturn = getTaxReturnById(taxReturnId);
        return taxReturn.getPerson();
    }

}