package com.skillstorm.services;

import com.skillstorm.models.Employer;
import com.skillstorm.respositories.EmployerRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployerService {

    @Autowired
    EmployerRepository employerRepository;

    /**
     * Retrieves a list of all employers.
     *
     * @return The list of all employers.
     */
    public List<Employer> findAllEmployers() {
        return employerRepository.findAll();
    }

    /**
     * Finds an employer by its ID.
     *
     * @param id the ID of the employer to find
     * @return the employer with the specified ID, or null if no employer is found
     */
    public Employer findEmployerById(int id) {
        Optional<Employer> employer = employerRepository.findById(id);

        if (employer.isPresent()) {

            return employer.get();
        }

        return null;
    }

    /**
     * Saves an employer to the database.
     *
     * @param employer The employer to save.
     * @return The saved employer.
     */
    public Employer saveEmployer(Employer employer) {
        return employerRepository.save(employer);
    }

    /**
     * Edits the details of an employer.
     *
     * @param id       the ID of the employer to edit
     * @param employer the updated employer information
     * @return the edited employer
     */
    public Employer editEmployer(int id, Employer employer) {
        Optional<Employer> existingEmployerOptional = employerRepository.findById(id);

        if (existingEmployerOptional.isPresent()) {

            Employer existingEmployer = existingEmployerOptional.get();
            // Update fields only if they are not null in the request payload
            if (employer.getEin() != null) {
                existingEmployer.setEin(employer.getEin());
            }
            if (employer.getName() != null) {
                existingEmployer.setName(employer.getName());
            }
            if (employer.getAddress() != null) {
                existingEmployer.setAddress(employer.getAddress());
            }
            if (employer.getFormW2s() != null) {
                existingEmployer.setFormW2s(employer.getFormW2s());
            }

            return employerRepository.save(existingEmployer);
        } else {
            return employerRepository.save(employer);
        }
    }

    /**
     * Deletes an employer with the specified ID.
     *
     * @param id the ID of the employer to delete
     */
    public void deleteEmployerById(int id) {
        employerRepository.deleteById(id);
    }
}
