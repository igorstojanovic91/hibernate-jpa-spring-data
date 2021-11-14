package com.example.hibernatejpaspringdata.repository;

import com.example.hibernatejpaspringdata.dto.DepartmentSalaryDTO;
import com.example.hibernatejpaspringdata.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    @Query("select new com.example.hibernatejpaspringdata.dto.DepartmentSalaryDTO(e.department.name, AVG(e.salary))" +
            " FROM Employee e GROUP BY e.department.name")
    List<DepartmentSalaryDTO> findAverageDepartmentSalary();
}
