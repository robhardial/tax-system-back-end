package com.skillstorm.respositories;

import org.springframework.stereotype.Repository;

import com.skillstorm.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
