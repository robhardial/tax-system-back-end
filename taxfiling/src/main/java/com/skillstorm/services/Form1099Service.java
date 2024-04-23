package com.skillstorm.services;

import com.skillstorm.Exceptions.ResourceNotFoundException;
import com.skillstorm.models.Form1099;
import com.skillstorm.respositories.Form1099Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Form1099Service {

    @Autowired
    private Form1099Repository form1099Repository;

    // Create or Update (save)
    public Form1099 save(Form1099 form1099) {
        return form1099Repository.save(form1099);
    }

    // Read by id
    public Form1099 findById(int id) {
        return form1099Repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Form1099 not found with id: " + id));
    }

    // Read all entries
    public List<Form1099> findAll() {
        return form1099Repository.findAll();
    }

    // Update (if you need a separate method from save, though save can handle both due to JPA's merge operation)
    public Form1099 update(Form1099 form1099) {
        return form1099Repository.save(form1099);
    }

    // Delete by id
    public void deleteById(int id) {
        if (!form1099Repository.existsById(id)) {
            throw new ResourceNotFoundException("Form1099 not found with id: " + id);
        }
        form1099Repository.deleteById(id);
    }
}