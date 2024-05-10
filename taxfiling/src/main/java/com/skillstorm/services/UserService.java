package com.skillstorm.services;

import org.springframework.stereotype.Service;

import com.skillstorm.models.User;
import com.skillstorm.respositories.UserRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    /**
     * Creates a new user with the given authentication principal.
     *
     * @param user the authentication principal of the user
     * @return the created user object
     */
    public User createUser(@AuthenticationPrincipal OAuth2User user) {
        Map<String, Object> userInfo = user.getAttributes();
        String userEmail = (String) userInfo.get("email");

        User existingUser = userRepository.findByEmail(userEmail);

        if (existingUser == null) {
            User newUser = new User();
            newUser.setEmail(userEmail);
            return userRepository.save(newUser);
        } else {
            return existingUser;
        }
    }

    /**
     * Retrieves all users from the database.
     *
     * @return A list of User objects representing all users in the database.
     */
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Finds a User by their ID.
     *
     * @param id the ID of the User to find
     * @return the User with the specified ID, or null if no User is found
     */
    public User findUserById(int id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {

            return user.get();
        }

        return null;
    }

    /**
     * Saves the given user into the repository.
     *
     * @param user The user to be saved.
     * @return The saved user.
     */
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Edits an existing user in the system with the specified ID.
     *
     * @param id   The ID of the user to edit.
     * @param user The updated user object with the new email and password.
     * @return The edited user object.
     */
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

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to be deleted
     */
    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

}
