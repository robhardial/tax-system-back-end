package com.skillstorm.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import com.skillstorm.models.Person;
import com.skillstorm.models.User;
import com.skillstorm.services.PersonService;
import com.skillstorm.services.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;
    @Mock
    private PersonService personService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    @WithMockUser
    public void testRedirectView() throws Exception {
        OAuth2User user = mock(OAuth2User.class);
        User newUser = new User();
        Person newPerson = new Person();
        when(userService.createUser(any(OAuth2User.class))).thenReturn(newUser);
        when(personService.createPersonWithToken(any(OAuth2User.class))).thenReturn(newPerson);

        mockMvc.perform(get("/users/signin"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost:5173"));
    }

    @Test
    public void testRedirectLogoutView() throws Exception {
        mockMvc.perform(get("/users/logout_success"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost:5173"));
    }

    @Test
    public void testFindAllUsers() throws Exception {
        List<User> users = Arrays.asList(new User(), new User());
        when(userService.findAllUsers()).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(users.size()));
    }

    @Test
    public void testGetUserById() throws Exception {
        int userId = 1;
        User user = new User();
        when(userService.findUserById(userId)).thenReturn(user);

        mockMvc.perform(get("/users/user/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    public void testCreateUser() throws Exception {
        User user = new User();
        when(userService.saveUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    public void testEditUser() throws Exception {
        int userId = 1;
        User user = new User();
        when(userService.editUser(eq(userId), any(User.class))).thenReturn(user);

        mockMvc.perform(put("/users/user/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    public void testDeleteUser() throws Exception {
        int userId = 1;
        doNothing().when(userService).deleteUserById(userId);

        mockMvc.perform(delete("/users/user/{id}", userId))
                .andExpect(status().isNoContent());
    }
}
