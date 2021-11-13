package com.example.hibernatejpaspringdata.repository;

import com.example.hibernatejpaspringdata.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RelationshipTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EntityManager em;


    @BeforeEach
    void createsFullModel() {
        Employee nicole = new Employee();
        nicole.setName("Nicole Stojanovic");
        nicole.setSalary(200000L);
        employeeRepository.saveAndFlush(nicole);

        Address nicolesAddress = new Address();
        nicolesAddress.setStreet("Stauffacherstrasse 27");
        nicolesAddress.setState("BE");
        nicolesAddress.setZip("3014");
        nicolesAddress.setCity("Bern");

        Project p1 = new Project();
        p1.setName("Ferien");
        p1.addEmployeeToProject(nicole);
        Project p2 = new Project();
        p2.setName("Reisen");
        p2.addEmployeeToProject(nicole);
        Set<Project> projectList = new HashSet<>() {
            {
                add(p1);
                add(p2);
            }
        };

        projectRepository.saveAllAndFlush(projectList);

        Phone phone = new Phone();
        phone.setPhonenumber("0792613050");
        phone.setType(PhoneType.MOBILE);
        phone.setEmployee(nicole);


        Department department = new Department();
        department.setName("IT");
        department.addEmployee(nicole);
        departmentRepository.saveAndFlush(department);

        Employee igor = new Employee();
        igor.setSalary(120000L);
        igor.setName("Igor Stojanovic");


        igor.setBoss(nicole);
        nicole.addPhone(phone);
        nicole.setAddress(nicolesAddress);
        nicole.setProjects(projectList);

        employeeRepository.saveAndFlush(nicole);


    }

    @Test
    void oneEntrySavedInDB() {
        List<Employee> all = employeeRepository.findAll();

        assertEquals(1, all.size());

    }

    @Test
    void findNicole_shouldWork() {
        List<Employee> employees = employeeRepository.findAll();
        Employee nicole = employees.get(0);


        assertEquals("Nicole Stojanovic", nicole.getName());
    }


    @Test
    void cascadePhone_shouldWork() {
        List<Employee> employees = employeeRepository.findAll();
        Employee nicole = employees.get(0);


        assertEquals("0792613050", nicole.getPhones().get(0).getPhonenumber());

    }

    @Test
    void cascadeAddress_shouldWork() {
        List<Employee> employees = employeeRepository.findAll();
        Employee nicole = employees.get(0);

        assertEquals("Bern", nicole.getAddress().getCity());
    }

    @Test
    void orphanRemoval_shouldWork() {
        List<Employee> employees = employeeRepository.findAll();
        Employee nicole = employees.get(0);
        Phone phone = nicole.getPhones().get(0);
        nicole.getPhones().remove(phone);

        employeeRepository.saveAndFlush(nicole);

        em.refresh(nicole);

        assertEquals(0, nicole.getPhones().size());

    }


}
