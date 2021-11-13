package com.example.hibernatejpaspringdata.repository;

import com.example.hibernatejpaspringdata.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
