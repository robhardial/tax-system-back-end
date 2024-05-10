package com.skillstorm.controllers;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.DTO.Form1099Dto;
import com.skillstorm.models.Form1099;
import com.skillstorm.services.Form1099Service;
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

@ExtendWith(MockitoExtension.class)
public class Form1099ControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private Form1099Service form1099Service;

    @InjectMocks
    private Form1099Controller form1099Controller;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(form1099Controller).build();
    }

    @Test
    public void testCreateForm1099() throws Exception {
        Form1099Dto form1099Dto = new Form1099Dto(); // Assume setters if needed
        Form1099 form1099 = new Form1099(); // Assume setters if needed
        when(form1099Service.save(form1099Dto)).thenReturn(form1099);

        mockMvc.perform(post("/form1099s")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(form1099Dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    public void testGetAllForm1099s() throws Exception {
        List<Form1099> form1099s = Arrays.asList(new Form1099(), new Form1099());
        when(form1099Service.findAll()).thenReturn(form1099s);

        mockMvc.perform(get("/form1099s"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(form1099s.size()));
    }

    @Test
    public void testGetForm1099ById() throws Exception {
        Form1099 form1099 = new Form1099(); // assume there's an ID setter
        when(form1099Service.findById(1)).thenReturn(form1099);

        mockMvc.perform(get("/form1099s/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    public void testGetForm1099sByTaxReturnId() throws Exception {
        List<Form1099> form1099s = Arrays.asList(new Form1099(), new Form1099());
        when(form1099Service.findAllByTaxReturnId(1)).thenReturn(form1099s);

        mockMvc.perform(get("/form1099s/tax-return/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(form1099s.size()));
    }

    @Test
    public void testUpdateForm1099() throws Exception {
        Form1099Dto form1099Dto = new Form1099Dto(); // Assume setters
        Form1099 updatedForm1099 = new Form1099(); // Assume setters
        when(form1099Service.update(form1099Dto)).thenReturn(updatedForm1099);

        mockMvc.perform(put("/form1099s")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(form1099Dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    public void testDeleteForm1099ById() throws Exception {
        when(form1099Service.deleteById(1)).thenReturn(true);

        mockMvc.perform(delete("/form1099s/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }
}