package com.nir.coupons.logic;

import com.nir.coupons.dal.PurchasesDal;
import com.nir.coupons.dto.Coupon;
import com.nir.coupons.dto.Purchase;
import com.nir.coupons.dto.PurchaseDetails;
import com.nir.coupons.enums.ErrorType;
import com.nir.coupons.exceptions.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchasesLogic {

    private PurchasesDal purchasesDal;
    @Autowired
    private CouponsLogic couponsLogic;

    @Autowired
    public PurchasesLogic(PurchasesDal purchasesDal) {
        this.purchasesDal = purchasesDal;
    }

    public int createPurchases(Purchase purchase, String userType) throws ServerException {
        validatePurchases(purchase);
        couponsLogic.decreaseCouponAmount(purchase.getAmount(), purchase.getCouponId());
        return purchasesDal.createPurchase(purchase);
    }

    public void updatePurchases(Purchase purchase) throws ServerException {
        validatePurchases(purchase);
        purchasesDal.updatePurchase(purchase);
    }

    public List<Purchase> getPurchases() throws ServerException {
        return purchasesDal.getPurchases();
    }

    public Purchase getPurchase(int id) throws ServerException {
        return purchasesDal.getPurchase(id);
    }

    public void deletePurchase(int id, String userType) throws ServerException {
        Purchase purchase = getPurchase(id);
        couponsLogic.increaseCouponAmount(purchase.getAmount(), purchase.getCouponId());
        purchasesDal.deletePurchase(id);
    }

    public PurchaseDetails getPurchaseDetailsById(int id) throws ServerException {
        return purchasesDal.getPurchaseDetailsById(id);
    }

    public List<PurchaseDetails> getPurchaseDetailsByCustomerId(int customerId) throws ServerException {
        return purchasesDal.getPurchasesByCustomerId(customerId);
    }

    public List<PurchaseDetails> getPurchasesByCategoryIdOfCustomer(int customerId, String categoryName) throws ServerException {
        return purchasesDal.getPurchasesByCategoryOfCustomer(customerId, categoryName);
    }

    public List<PurchaseDetails> getPurchasesByCategoryOfCompany(int companyId, String categoryName) throws ServerException {
        return purchasesDal.getPurchasesByCategoryOfCompany(companyId, categoryName);
    }

    public List<PurchaseDetails> getPurchasesDetailsByPrice(int customerId, int price) throws ServerException {
        return purchasesDal.getPurchasesDetailsByPrice(customerId, price);
    }


    private void validatePurchases(Purchase purchase) throws ServerException {

        Coupon coupon = couponsLogic.getCoupon(purchase.getCouponId());

        if (purchase.getTimestamp().after(coupon.getEndDate())) {
            throw new ServerException(ErrorType.EXPIRED_DATE_PURCHASE, purchase.toString());
        }
        if (purchase.getAmount() < 1) {
            throw new ServerException(ErrorType.INVALID_PURCHASES_AMOUNT, purchase.toString());
        }
        if (!isCouponAmountSufficient(purchase)) {
            throw new ServerException(ErrorType.INVALID_COUPON_AMOUNT_AFTER_PURCHASE, purchase.toString());
        }
    }

    private boolean isCouponAmountSufficient(Purchase purchase) throws ServerException {
        Coupon coupon = couponsLogic.getCoupon(purchase.getCouponId());
        if (coupon == null || coupon.getAmount() < purchase.getAmount()) {
            return false;
        }
        return true;
    }

    void deletePurchaseByCompanyId(int id) throws ServerException {
        purchasesDal.deletePurchasesByCompanyId(id);
    }

    public void deletePurchasesByCouponId(int couponId) throws ServerException {
        purchasesDal.deletePurchasesByCouponId(couponId);
    }

    public void deletePurchasesByUserId(int id) throws ServerException {
        purchasesDal.deletePurchasesByUserId(id);
    }

    public void deletePurchasesOfExpiredCoupons() throws ServerException {
        purchasesDal.deletePurchasesOfExpiredCoupons();
    }

    public void deletePurchasesByCategoryId(int id) throws ServerException {
        purchasesDal.deletePurchasesByCategoryId(id);
    }

    public void deletePurchaseByAdmin(int id, String userType) throws ServerException {
        if (!userType.equals("Admin")) {
            throw new ServerException(ErrorType.INVALID_USER_TYPE, "This action allowed only for admin");
        }
        Purchase purchase = getPurchase(id);
        couponsLogic.increaseCouponAmount(purchase.getAmount(), purchase.getCouponId());
        purchasesDal.deletePurchase(id);
    }

    public PurchaseDetails getPurchaseDetailsByIdByAdmin(int id, String userType) throws ServerException {
        if (!userType.equals("Admin")) {
            throw new ServerException(ErrorType.INVALID_USER_TYPE, "This action allowed only for admin");
        }
        return purchasesDal.getPurchaseDetailsById(id);
    }
}
