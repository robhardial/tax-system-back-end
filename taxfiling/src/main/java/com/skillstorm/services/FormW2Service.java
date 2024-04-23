package com.skillstorm.services;

import com.skillstorm.Exceptions.ResourceNotFoundException;
import com.skillstorm.models.FormW2;
import com.skillstorm.respositories.FormW2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FormW2Service {


    @Autowired
    private FormW2Repository formW2Repository;

    // Create or Update (save)
    public FormW2 save(FormW2 formW2) {
        return formW2Repository.save(formW2);
    }

    // Read by id
    public Optional<FormW2> findById(int id) {
        return formW2Repository.findById(id);
    }

    // Read all entries
    public List<FormW2> findAll() {
        return formW2Repository.findAll();
    }

    // Update
    public FormW2 update(FormW2 formW2) {
        return formW2Repository.save(formW2);
    }

    // Delete by id
    public void deleteById(int id) {
        if(!formW2Repository.existsById(id)){
            throw new ResourceNotFoundException("Form w2 not found with id:" + id);
        }
        formW2Repository.deleteById(id);
    }
}
