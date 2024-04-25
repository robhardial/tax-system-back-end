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
            existingEmployer.setEin(employer.getEin());
            existingEmployer.setName(employer.getName());
            existingEmployer.setAddress(employer.getAddress());
            existingEmployer.setFormW2s(employer.getFormW2s());
            return employerRepository.save(existingEmployer);
        } else {
            return employerRepository.save(employer);
        }
    }

    public void deleteEmployerById(int id) {
        employerRepository.deleteById(id);
    }
}
