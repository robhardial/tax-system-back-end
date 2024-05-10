package com.skillstorm.services;

import com.skillstorm.DTO.FormW2Dto;
import com.skillstorm.Exceptions.ResourceNotFoundException;
import com.skillstorm.models.Employer;
import com.skillstorm.models.Form1099;
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
     * Saves a FormW2 object in the database.
     *
     * @param formW2Dto The FormW2Dto object containing the data to be saved.
     * @return The saved FormW2 object.
     * @throws ResourceNotFoundException if the Employer or TaxReturn corresponding to the provided IDs are not found.
     */
    public FormW2 save(FormW2Dto formW2Dto) {
        Employer employer = employerRepository.findById(formW2Dto.getEmployerId())
                .orElseThrow(() -> new ResourceNotFoundException("Employer not found with id: " + formW2Dto.getEmployerId()));

        TaxReturn taxReturn = taxReturnRepository.findById(formW2Dto.getTaxReturnId())
                .orElseThrow(() -> new ResourceNotFoundException("TaxReturn not found with id: " + formW2Dto.getTaxReturnId()));

        FormW2 formW2 = new FormW2();
        formW2.setEmployer(employer);
        formW2.setTaxReturn(taxReturn);
        formW2.setYear(formW2Dto.getYear());
        formW2.setWages(formW2Dto.getWages());
        formW2.setFederalIncomeTaxWithheld(formW2Dto.getFederalIncomeTaxWithheld());
        formW2.setSocialSecurityTaxWithheld(formW2Dto.getSocialSecurityTaxWithheld());
        formW2.setMedicareTaxWithheld(formW2Dto.getMedicareTaxWithheld());

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
                .orElseThrow(() -> new ResourceNotFoundException("FormW2 not found with id" + id));
    }

    /**
     * Retrieves all the FormW2 objects with the specified taxReturnId from the database.
     *
     * @param taxReturnId The ID of the tax return to filter the FormW2 objects.
     * @return A List of FormW2 objects with the specified taxReturnId.
     */
    public List<FormW2> findAllByTaxReturnId(int taxReturnId){
        return formW2Repository.findAllByTaxReturnId(taxReturnId);
    }

    /**
     * Updates a FormW2 object in the database.
     *
     * @param formW2Dto The FormW2 object to be updated.
     * @return The updated FormW2 object.
     */
    public FormW2 update(FormW2Dto formW2Dto) {

        FormW2 formW2 = formW2Repository.findById(formW2Dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("FormW2 not found with id: " + formW2Dto.getId()));

        Employer employer = employerRepository.findById(formW2Dto.getEmployerId())
                .orElseThrow(() -> new ResourceNotFoundException("Employer not found with id: " + formW2Dto.getEmployerId()));

        TaxReturn taxReturn = taxReturnRepository.findById(formW2Dto.getTaxReturnId())
                .orElseThrow(() -> new ResourceNotFoundException("TaxReturn not found with id: " + formW2Dto.getTaxReturnId()));


        if (formW2Dto.getYear() != 0) {
            formW2.setYear(formW2.getYear());
        }

        if (formW2Dto.getWages() != 0) {
            formW2.setWages(formW2.getWages());
        }

        if (formW2Dto.getFederalIncomeTaxWithheld() != 0) {
            formW2.setFederalIncomeTaxWithheld(formW2.getFederalIncomeTaxWithheld());
        }

        if (formW2Dto.getSocialSecurityTaxWithheld() != 0) {
            formW2.setSocialSecurityTaxWithheld(formW2.getFederalIncomeTaxWithheld());
        }

        if (formW2Dto.getMedicareTaxWithheld() != 0) {
            formW2.setMedicareTaxWithheld(formW2.getMedicareTaxWithheld());
        }

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

}// End Of Service
