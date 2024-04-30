package com.skillstorm.respositories;

import com.skillstorm.models.Person;
import com.skillstorm.models.User;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Person p WHERE p.id = :personId")
    void deleteByPersonId(@Param("personId") int personId);

    Person findByUser(User user);
}
