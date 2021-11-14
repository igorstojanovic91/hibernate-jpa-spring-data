package com.example.hibernatejpaspringdata.repository;

import com.example.hibernatejpaspringdata.dto.DepartmentSalaryDTO;
import com.example.hibernatejpaspringdata.dto.EmployeeNameWithAddress;
import com.example.hibernatejpaspringdata.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class QueryTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    EntityManager em;

    @Test
    void findAllEmployeesInCantonZurich() {
        List<Employee> employeesInZH = employeeRepository.findAllByAddressState("ZH");
        assertFalse(employeesInZH.isEmpty());
        assertEquals(3, employeesInZH.size());
    }

    @Test
    void findAllZuercher() {
        TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e WHERE e.address.state = :state", Employee.class);

        query.setParameter("state", "ZH");

        List<Employee> resultListZH = query.getResultList();

        assertEquals(3, resultListZH.size());


    }

    @Test
    void calculateAverageSalaryPerDepartment() {
        List<DepartmentSalaryDTO> averageDepartmentSalary = departmentRepository.findAverageDepartmentSalary();

        assertFalse(averageDepartmentSalary.isEmpty());

        assertEquals(2, averageDepartmentSalary.size());

        for (DepartmentSalaryDTO statistics : averageDepartmentSalary) {
            if (statistics.name().equals("IT")) {
                assertEquals(97200.0, statistics.salary(), 0);
            }
            if (statistics.name().equals("HR")) {
                assertEquals(95000.0, statistics.salary(), 0);
            }
        }
    }

    @Test
    void employeeWithLowestSalary_SpringData() {
        List<Employee> employeeList = employeeRepository.lowestSalary();
        assertFalse(employeeList.isEmpty());
        assertEquals(2, employeeList.size());

        employeeList.forEach(e -> assertEquals(72000, e.getSalary()));

    }

    @Test
    void employeeWithLowestSalary() {
        TypedQuery<Employee> namedQuery = em.createNamedQuery(Employee.LOWEST_SALARY, Employee.class);
        List<Employee> employeeList = namedQuery.getResultList();

        assertFalse(employeeList.isEmpty());
        assertEquals(2, employeeList.size());

        employeeList.forEach(e -> assertEquals(72000, e.getSalary()));
    }

    @Test
    void findAllEmployeesWithCompleteAddressOrderByName() {
        List<EmployeeNameWithAddress> employeesByAddressIsNotNullOrderByName = employeeRepository.findEmployeesByAddressIsNotNullOrderByName();

        assertFalse(employeesByAddressIsNotNullOrderByName.isEmpty());

        employeesByAddressIsNotNullOrderByName.forEach(employeeNameWithAddress ->
                System.out.println(employeeNameWithAddress.getAddress()));

    }

    @Test
    void findEmployeesNotAssignedToProject() {
        TypedQuery<Employee> query = em.createQuery("SELECT e from Employee e WHERE e.projects IS EMPTY", Employee.class);

        List<Employee> list = query.getResultList();

        assertEquals(3, list.size());
    }

    @Test
    void findAllBusinessPhoneNumbersOrderedByNumber() {
        TypedQuery<String> query = em.createQuery("SELECT p.phonenumber FROM Phone p WHERE p.type = :type ORDER BY p.phonenumber", String.class);
        query.setParameter("type", PhoneType.WORK);

        List<String> resultList = query.getResultList();

        assertEquals(5, resultList.size());
    }

    @Test
    void findAllEmployeesWithoutBusinessPhone() {
        TypedQuery<Employee> query = em.createNamedQuery(Employee.WITHOUT_BUSINESS_PHONE, Employee.class);
        query.setParameter("type", PhoneType.WORK);

        List<Employee> resultList = query.getResultList();
        assertEquals(1, resultList.size());

    }

    /**
     * Not included in exercise 4
     */
    @Test
    void inheritanceOnBaseClass() {
        List<Project> list = em.createQuery("select p from Project p", Project.class).getResultList();

        assertEquals(2, list.size());

        assertTrue(list.get(0) instanceof DesignProject);
        assertTrue(list.get(1) instanceof QualityProject);
    }

    /**
     * Not included in exercise 4
     */
    @Test
    void inheritanceOnSubClass() {
        List<DesignProject> list = em.createQuery("select p from DesignProject p", DesignProject.class).getResultList();

        assertEquals(1, list.size());

        assertTrue(list.get(0) instanceof DesignProject);
    }



}
