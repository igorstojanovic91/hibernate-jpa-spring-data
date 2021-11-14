package com.example.hibernatejpaspringdata.repository;

import com.example.hibernatejpaspringdata.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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

    private Integer peterMusterId;


    @Test
    void EntriesFromScriptSavedInDB() {
        List<Employee> all = employeeRepository.findAll();

        assertEquals(8, all.size());

    }

    @Test
    void findUrsula_shouldWork() {
        List<Employee> employees = employeeRepository.findAll();
        Employee nicole = employees.get(0);


        assertEquals("Ursula Friedman", nicole.getName());
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

    @Test
    void lazyLoading() {
        Employee ursula = employeeRepository.findById(1).orElseThrow();

        List<Phone> phones = ursula.getPhones();
        assertFalse(phones.isEmpty());

    }

    @BeforeEach
    public void createTestData() {
        Employee bigBoss = new Employee();
        bigBoss.setName("Big Boss");
        bigBoss.setSalary(100000L);

        // Immer den Rückgabewert von save() weiterverwenden!
        bigBoss = employeeRepository.saveAndFlush(bigBoss);

        Project project = new DesignProject();
        project.setName("Bookstore");
        project = projectRepository.saveAndFlush(project);

        Department department = new Department();
        department.setName("IT");
        department = departmentRepository.saveAndFlush(department);

        Address address = new Address();
        address.setStreet("Bahnhofstrasse 42");
        address.setZip("8000");
        address.setCity("Zürich");
        address.setState("ZH");

        Employee peterMuster = new Employee();
        peterMuster.setName("Peter Muster");
        peterMuster.setSalary(80000L);

        peterMuster.setDepartment(department);
        peterMuster.setAddress(address);

        peterMuster.setBoss(bigBoss);
        // Rückbeziehung auch setzen beim Erzeugen
        bigBoss.addDirects(peterMuster);

        // Beide Seiten der Beziehung abfüllen
        peterMuster.getProjects().add(project);
        project.getEmployees().add(peterMuster);

        Phone phone = new Phone();
        phone.setType(PhoneType.MOBILE);
        phone.setPhonenumber("079 123 45 67");

        peterMuster.addPhone(phone);
        phone.setEmployee(peterMuster);

        // Immer den Rückgabewert von save() weiterverwenden!
        peterMuster = employeeRepository.saveAndFlush(peterMuster);
        peterMusterId = peterMuster.getId();

        em.clear();
    }


}
