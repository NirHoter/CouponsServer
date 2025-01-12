package com.nir.coupons.logic;

import com.nir.coupons.dal.CustomersDal;
import com.nir.coupons.dto.Customer;
import com.nir.coupons.dto.CustomerDetails;
import com.nir.coupons.dto.UserLoginDetails;
import com.nir.coupons.enums.ErrorType;
import com.nir.coupons.exceptions.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomersLogic {

    private UserLogic userLogic;
    private CustomersDal customersDal;
    private PurchasesLogic purchasesLogic;

    @Autowired
    public CustomersLogic(UserLogic userLogic, CustomersDal customersDal, PurchasesLogic purchasesLogic) {
        this.userLogic = userLogic;
        this.customersDal = customersDal;
        this.purchasesLogic = purchasesLogic;
    }


    public void createCustomer(Customer customer) throws ServerException {
        validateCustomers(customer);
        int userId = this.userLogic.createUser(customer.getUser());
        customer.setId(userId);
        this.customersDal.createCustomer(customer);
    }

    public void updateCustomer(Customer customer) throws ServerException {
        validateCustomers(customer);
        customersDal.updateCustomer(customer);
    }

    public List<CustomerDetails> getCustomers(String userType) throws ServerException {
        if (!userType.equals("Admin")) {
            throw new ServerException(ErrorType.INVALID_USER_TYPE, "This action allowed only to admin");
        }
        return customersDal.getCustomers();
    }

    public CustomerDetails getCustomer(int id) throws ServerException {
        return customersDal.getCustomerDetails(id);
    }

    public void deleteCustomer(int id, UserLoginDetails userLoginDetails) throws ServerException {
        if (id != userLoginDetails.getId()) {
            throw new ServerException(ErrorType.GENERAL_ERROR);
        }
        purchasesLogic.deletePurchasesByUserId(id);
        userLogic.deleteUser(id);
        customersDal.deleteCustomer(id);
    }

    private void validateCustomers(Customer customer) throws ServerException {
        if (customer.getName() == null && customer.getName().length() > 45) {
            throw new ServerException(ErrorType.INVALID_NAME, customer.toString());
        }

        if (customer.getPhone() != null && (customer.getPhone().length() < 9 || customer.getPhone().length() > 15)) {
            throw new ServerException(ErrorType.INVALID_PHONE_NUMBER, customer.toString());
        }

        if (customer.getAddress() != null && customer.getAddress().length() > 45) {
            throw new ServerException(ErrorType.INVALID_ADDRESS, customer.toString());
        }
    }

    public CustomerDetails getCustomerByAdmin(String userType, int customerId) throws ServerException {
        if (!userType.equals("Admin")) {
            throw new ServerException(ErrorType.INVALID_USER_TYPE, "This action allowed only for admin");
        }
        return customersDal.getCustomerDetails(customerId);
    }
}
