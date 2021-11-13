package com.example.hibernatejpaspringdata.repository;

import com.example.hibernatejpaspringdata.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EntityManager em;

    @BeforeEach
    void clearDB() {
        employeeRepository.deleteAll();
    }

    @Test
    void createAndSaveEmployee_Success() {
        Employee igor = getEmployee();

        employeeRepository.saveAndFlush(igor);

        List<Employee> employees = employeeRepository.findAll();

        assertEquals(1, employees.size());
    }

    @Test
    void createAndSaveEmployee_FindIt() {
        Employee igor = getEmployee();

        employeeRepository.saveAndFlush(igor);

        Optional<Employee> employee = employeeRepository.findById(1);

        assertEquals("Igor Stojanovic", employee.get().getName());
    }


    private Employee getEmployee() {
        Employee igor = new Employee();
        igor.setId(1);
        igor.setName("Igor Stojanovic");
        igor.setSalary(120000L);
        return igor;
    }

}