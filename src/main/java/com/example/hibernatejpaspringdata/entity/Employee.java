package com.example.hibernatejpaspringdata.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@NamedQuery(name = Employee.LOWEST_SALARY,
        query = """
                            SELECT e FROM Employee e WHERE e.salary = (SELECT MIN(e.salary) FROM Employee e)

                """)
@NamedQuery(name = Employee.WITHOUT_BUSINESS_PHONE,
query = "SELECT e from Employee e  WHERE e NOT IN ( SELECT DISTINCT e FROM Employee e join e.phones p WHERE p.type = :type)")
@Entity
public class Employee {

    public static final String LOWEST_SALARY = "Employee.lowestSalary";
    public static final String WITHOUT_BUSINESS_PHONE = "Employee.withoutBusinessPhone";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_seq")
    @SequenceGenerator(name = "employee_seq", sequenceName = "employee_seq", initialValue = 1000, allocationSize = 50)
    private Integer id;
    private String name;
    private Long salary;

    @ManyToOne
    private Employee boss;

    @OneToMany(mappedBy = "boss")
    private Set<Employee> directs = new HashSet<>();

    @OneToMany(mappedBy = "employee",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @OrderBy("type")
    private List<Phone> phones = new ArrayList<>();

    @ManyToMany(mappedBy = "employees")
    private Set<Project> projects = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;

    //<editor-fold desc="getter and setter">
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

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public Employee getBoss() {
        return boss;
    }

    public void setBoss(Employee boss) {
        this.boss = boss;
    }

    public Set<Employee> getDirects() {
        return directs;
    }

    public void setDirects(Set<Employee> directs) {
        this.directs = directs;
    }

    public void addDirects(Employee employee) {
        this.directs.add(employee);
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public void addPhone(Phone phone) {
        this.phones.add(phone);
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public void addProject(Project project) {
        this.projects.add(project);
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    //</editor-fold>

}
