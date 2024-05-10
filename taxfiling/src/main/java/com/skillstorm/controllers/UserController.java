package com.skillstorm.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import com.skillstorm.models.Person;
import com.skillstorm.models.User;
import com.skillstorm.services.PersonService;
import com.skillstorm.services.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    PersonService personService;

    /**
     * Performs a redirect to the specified URL after successful authentication.
     *
     * @param user the OAuth2User representing the authenticated user
     * @return a RedirectView object that redirects to the specified URL
     */
    @GetMapping("/signin")
    public RedirectView redirectView(@AuthenticationPrincipal OAuth2User user) {
        User newUser = userService.createUser(user);
        Person newPerson = personService.createPersonWithToken(user);
        return new RedirectView("http://localhost:5173");
    }

    /**
     * Returns a RedirectView object that redirects to the logout success page.
     *
     * @return A RedirectView object that redirects to the logout success page.
     */
    @GetMapping("/logout_success")
    @ResponseBody
    public RedirectView redirectLogoutView() {
        return new RedirectView("http://localhost:5173");
    }

    /**
     * Retrieves all users from the database.
     *
     * @return A ResponseEntity containing a list of User objects representing all users in the database.
     */
    @GetMapping
    public ResponseEntity<List<User>> findAllUsers() {
        List<User> users = userService.findAllUsers();
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    /**
     * Retrieves a User from the database based on the provided ID.
     *
     * @param id the ID of the User to retrieve
     * @return a ResponseEntity containing the requested User if found, or HttpStatus.OK if successful
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userService.findUserById(id);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    /**
     * Creates a new user with the provided user information.
     *
     * @param user The user object containing the user information.
     * @return A ResponseEntity object with the created user and HTTP status code 201 (Created).
     */
    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User newUser = userService.saveUser(user);
        return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
    }

    /**
     * Edits an existing user in the system with the specified ID.
     *
     * @param id   The ID of the user to edit.
     * @param user The updated user object with the new email and password.
     * @return The edited user object.
     */
    @PutMapping("/user/{id}")
    public ResponseEntity<User> editUser(@PathVariable int id, @RequestBody User user) {
        User updatedUser = userService.editUser(id, user);
        return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to be deleted
     * @return a response entity indicating success with no content
     */
    @DeleteMapping("/user/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable int id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

}
