package com.example.hibernatejpaspringdata.dto;

import java.util.List;

public interface EmployeeWithPhones {

    String getName();

    List<PhoneNumber> getPhoneNumbers();

    interface PhoneNumber {
        String getPhonenumber();
    }

}
