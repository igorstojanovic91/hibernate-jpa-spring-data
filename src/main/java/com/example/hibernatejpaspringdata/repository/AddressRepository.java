package com.example.hibernatejpaspringdata.repository;

import com.example.hibernatejpaspringdata.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
