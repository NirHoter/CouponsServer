package com.nir.coupons.entity;

import javax.persistence.*;


@Entity
@Table(name = "categories")
public class CategoryEntity {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "name", unique = true, nullable = false, length = 45)
    private String name;

    public CategoryEntity() {
    }

    public CategoryEntity(int id, String name) {
        this.id = id;
        this.name = name;
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
}
