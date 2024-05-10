package com.skillstorm.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.models.Employer;
import com.skillstorm.services.EmployerService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
public class EmployerControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private EmployerService employerService;

    @InjectMocks
    private EmployerController employerController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(employerController).build();
    }

    @Test
    public void testFindAllEmployers() throws Exception {
        List<Employer> employers = Arrays.asList(new Employer(), new Employer());
        when(employerService.findAllEmployers()).thenReturn(employers);

        mockMvc.perform(get("/employers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(employers.size()));
    }

    @Test
    public void testGetEmployerById() throws Exception {
        Employer employer = new Employer(); // assume there's an ID setter if needed
        when(employerService.findEmployerById(1)).thenReturn(employer);

        mockMvc.perform(get("/employers/employer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employer.getId()));
    }

    @Test
    public void testCreateEmployer() throws Exception {
        Employer employer = new Employer();
        when(employerService.saveEmployer(any(Employer.class))).thenReturn(employer);

        mockMvc.perform(post("/employers/employer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employer)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testEditEmployer() throws Exception {
        Employer employer = new Employer(); // assuming setId is available
        when(employerService.editEmployer(eq(1), any(Employer.class))).thenReturn(employer);

        mockMvc.perform(put("/employers/employer/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employer)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteEmployer() throws Exception {
        doNothing().when(employerService).deleteEmployerById(1);

        mockMvc.perform(delete("/employers/employer/1"))
                .andExpect(status().isNoContent());
    }
}