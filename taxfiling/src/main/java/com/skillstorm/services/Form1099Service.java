package com.skillstorm.services;

import com.skillstorm.DTO.Form1099Dto;
import com.skillstorm.Exceptions.ResourceNotFoundException;
import com.skillstorm.models.Form1099;
import com.skillstorm.models.TaxReturn;
import com.skillstorm.respositories.Form1099Repository;
import com.skillstorm.respositories.TaxReturnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Form1099Service {

    @Autowired
    private Form1099Repository form1099Repository;

    @Autowired
    private TaxReturnRepository taxReturnRepository;

    public Form1099 save(Form1099Dto form1099Dto) {
        TaxReturn taxReturn = taxReturnRepository.findById(form1099Dto.getTaxReturnId())
                .orElseThrow(() -> new ResourceNotFoundException("TaxReturn not found with id: " + form1099Dto.getTaxReturnId()));

        Form1099 form1099 = new Form1099();
        form1099.setTaxReturn(taxReturn);
        form1099.setPayer(form1099Dto.getPayer());
        form1099.setWages(form1099Dto.getWages());
        form1099.setYear(form1099Dto.getYear());

        return form1099Repository.save(form1099);

    }

    /**
     * Retrieves all Form1099 objects from the database.
     *
     * @return a list of Form1099 objects
     */
    public List<Form1099> findAll() {
        return form1099Repository.findAll();
    }

    /**
     * Finds a Form1099 object by its ID.
     *
     * @param id the ID of the Form1099 object to be found
     * @return the found Form1099 object
     * @throws ResourceNotFoundException if no Form1099 object is found with the
     *                                   specified ID
     */
    public Form1099 findById(int id) {
        return form1099Repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Form1099 not found with id: " + id));
    }

    /**
     * Retrieves a list of Form1099 objects based on the tax return ID.
     *
     * @param taxReturnId the ID of the tax return
     * @return a List of Form1099 objects found
     */
    public List<Form1099> findAllByTaxReturnId(int taxReturnId){
        return form1099Repository.findAllByTaxReturnId(taxReturnId);
    }

    /**
     * Updates a Form1099 object in the database based on the provided Form1099Dto.
     *
     * @param form1099Dto the Form1099Dto object containing the updated values
     * @return the updated Form1099 object
     * @throws ResourceNotFoundException if no Form1099 object is found with the specified ID
     */
    public Form1099 update(Form1099Dto form1099Dto) {
        Form1099 form1099 = form1099Repository.findById(form1099Dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Form1099 not found with id: " + form1099Dto.getId()));

        form1099.setPayer(form1099Dto.getPayer());
        form1099.setWages(form1099Dto.getWages());
        form1099.setYear(form1099Dto.getYear());

        return form1099Repository.save(form1099);
    }

    /**
     * Deletes a Form1099 object from the database by its ID.
     *
     * @param id the ID of the Form1099 object to be deleted
     * @return true if the Form1099 object is deleted successfully, false otherwise
     */
    // Delete by id
    public boolean deleteById(int id) {
        form1099Repository.deleteById(id);
        return !form1099Repository.existsById(id);
    }

}// End Of Service