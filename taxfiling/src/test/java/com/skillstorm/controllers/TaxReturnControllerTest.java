package com.skillstorm.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.models.FormW2;
import com.skillstorm.models.Form1099;
import com.skillstorm.models.Person;
import com.skillstorm.models.TaxReturn;
import com.skillstorm.services.Form1099Service;
import com.skillstorm.services.FormW2Service;
import com.skillstorm.services.PersonService;
import com.skillstorm.services.TaxReturnService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class TaxReturnControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private TaxReturnService taxReturnService;
    @Mock
    private PersonService personService;
    @Mock
    private FormW2Service formW2Service;
    @Mock
    private Form1099Service form1099Service;

    @InjectMocks
    private TaxReturnController taxReturnController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(taxReturnController).build();
    }

    @Test
    public void testGetTaxReturnById() throws Exception {
        int taxReturnId = 1;
        TaxReturn taxReturn = new TaxReturn();
        when(taxReturnService.getTaxReturnById(taxReturnId)).thenReturn(taxReturn);

        mockMvc.perform(get("/returns/{id}", taxReturnId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    public void testCreateTaxReturnPersonNotFound() throws Exception {
        int personId = 1;
        TaxReturn taxReturn = new TaxReturn();
        when(personService.findPersonById(personId)).thenReturn(null);

        mockMvc.perform(post("/returns/{personId}", personId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taxReturn)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateTaxReturnSuccess() throws Exception {
        int personId = 1;
        TaxReturn taxReturn = new TaxReturn();
        Person person = new Person();
        when(personService.findPersonById(personId)).thenReturn(person);
        when(taxReturnService.save(taxReturn)).thenReturn(taxReturn);

        mockMvc.perform(post("/returns/{personId}", personId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taxReturn)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    public void testGetTaxForms() throws Exception {
        int taxReturnId = 1;
        List<FormW2> w2s = Arrays.asList(new FormW2());
        List<Form1099> form1099s = Arrays.asList(new Form1099());
        when(formW2Service.findAllByTaxReturnId(taxReturnId)).thenReturn(w2s);
        when(form1099Service.findAllByTaxReturnId(taxReturnId)).thenReturn(form1099s);

        mockMvc.perform(get("/returns/{id}/forms", taxReturnId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.w2s").isArray())
                .andExpect(jsonPath("$.form1099s").isArray());
    }

    @Test
    public void testUpdateTaxReturn() throws Exception {
        int taxReturnId = 1;
        TaxReturn taxReturn = new TaxReturn();
        when(taxReturnService.updateFilingStatus(taxReturn)).thenReturn(taxReturn);

        mockMvc.perform(put("/returns/{id}", taxReturnId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taxReturn)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }
}