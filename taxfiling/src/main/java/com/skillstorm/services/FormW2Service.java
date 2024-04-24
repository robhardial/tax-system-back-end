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

    /**
     * Saves a FormW2 object.
     *
     * @param formW2 The FormW2 object to be saved.
     * @return The saved FormW2 object.
     */
    // Create or Update (save)
    public FormW2 save(FormW2 formW2) {
        return formW2Repository.save(formW2);
    }

    /**
     * Retrieves all the FormW2 objects from the database.
     *
     * @return A List of FormW2 objects.
     */
    // Read by id
    public List<FormW2> findAll() {
        return formW2Repository.findAll();
    }

    /**
     * Retrieves a FormW2 object from the database by its ID.
     *
     * @param id The ID of the FormW2 object to retrieve.
     * @return An Optional containing the FormW2 object with the specified ID, or an empty Optional if no such FormW2 object exists.
     */
    public Optional<FormW2> findById(int id) {
        return formW2Repository.findById(id);
    }


    /**
     * Updates a FormW2 object in the database.
     *
     * @param formW2 The FormW2 object to be updated.
     * @return The updated FormW2 object.
     */
    // Update
    public FormW2 update(FormW2 formW2) {
        return formW2Repository.save(formW2);
    }


    public boolean deleteById(int id) {
        formW2Repository.deleteById(id);
        return !formW2Repository.existsById(id);
    }



}//End Of Service
