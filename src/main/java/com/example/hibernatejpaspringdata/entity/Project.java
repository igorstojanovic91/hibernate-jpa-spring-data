package com.example.hibernatejpaspringdata.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_seq")
    @SequenceGenerator(name = "project_seq", sequenceName = "project_seq", initialValue = 1000, allocationSize = 50)
    private Integer id;
    private String name;

    @ManyToMany
    private Set<Employee> employees = new HashSet<>();

    //<editor-fold desc="Description">
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public void addEmployeeToProject(Employee employee) {
        this.employees.add(employee);
    }

    //</editor-fold>
}
