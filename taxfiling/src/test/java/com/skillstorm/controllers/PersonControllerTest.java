package com.skillstorm.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.models.Person;
import com.skillstorm.models.TaxReturn;
import com.skillstorm.services.PersonService;
import com.skillstorm.services.TaxReturnService;

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
public class PersonControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private PersonService personService;
    @Mock
    private TaxReturnService taxReturnService;

    @InjectMocks
    private PersonController personController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
    }

    @Test
    public void testFindAllPersons() throws Exception {
        List<Person> persons = Arrays.asList(new Person(), new Person());
        when(personService.findAllPersons()).thenReturn(persons);

        mockMvc.perform(get("/persons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(persons.size()));
    }

    @Test
    public void testGetPersonById() throws Exception {
        Person person = new Person();
        when(personService.findPersonById(1)).thenReturn(person);

        mockMvc.perform(get("/persons/person/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    public void testCreatePerson() throws Exception {
        Person person = new Person();
        when(personService.savePerson(person)).thenReturn(person);

        mockMvc.perform(post("/persons/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    public void testEditPerson() throws Exception {
        Person person = new Person();
        when(personService.editPerson(1, person)).thenReturn(person);

        mockMvc.perform(put("/persons/person/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    public void testDeletePerson() throws Exception {
        doNothing().when(personService).deletePersonById(1);

        mockMvc.perform(delete("/persons/person/1"))
                .andExpect(status().isNoContent());
    }
}
