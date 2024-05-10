package com.skillstorm.DTO;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class TaxReturnDtoTest {

    @Test
    void testGettersAndSetters() {
        // Create an instance of the DTO
        TaxReturnDto dto = new TaxReturnDto();

        // Set values using setters
        dto.setTaxReturnId(100);
        dto.setPersonId(1);
        dto.setYear(2020);
        dto.setFilingStatus("Single");
        List<Integer> formsW2s = Arrays.asList(101, 102);
        List<Integer> form1099s = Arrays.asList(201, 202);
        dto.setFormsW2s(formsW2s);
        dto.setForm1099s(form1099s);

        // Verify that getters return the correct values
        assertAll("dto",
                () -> assertEquals(100, dto.getTaxReturnId(), "TaxReturnId should match the set value."),
                () -> assertEquals(1, dto.getPersonId(), "PersonId should match the set value."),
                () -> assertEquals(2020, dto.getYear(), "Year should match the set value."),
                () -> assertEquals("Single", dto.getFilingStatus(), "FilingStatus should match the set value."),
                () -> assertEquals(formsW2s, dto.getFormsW2s(), "FormsW2s should match the set value."),
                () -> assertEquals(form1099s, dto.getForm1099s(), "Form1099s should match the set value.")
        );
    }
}
