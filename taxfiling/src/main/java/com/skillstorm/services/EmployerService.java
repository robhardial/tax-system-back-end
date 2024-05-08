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

    public List<Employer> findAllEmployers() {
        return employerRepository.findAll();
    }

    public Employer findEmployerById(int id) {
        Optional<Employer> employer = employerRepository.findById(id);

        if (employer.isPresent()) {

            return employer.get();
        }

        return null;
    }

    public Employer saveEmployer(Employer employer) {
        return employerRepository.save(employer);
    }

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

    public void deleteEmployerById(int id) {
        employerRepository.deleteById(id);
    }
}
