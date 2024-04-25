package com.skillstorm.services;

import com.skillstorm.Exceptions.ResourceNotFoundException;
import com.skillstorm.models.Employer;
import com.skillstorm.models.FormW2;
import com.skillstorm.models.TaxReturn;
import com.skillstorm.respositories.EmployerRepository;
import com.skillstorm.respositories.FormW2Repository;
import com.skillstorm.respositories.TaxReturnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FormW2Service {


    @Autowired
    private FormW2Repository formW2Repository;

    @Autowired
    private TaxReturnRepository taxReturnRepository;

    @Autowired
    private EmployerRepository employerRepository;

    /**
     * Saves a FormW2 object.
     *
     * @param formW2 The FormW2 object to be saved.
     * @return The saved FormW2 object.
     */

    public FormW2 save(FormW2 formW2) {
        int taxReturnId = formW2.getTaxReturn().getId();
        int employerId = formW2.getEmployer().getId();

        Employer employer = employerRepository.findById(employerId)
                .orElseThrow(() -> new ResourceNotFoundException("Employer not found with id: " + employerId));


        TaxReturn taxReturn = taxReturnRepository.findById(taxReturnId)
                .orElseThrow(() -> new ResourceNotFoundException("TaxReturn not found with id: " + taxReturnId));

        formW2.setEmployer(employer);
        formW2.setTaxReturn(taxReturn);

        return formW2Repository.save(formW2);
    }

    /**
     * Retrieves all the FormW2 objects from the database.
     *
     * @return A List of FormW2 objects.
     */
    public List<FormW2> findAll() {
        return formW2Repository.findAll();
    }


    /**
     * Finds a FormW2 object by its ID.
     *
     * @param id The ID of the FormW2 object to find.
     * @return The FormW2 object with the specified ID.
     * @throws ResourceNotFoundException if the FormW2 object is not found.
     */
    public FormW2 findById(int id) {
        return formW2Repository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("FormW2 not found with id" + id));
    }


    /**
     * Updates a FormW2 object in the database.
     *
     * @param formW2 The FormW2 object to be updated.
     * @return The updated FormW2 object.
     */

    public FormW2 update(FormW2 formW2) {
        return formW2Repository.save(formW2);
    }


    /**
     * Deletes a FormW2 object from the database based on its ID.
     *
     * @param id The ID of the FormW2 object to delete.
     * @return True if the FormW2 object is deleted successfully, false otherwise.
     */
    public boolean deleteById(int id) {
        formW2Repository.deleteById(id);
        return !formW2Repository.existsById(id);
    }



}//End Of Service
