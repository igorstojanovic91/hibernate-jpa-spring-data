package com.example.hibernatejpaspringdata.repository;

import com.example.hibernatejpaspringdata.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
