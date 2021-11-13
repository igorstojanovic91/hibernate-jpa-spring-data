package com.example.hibernatejpaspringdata.repository;

import com.example.hibernatejpaspringdata.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<Phone, Integer> {
}
