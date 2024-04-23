package com.skillstorm.respositories;

import org.springframework.stereotype.Repository;

import com.skillstorm.models.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

}
