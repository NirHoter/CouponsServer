package com.nir.coupons.enums;

public enum ErrorType {
    GENERAL_ERROR(1000, "A general error has occurred please try again later"),

    UNAUTHORIZED(1001, "Invalid user name or password"),

    INVALID_COUPON_DESCRIPTION(1002, "Invalid coupon description"),
    INVALID_COUPON_AMOUNT(1003, "You have to create at least one coupon"),
    INVALID_COUPON_PRICE(1004, "The price must be greater than 0"),
    INVALID_COMPANY_ID(1005, "Invalid company ID, company ID can not be null and must be bigger than 0"),
    INVALID_CATEGORY_ID(1006, "Invalid category ID, category ID does not exists"),
    INVALID_TITLE(1007, "Invalid title, The title must contain between 1 and 45 characters"),
    INVALID_DATE(1008, "Start and End date can not be null"),
    INVALID_START_DATE(1009, "Start date must be earlier than the End date"),
    INVALID_IMAGE_URL_LENGTH(1010, "Invalid ImageURL, The ImageURL must contain less than 100 characters"),
    INVALID_USER_NAME_LENGTH(1011, "Invalid user name, User name must contain between 1 and 45 characters"),
    INVALID_PASSWORD(1012, "Invalid password, Password must contain between 1 and 45 characters"),
    INVALID_USER_TYPE(1013, "Invalid user type, User Type must be Customer/Company/Admin , and must to contains between 1 - 45 characters"),
    INVALID_CATEGORY_NAME(1014, "Invalid category name, Category name must contain between 1 and 45 characters"),
    INVALID_NAME(1015, "Invalid name, name can not contain more than 45 characters"),
    INVALID_PHONE_NUMBER(1016, "Invalid phone number, Phone number must contain between 9 and 15 characters"),
    INVALID_ADDRESS(1017, "Invalid  address, address can not contain more than 45 characters"),
    INVALID_PURCHASES_AMOUNT(1018, "You have to choose at least one coupon"),
    INVALID_USER_NAME(1019, "User name must be a valid email pattern"),
    INVALID_COUPON_AMOUNT_AFTER_PURCHASE(1020, "Invalid coupons amount, The quantity of coupons you are trying to buy does not exist"),
    EXPIRED_DATE_PURCHASE(1021, " the coupon is expired"),
    USER_NAME_ALREADY_EXIST(1021, "This user name is already exist");

    private int internalError;
    private String clientErrorMessage;

    ErrorType(int internalError, String clientErrorMessage) {
        this.internalError = internalError;
        this.clientErrorMessage = clientErrorMessage;
    }

    public int getInternalError() {
        return internalError;
    }

    public String getClientErrorMessage() {
        return clientErrorMessage;
    }
}
