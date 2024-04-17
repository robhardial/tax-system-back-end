package com.skillstorm.respositories;

import com.skillstorm.models.Person;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
}
