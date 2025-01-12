package com.nir.coupons.controllers;


import com.nir.coupons.dto.Customer;
import com.nir.coupons.dto.CustomerDetails;
import com.nir.coupons.dto.UserLoginDetails;
import com.nir.coupons.exceptions.ServerException;
import com.nir.coupons.logic.CustomersLogic;
import com.nir.coupons.logic.UserLogic;
import com.nir.coupons.utils.JWTUtils;
import com.oracle.xmlns.internal.webservices.jaxws_databinding.JavaWsdlMappingType;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private UserLogic userLogic;
    private CustomersLogic customersLogic;
    private UserLoginDetails userLoginDetails;

    @Autowired
    public CustomerController(UserLogic userLogic, CustomersLogic customersLogic) {
        this.userLogic = userLogic;
        this.customersLogic = customersLogic;
    }

    @GetMapping("/MyCustomer")
    public CustomerDetails getMyCustomer(@RequestHeader("Authorization") String token) throws Exception {
        userLoginDetails = JWTUtils.decodeJWT(token);
        return this.customersLogic.getCustomer(userLoginDetails.getUserId());
    }

    @GetMapping("{id}")
    public CustomerDetails getCustomerByAdmin(@RequestHeader("Authorization") String token,@PathVariable("id") int id) throws Exception {
        userLoginDetails = JWTUtils.decodeJWT(token);
        return this.customersLogic.getCustomerByAdmin(id,userLoginDetails.getUserType());
    }

    @PostMapping
    public void createCustomer(@RequestBody Customer customer) throws ServerException, MessagingException {
        this.customersLogic.createCustomer(customer);
    }

    @PutMapping
    public void updateCustomer(@RequestHeader("Authorization") String token , @RequestBody Customer customer) throws Exception {
        userLoginDetails = JWTUtils.decodeJWT(token);
        customer.setId(userLoginDetails.getUserId());
        customer.getUser().setId(userLoginDetails.getUserId());
        customer.getUser().setUserType("Customer");
        customer.getUser().setCompanyId(null);
        this.customersLogic.updateCustomer(customer);
        this.userLogic.updateUser(customer.getUser());
    }


    @DeleteMapping()
    public void deleteMyCustomer(@RequestHeader("Authorization") String token) throws Exception {
        userLoginDetails = JWTUtils.decodeJWT(token);

        this.customersLogic.deleteCustomer(userLoginDetails.getUserId());
    }

    //TODO עובד
    @DeleteMapping("/deleteByAdmin/{id}")
    public void deleteCustomerByAdmin(@RequestHeader("Authorization") String token, @PathVariable("id") int id) throws Exception {
        userLoginDetails = JWTUtils.decodeJWT(token);
        this.customersLogic.deleteCustomerByAdmin(id,userLoginDetails.getUserType());
    }

    @GetMapping
    public Page<CustomerDetails> getCustomers(@RequestHeader("Authorization") String token,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) throws Exception {
        userLoginDetails = JWTUtils.decodeJWT(token);
        return this.customersLogic.getCustomers(userLoginDetails.getUserType(),page,size);
    }
}
