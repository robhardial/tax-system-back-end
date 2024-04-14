package com.skillstorm.services;

import org.springframework.stereotype.Service;

import com.skillstorm.models.User;
import com.skillstorm.respositories.UserRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(int id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {

            return user.get();
        }

        return null;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User editUser(int id, User user) {
        Optional<User> existingUserOptional = userRepository.findById(id);

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
            return userRepository.save(existingUser);
        } else {
            return userRepository.save(user);
        }
    }

    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

}
