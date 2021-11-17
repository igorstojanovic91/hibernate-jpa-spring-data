package com.example.hibernatejpaspringdata.repository;

import com.example.hibernatejpaspringdata.entity.Department;
import org.junit.function.ThrowingRunnable;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@SpringBootTest
public class OptimisticLockIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(OptimisticLockIT.class);

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EntityManager entityManager;

    @Test()
    public void lostUpdate() throws InterruptedException {
        Department department = new Department();
        department.setName("HR");
        department = departmentRepository.saveAndFlush(department);

        Integer id = department.getId();
        Updater u1 = new Updater(id, 1000, "No Effect");
        Updater u2 = new Updater(id, 0, "Set up Version");

        u1.start();
        u2.start();

        try {
            u1.join();
            u2.join();
        } catch (Exception e) {
            assertEquals(ObjectOptimisticLockingFailureException.class, e.getClass());
        }

    }


    public class Updater extends Thread {

        private final Integer id;
        private final int sleepTime;
        private final String text;

        Updater(Integer id, int sleepTime, String text) {
            this.id = id;
            this.sleepTime = sleepTime;
            this.text = text;
        }

        @Override
        public void run() {
            try {
                Optional<Department> departmentOptional = departmentRepository.findById(id);
                Department department = departmentOptional.get();
                department.setName(text);

                departmentRepository.saveAndFlush(department);

                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }

        }
    }
}

