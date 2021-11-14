package com.example.hibernatejpaspringdata.repository;

import com.example.hibernatejpaspringdata.dto.DepartmentSalaryDTO;
import com.example.hibernatejpaspringdata.entity.*;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

import static org.junit.Assert.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CriteriaTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EntityManager em;

    @Test
    void findAllZuercher() {
        List<Employee> zuercher = employeeRepository.findAll((employee, cq, cb) -> {
            Join<Employee, Address> address = employee.join(Employee_.address);
            return cb.equal(address.get(Address_.state), "ZH");
        });

        assertEquals(3, zuercher.size());
    }

    @Test
    void getAverageSalaryPerDepartment() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<DepartmentSalaryDTO> criteriaQuery = criteriaBuilder.createQuery(DepartmentSalaryDTO.class);

        Root<Department> department = criteriaQuery.from(Department.class);
        SetJoin<Department, Employee> employee = department.join(Department_.employees);

        Expression<Double> avg = criteriaBuilder.avg(employee.get(Employee_.salary));
        criteriaQuery.groupBy(department.get(Department_.name));


        criteriaQuery.select(
                criteriaBuilder.construct(DepartmentSalaryDTO.class,
                        department.get(Department_.name), avg)
        );

        TypedQuery<DepartmentSalaryDTO> query = em.createQuery(criteriaQuery);

        List<DepartmentSalaryDTO> resultList = query.getResultList();

        assertEquals(2, resultList.size());

    }

    @Test
    void findAllEmployeesWithoutProject() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);

        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);

        criteriaQuery.where(criteriaBuilder.isEmpty(employeeRoot.get(Employee_.projects)));
        criteriaQuery.select(employeeRoot);

        TypedQuery<Employee> query = em.createQuery(criteriaQuery);
        List<Employee> list = query.getResultList();

        assertEquals(3, list.size());

    }


}
