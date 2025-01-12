package com.nir.coupons.dto;

import java.sql.Timestamp;
import java.util.Date;

public class Purchase {

    private int id;
    private int customerId;
    private int couponId;
    private int amount;
    private Timestamp timestamp;

    public Purchase() {

    }

    public Purchase(int id, int customerId, int couponId, int amount, Timestamp timestamp) {
        this(customerId, couponId, amount, timestamp);
        this.id = id;
    }

    public Purchase(int customerId, int couponId, int amount, Timestamp timestamp) {
        this.customerId = customerId;
        this.couponId = couponId;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getCouponId() {
        return couponId;
    }

    public int getAmount() {
        return amount;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", couponId=" + couponId +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                '}';
    }
}
