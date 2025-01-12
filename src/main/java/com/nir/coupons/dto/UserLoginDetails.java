package com.nir.coupons.dto;

public class UserLoginDetails {

    private int id;
    private String userType;
    private int companyId;

    public UserLoginDetails(int id, String userType, int companyId) {
        this.id = id;
        this.userType = userType;
        this.companyId = companyId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
}
