package com.nir.coupons.entity;


import javax.persistence.*;

@Entity
@Table(name = "companies")
public class CompanyEntity {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "name", unique = true, nullable = false, length = 45)
    private String name;

    @Column(name = "address", length = 45)
    private String address;

    @Column(name = "phone", length = 45)
    private String phone;

    public CompanyEntity() {
    }

    public CompanyEntity(int id, String name, String address, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
