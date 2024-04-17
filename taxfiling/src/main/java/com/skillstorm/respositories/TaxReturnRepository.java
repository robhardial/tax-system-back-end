package com.skillstorm.respositories;

import com.skillstorm.models.TaxReturn;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxReturnRepository extends JpaRepository<TaxReturn, Integer> {
}
