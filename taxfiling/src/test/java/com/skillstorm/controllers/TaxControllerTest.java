package com.skillstorm.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import com.skillstorm.models.TaxReturn;
import com.skillstorm.services.TaxServices.TaxCalculationService;
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

@ExtendWith(MockitoExtension.class)
public class TaxControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TaxCalculationService taxCalculationService;

    @Mock
    private TaxReturnService taxReturnService;

    @InjectMocks
    private TaxController taxController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(taxController).build();
    }

    @Test
    public void testCalculateTaxReturn() throws Exception {
        int taxReturnId = 1;
        TaxReturn taxReturn = new TaxReturn(); // Assume necessary setters are used to set up the object
        when(taxReturnService.getTaxReturnById(taxReturnId)).thenReturn(taxReturn);
        when(taxCalculationService.calculateTotalTaxDue(taxReturn)).thenReturn(500.00);

        mockMvc.perform(get("/tax/calculate/{id}", taxReturnId))
                .andExpect(status().isOk())
                .andExpect(content().string("500.0"));
    }
}
