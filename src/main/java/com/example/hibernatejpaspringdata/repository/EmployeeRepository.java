package com.example.hibernatejpaspringdata.repository;

import com.example.hibernatejpaspringdata.dto.EmployeeNameWithAddress;
import com.example.hibernatejpaspringdata.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findAllByAddressState(String state);

    List<Employee> lowestSalary();

    List<EmployeeNameWithAddress> findEmployeesByAddressIsNotNullOrderByName();

}
