package com.example.hibernatejpaspringdata.repository;

import com.example.hibernatejpaspringdata.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
public class EmployeeRepositoryIT {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void createAndFind() {
        Employee employee = new Employee();
        employee.setName("Igor Stojanovic");
        employee.setSalary(80000L);

        employeeRepository.saveAndFlush(employee);

        List<Employee> employeeList = employeeRepository.findAll();

        assertEquals(1, employeeList.size());

    }
}
