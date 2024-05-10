package com.skillstorm.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.DTO.FormW2Dto;
import com.skillstorm.models.FormW2;
import com.skillstorm.services.FormW2Service;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class FormW2ControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private FormW2Service formW2Service;

    @InjectMocks
    private FormW2Controller formW2Controller;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(formW2Controller).build();
    }

    @Test
    public void testCreateFormW2() throws Exception {
        FormW2Dto formW2Dto = new FormW2Dto(); // Assume setters if needed
        FormW2 createdFormW2 = new FormW2(); // Assume setters if needed
        when(formW2Service.save(formW2Dto)).thenReturn(createdFormW2);

        mockMvc.perform(post("/w2s")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(formW2Dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    public void testGetAllFormW2s() throws Exception {
        List<FormW2> formW2s = Arrays.asList(new FormW2(), new FormW2());
        when(formW2Service.findAll()).thenReturn(formW2s);

        mockMvc.perform(get("/w2s"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(formW2s.size()));
    }

    @Test
    public void testGetFormW2ById() throws Exception {
        FormW2 formW2 = new FormW2(); // Assume there's an ID setter
        when(formW2Service.findById(1)).thenReturn(formW2);

        mockMvc.perform(get("/w2s/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    public void testGetFormW2sByTaxReturnId() throws Exception {
        List<FormW2> formW2s = Arrays.asList(new FormW2(), new FormW2());
        when(formW2Service.findAllByTaxReturnId(1)).thenReturn(formW2s);

        mockMvc.perform(get("/w2s/tax-return/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(formW2s.size()));
    }

//    @Test
//    public void testUpdateFormW2() throws Exception {
//        FormW2 formW2 = new FormW2(); // Assume setters
//        when(formW2Service.update(formW2)).thenReturn(formW2);
//
//        mockMvc.perform(put("/w2s")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(formW2)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").exists());
//    }

    @Test
    public void testDeleteFormW2ById() throws Exception {
        when(formW2Service.deleteById(1)).thenReturn(true);

        mockMvc.perform(delete("/w2s/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }
}
