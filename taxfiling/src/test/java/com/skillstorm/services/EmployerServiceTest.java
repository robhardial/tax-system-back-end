package com.skillstorm.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.skillstorm.models.Employer;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.skillstorm.respositories.EmployerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EmployerServiceTest {

    @Mock
    private EmployerRepository employerRepository;

    @InjectMocks
    private EmployerService employerService;

    @Test
    public void testFindAllEmployers() {
        List<Employer> expectedEmployers = Arrays.asList(new Employer(), new Employer());
        when(employerRepository.findAll()).thenReturn(expectedEmployers);

        List<Employer> actualEmployers = employerService.findAllEmployers();

        assertSame(expectedEmployers, actualEmployers, "Should return all employers");
    }

    @Test
    public void testFindEmployerById_found() {
        int id = 1;
        Employer expectedEmployer = new Employer();
        when(employerRepository.findById(id)).thenReturn(Optional.of(expectedEmployer));

        Employer actualEmployer = employerService.findEmployerById(id);

        assertSame(expectedEmployer, actualEmployer, "Should return the correct employer");
    }

    @Test
    public void testFindEmployerById_notFound() {
        int id = 1;
        when(employerRepository.findById(id)).thenReturn(Optional.empty());

        Employer actualEmployer = employerService.findEmployerById(id);

        assertNull(actualEmployer, "Should return null when employer is not found");
    }

    @Test
    public void testSaveEmployer() {
        Employer employer = new Employer();
        when(employerRepository.save(employer)).thenReturn(employer);

        Employer savedEmployer = employerService.saveEmployer(employer);

        assertSame(employer, savedEmployer, "Should save and return the employer");
    }

    @Test
    public void testEditEmployer_existing() {
        int id = 1;
        Employer existingEmployer = new Employer();
        existingEmployer.setEin("123456789");
        Employer updatedDetails = new Employer();
        updatedDetails.setEin("987654321");

        when(employerRepository.findById(id)).thenReturn(Optional.of(existingEmployer));
        when(employerRepository.save(existingEmployer)).thenReturn(existingEmployer);

        Employer updatedEmployer = employerService.editEmployer(id, updatedDetails);

        assertSame(existingEmployer, updatedEmployer, "Should update and return the employer");
        assertEquals("987654321", existingEmployer.getEin(), "Should update the EIN correctly");
    }

    @Test
    public void testEditEmployer_nonExisting() {
        int id = 1;
        Employer newEmployer = new Employer();

        when(employerRepository.findById(id)).thenReturn(Optional.empty());
        when(employerRepository.save(newEmployer)).thenReturn(newEmployer);

        Employer createdEmployer = employerService.editEmployer(id, newEmployer);

        assertSame(newEmployer, createdEmployer, "Should create and return the new employer");
    }

    @Test
    public void testDeleteEmployerById() {
        int id = 1;

        doNothing().when(employerRepository).deleteById(id);

        employerService.deleteEmployerById(id);

        verify(employerRepository).deleteById(id);
    }
}
