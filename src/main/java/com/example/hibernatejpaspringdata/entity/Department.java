package com.example.hibernatejpaspringdata.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Department extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_seq")
    @SequenceGenerator(name = "department_seq", sequenceName = "department_seq", initialValue = 1000, allocationSize = 50)
    private Integer id;
    private String name;
    @OneToMany(mappedBy = "department")
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

    public void addEmployee(Employee employee) {
        this.employees.add(employee);
    }

    //</editor-fold>
}
