package com.skillstorm.respositories;

import com.skillstorm.models.FormW2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormW2Repository extends JpaRepository<FormW2, Integer> {
    List<FormW2> findAllByTaxReturnId(int taxReturnId);
}
