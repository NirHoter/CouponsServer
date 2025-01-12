package com.nir.coupons.logic;

import com.nir.coupons.dal.CompaniesDal;
import com.nir.coupons.dto.Company;
import com.nir.coupons.enums.ErrorType;
import com.nir.coupons.exceptions.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompaniesLogic {
    private CompaniesDal companiesDal;
    private PurchasesLogic purchasesLogic;
    private CouponsLogic couponsLogic;
    private UserLogic userLogic;



    @Autowired
    public CompaniesLogic(CompaniesDal companiesDal, PurchasesLogic purchasesLogic, CouponsLogic couponsLogic, UserLogic userLogic) {
        this.companiesDal = companiesDal;
        this.purchasesLogic = purchasesLogic;
        this.couponsLogic = couponsLogic;
        this.userLogic = userLogic;
    }

    public int createCompany(Company company, String userType) throws ServerException {
        if (!userType.equals("Admin")) {
            throw new ServerException(ErrorType.INVALID_USER_TYPE, "This action allowed only to admin");
        }
        validateCompanies(company);
        return companiesDal.createCompany(company);
    }

    public void updateCompanies(Company company, String userType) throws ServerException {
        if (!userType.equals("Admin")) {
            throw new ServerException(ErrorType.INVALID_USER_TYPE, "This action allowed only to admin");
        }
        validateCompanies(company);
        companiesDal.updateCompany(company);
    }

    public List<Company> getCompanies() throws ServerException {
        return companiesDal.getCompanies();
    }

    public Company getCompany(int id) throws ServerException {
        return companiesDal.getCompany(id);
    }

    public void deleteCompany(int id, String userType) throws ServerException {
        if (!userType.equals("Admin")) {
            throw new ServerException(ErrorType.INVALID_USER_TYPE, "This action allowed only to admin");
        }
        purchasesLogic.deletePurchaseByCompanyId(id);
        couponsLogic.deleteCouponByCompanyId(id);
        userLogic.deleteUsersByCompanyId(id);
        companiesDal.deleteCompany(id);

    }

    private void validateCompanies(Company company) throws ServerException {
        if (company.getName() == null && company.getName().length() > 45) {
            throw new ServerException(ErrorType.INVALID_NAME, company.toString());
        }

        if (company.getPhone() != null && (company.getPhone().length() < 9 || company.getPhone().length() > 15)) {
            throw new ServerException(ErrorType.INVALID_PHONE_NUMBER, company.toString());
        }

        if (company.getAddress() != null && company.getAddress().length() > 45) {
            throw new ServerException(ErrorType.INVALID_ADDRESS, company.toString());
        }
    }
}
