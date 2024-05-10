package com.skillstorm.services;

import com.skillstorm.DTO.Form1099Dto;
import com.skillstorm.Exceptions.ResourceNotFoundException;
import com.skillstorm.models.Form1099;
import com.skillstorm.models.TaxReturn;


import com.skillstorm.respositories.Form1099Repository;
import com.skillstorm.respositories.TaxReturnRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class Form1099ServiceTest {

    @Mock
    private Form1099Repository form1099Repository;

    @Mock
    private TaxReturnRepository taxReturnRepository;

    @InjectMocks
    private Form1099Service form1099Service;

    @Test
    void testSave() {
        Form1099Dto dto = new Form1099Dto();
        dto.setTaxReturnId(1);
        dto.setPayer("Payer Inc");
        dto.setWages(5000.00);
        dto.setYear(2020);

        TaxReturn taxReturn = new TaxReturn();
        Form1099 form1099 = new Form1099();
        form1099.setTaxReturn(taxReturn);
        form1099.setPayer("Payer Inc");
        form1099.setWages(5000.00);
        form1099.setYear(2020);

        when(taxReturnRepository.findById(dto.getTaxReturnId())).thenReturn(Optional.of(taxReturn));
        when(form1099Repository.save(any(Form1099.class))).thenReturn(form1099);

        Form1099 savedForm1099 = form1099Service.save(dto);

        assertAll(
                () -> assertEquals("Payer Inc", savedForm1099.getPayer()),
                () -> assertEquals(5000.00, savedForm1099.getWages()),
                () -> assertEquals(2020, savedForm1099.getYear())
        );
    }

    @Test
    void testFindAll() {
        List<Form1099> form1099s = Arrays.asList(new Form1099(), new Form1099());
        when(form1099Repository.findAll()).thenReturn(form1099s);

        List<Form1099> results = form1099Service.findAll();

        assertEquals(2, results.size(), "Should return a list of Form1099s");
    }

    @Test
    void testFindById() {
        int id = 1;
        Form1099 form1099 = new Form1099();
        when(form1099Repository.findById(id)).thenReturn(Optional.of(form1099));

        Form1099 found = form1099Service.findById(id);

        assertSame(form1099, found);
    }

    @Test
    void testFindById_NotFound() {
        int id = 1;
        when(form1099Repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> form1099Service.findById(id));
    }

    @Test
    void testFindAllByTaxReturnId() {
        int taxReturnId = 1;
        List<Form1099> form1099s = Arrays.asList(new Form1099(), new Form1099());
        when(form1099Repository.findAllByTaxReturnId(taxReturnId)).thenReturn(form1099s);

        List<Form1099> results = form1099Service.findAllByTaxReturnId(taxReturnId);

        assertEquals(2, results.size(), "Should return a list of Form1099s based on tax return ID");
    }

    @Test
    void testUpdate() {
        Form1099Dto dto = new Form1099Dto();
        dto.setId(1);
        dto.setPayer("Updated Payer");
        dto.setWages(6000.00);
        dto.setYear(2021);

        Form1099 form1099 = new Form1099();
        form1099.setPayer("Original Payer");
        form1099.setWages(5000.00);
        form1099.setYear(2020);

        when(form1099Repository.findById(dto.getId())).thenReturn(Optional.of(form1099));
        when(form1099Repository.save(form1099)).thenReturn(form1099);

        Form1099 updatedForm1099 = form1099Service.update(dto);

        assertAll(
                () -> assertEquals("Updated Payer", updatedForm1099.getPayer()),
                () -> assertEquals(6000.00, updatedForm1099.getWages()),
                () -> assertEquals(2021, updatedForm1099.getYear())
        );
    }

    @Test
    void testDeleteById() {
        int id = 1;
        doNothing().when(form1099Repository).deleteById(id);
        when(form1099Repository.existsById(id)).thenReturn(false);

        boolean deleted = form1099Service.deleteById(id);

        assertTrue(deleted, "Should return true when deletion is successful");
        verify(form1099Repository).deleteById(id);
    }
}
