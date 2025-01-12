package com.nir.coupons.controllers;

import com.nir.coupons.dto.Company;
import com.nir.coupons.dto.UserLoginDetails;
import com.nir.coupons.exceptions.ServerException;
import com.nir.coupons.logic.CompaniesLogic;
import com.nir.coupons.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompaniesController {

    private CompaniesLogic companiesLogic;
    private UserLoginDetails userLoginDetails;

    @Autowired
    public CompaniesController(CompaniesLogic companiesLogic) {
        this.companiesLogic = companiesLogic;
    }

    @GetMapping("/{id}")
    public Company getCompanyById(@PathVariable("id") int companyId) throws ServerException {
        return this.companiesLogic.getCompany(companyId);
    }

    @PostMapping
    public void createCompany(@RequestHeader ("Authorization") String token, @RequestBody Company company) throws Exception {
        userLoginDetails = JWTUtils.decodeJWT(token);
        this.companiesLogic.createCompany(company, userLoginDetails.getUserType());
    }

    @PutMapping
    public void updateCompany(@RequestHeader ("Authorization") String token, @RequestBody Company company) throws Exception {
        userLoginDetails = JWTUtils.decodeJWT(token);
        this.companiesLogic.updateCompanies(company, userLoginDetails.getUserType());
    }

    //admin
    @DeleteMapping("/{id}")
    public void deleteCompany(@RequestHeader("Authorization") String token  , @PathVariable("id") int id) throws Exception {
        userLogin = JWTUtils.decodeJWT(token);
        this.companiesLogic.deleteCompany(id, userLogin.getUserType());
    }

    @GetMapping
    public Page<Company> getCompanies(@RequestParam(defaultValue = "0")) int page,
                                      @RequestParam(defaultValue = "10") int size throws ServerException {
        return this.companiesLogic.getCompanies(page, size);
    }
}
