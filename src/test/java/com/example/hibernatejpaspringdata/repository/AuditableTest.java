package com.example.hibernatejpaspringdata.repository;

import com.example.hibernatejpaspringdata.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuditableTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EntityManager em;

    @Test
    void findAllEmployees() {
        List<Employee> employeeRepositoryAll = employeeRepository.findAll();

        assertFalse(employeeRepositoryAll.isEmpty());
        employeeRepositoryAll.forEach(employee -> assertEquals((Integer) 0, (Integer) employee.getVersion()));
    }

}
