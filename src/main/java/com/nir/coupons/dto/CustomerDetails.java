package com.nir.coupons.dto;

public class CustomerDetails {

    private String name;
    private String userName;
    private String address;
    private String phoneNumber;
    private int amountOfKids;

    public CustomerDetails() {

    }

    public CustomerDetails(String name, String userName, String address, String phoneNumber, int amountOfKids) {
        this.name = name;
        this.userName = userName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.amountOfKids = amountOfKids;
    }

    public String getName() {
        return name;
    }

    public String getUserName() {
        return userName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getAmountOfKids() {
        return amountOfKids;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAmountOfKids(int amountOfKids) {
        this.amountOfKids = amountOfKids;
    }

    @Override
    public String toString() {
        return "CustomerDetails{" +
                "name='" + name + '\'' +
                ", userName='" + userName + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", amountOfKids=" + amountOfKids +
                '}';
    }
}
