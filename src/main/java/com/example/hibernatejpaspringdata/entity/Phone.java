package com.example.hibernatejpaspringdata.entity;

import javax.persistence.*;

@Entity
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phone_seq")
    @SequenceGenerator(name = "phone_seq", sequenceName = "phone_seq", initialValue = 1000, allocationSize = 50)
    private Integer id;
    private String phonenumber;
    @Enumerated(EnumType.STRING)
    private PhoneType type;

    @ManyToOne(optional = false)
    private Employee employee;

    //<editor-fold desc="getter and setter">
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phoneNumber) {
        this.phonenumber = phoneNumber;
    }

    public PhoneType getType() {
        return type;
    }

    public void setType(PhoneType phoneType) {
        this.type = phoneType;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    //</editor-fold>
}

