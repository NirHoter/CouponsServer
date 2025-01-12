package com.nir.coupons.entity;


import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "purchases")
public class PurchaseEntity {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "customer_id", nullable = false)
    private int customerId;

    @Column(name = "coupon_id", nullable = false)
    private int couponId;

    @Column(name = "amount", nullable = false)
    private int amount;

    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;

    public PurchaseEntity() {
    }

    public PurchaseEntity(int id, int customerId, int couponId, int amount, Timestamp timestamp) {
        this.id = id;
        this.customerId = customerId;
        this.couponId = couponId;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
