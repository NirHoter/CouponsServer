package com.nir.coupons.dto;

public class SendStatisticsThreads extends Thread {

    private String userName;
    private String actionType;

    public SendStatisticsThreads(String userName, String actionType) {
        this.userName = userName;
        this.actionType = actionType;
    }

    public void run() {
        System.out.println("User name is " + userName + "User type of action is " + actionType);
    }

    public String getUserName() {
        return userName;
    }

    public String getActionType() {
        return actionType;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }
}

