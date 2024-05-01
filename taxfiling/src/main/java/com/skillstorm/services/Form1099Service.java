package com.skillstorm.services;

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

    /**
     * Saves a Form1099 object to the database.
     *
     * @param form1099 the Form1099 object to be saved
     * @return the saved Form1099 object
     */
    public Form1099 save(Form1099 form1099) {
        int taxReturnId = form1099.getTaxReturn().getId();
        TaxReturn taxReturn = taxReturnRepository.findById(taxReturnId)
                .orElseThrow(() -> new ResourceNotFoundException("TaxReturn not found with id: " + taxReturnId));

        form1099.setTaxReturn(taxReturn);
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

    public Form1099 update(Form1099 form1099) {

        Optional<Form1099> existingForm1099Optional = form1099Repository.findById(form1099.getId());

        if (existingForm1099Optional.isPresent()) {

            Form1099 existingForm1099 = existingForm1099Optional.get();

            if (form1099.getTaxReturn() != null) {
                existingForm1099.setTaxReturn(form1099.getTaxReturn());
            }

            if (form1099.getWages() != 0) {
                existingForm1099.setWages((form1099.getWages()));
            }

            if (form1099.getYear() != 0) {
                existingForm1099.setYear(form1099.getYear());
            }

            return form1099Repository.save(existingForm1099);
        } else {
            return form1099Repository.save(form1099);
        }
    }

    // Delete by id
    public boolean deleteById(int id) {
        form1099Repository.deleteById(id);
        return !form1099Repository.existsById(id);
    }

}// End Of Service