package com.nir.coupons.entity;

import com.nir.coupons.dto.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customers")
public class CustomerEntity {

    @Id
    private int id;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Column(name = "address", length = 45)
    private String address;

    @Column(name = "amountOfKids")
    private Integer amountOfKids;

    @Column(name = "phone", length = 15)
    private String phone;

    public CustomerEntity() {
    }

    public CustomerEntity(int id, String name, String address, Integer amountOfKids, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.amountOfKids = amountOfKids;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAmountOfKids() {
        return amountOfKids;
    }

    public void setAmountOfKids(Integer amountOfKids) {
        this.amountOfKids = amountOfKids;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
