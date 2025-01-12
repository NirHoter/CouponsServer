package com.nir.coupons.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "coupons")
public class CouponEntity {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "title", nullable = false, length = 45)
    private String title;

    @Column(name = "description", nullable = false, length = 200)
    private String description;

    @Column(name = "price", nullable = false)
    private float price;

    @Column(name = "company_id", nullable = false)
    private int companyId;

    @Column(name = "category_id", nullable = false)
    private int categoryId;

    @Column(name = "startDate", nullable = false)
    private Date startDate;

    @Column(name = "endDate", nullable = false)
    private Date endDate;

    @Column(name = "amount", nullable = false)
    private int amount;

    @Column(name = "image_url")
    private String imageURL;

    public CouponEntity() {
    }

    public CouponEntity(int id, String title, String description, float price, int companyId, int categoryId, Date startDate, Date endDate, int amount, String imageURL) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.companyId = companyId;
        this.categoryId = categoryId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.imageURL = imageURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
