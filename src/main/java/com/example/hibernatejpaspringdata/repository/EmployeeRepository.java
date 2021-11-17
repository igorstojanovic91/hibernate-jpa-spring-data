package com.example.hibernatejpaspringdata.repository;

import com.example.hibernatejpaspringdata.dto.EmployeeNameWithAddress;
import com.example.hibernatejpaspringdata.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import javax.transaction.Transactional;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>, JpaSpecificationExecutor<Employee> {

    List<Employee> findAllByAddressState(String state);

    List<Employee> lowestSalary();

    List<EmployeeNameWithAddress> findEmployeesByAddressIsNotNullOrderByName();

    <T> List<T> findAllByAddressState(String state, Class<T> type);


}
