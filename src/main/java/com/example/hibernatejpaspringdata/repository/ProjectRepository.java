package com.example.hibernatejpaspringdata.repository;

import com.example.hibernatejpaspringdata.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
}
