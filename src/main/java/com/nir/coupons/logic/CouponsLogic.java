package com.nir.coupons.logic;

import com.nir.coupons.dal.CouponsDal;
import com.nir.coupons.dto.Category;
import com.nir.coupons.dto.Coupon;
import com.nir.coupons.dto.UserLoginDetails;
import com.nir.coupons.enums.ErrorType;
import com.nir.coupons.exceptions.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponsLogic {


    @Autowired
    private PurchasesLogic purchasesLogic;
    @Autowired
    private CategoriesLogic categoriesLogic;
    private CouponsDal couponsDal;

    @Autowired
    public CouponsLogic(CouponsDal couponsDal) {
        this.couponsDal = couponsDal;
    }

    public int createCoupon(Coupon coupon, UserLoginDetails userLoginDetails) throws ServerException {
        validateCoupon(coupon2);
        return couponsDal.createCoupon(coupon);
    }

    public void updateCoupon(Coupon coupon, String userType) throws ServerException {
        validateCoupon(coupon);
        couponsDal.updateCoupon(coupon);
    }

    public List<Coupon> getCoupons() throws ServerException {
        return couponsDal.getCoupons();
    }

    public List<Coupon> getCouponsByMaxPrice(int price) throws ServerException {
        return couponsDal.getCouponsBelowPrice(price);
    }

    public Coupon getCoupon(int id) throws ServerException {
        return couponsDal.getCouponById(id);
    }

    public List<Coupon> getCouponsByCompanyId(int companyId) throws ServerException {
        return couponsDal.getCouponsByCompanyId(companyId);
    }

    public void deleteCoupon(int id, String userType) throws ServerException {
        if (!userType.equals("Admin")) {
            throw new ServerException(ErrorType.INVALID_USER_TYPE, "This action allowed only for admin");
        }
        purchasesLogic.deletePurchasesByCouponId(id);
        couponsDal.deleteCoupon(id);
    }

    public void deleteCouponByCompanyId(int id) throws ServerException {
        couponsDal.deleteCouponsByCompanyId(id);
    }

    private void validateCoupon(Coupon coupon) throws ServerException {

        if (coupon.getAmount() < 1) {
            throw new ServerException(ErrorType.INVALID_COUPON_AMOUNT, coupon.toString());
        }

        if (coupon.getDescription() == null) {
            throw new ServerException(ErrorType.INVALID_COUPON_DESCRIPTION, coupon.toString());
        }

        if (coupon.getDescription().length() > 200) {
            throw new ServerException(ErrorType.INVALID_COUPON_DESCRIPTION, coupon.toString());
        }

        if (coupon.getPrice() <= 0) {
            throw new ServerException(ErrorType.INVALID_COUPON_PRICE, coupon.toString());
        }

//        if (coupon.getCategoryId() == null) {
//            throw new ServerException(ErrorType.INVALID_CATEGORY_ID, coupon.toString());
//        }

        if (coupon.getTitle() == null) {
            throw new ServerException(ErrorType.INVALID_TITLE, coupon.toString());
        }

        if (coupon.getTitle().length() > 45) {
            throw new ServerException(ErrorType.INVALID_TITLE, coupon.toString());
        }

        if (coupon.getStartDate() == null || coupon.getEndDate() == null) {
            throw new ServerException(ErrorType.INVALID_DATE, coupon.toString());
        }

        if (coupon.getEndDate().before(coupon.getStartDate())) {
            throw new ServerException(ErrorType.INVALID_START_DATE, coupon.toString());
        }

        if (coupon.getImageURL() != null && coupon.getImageURL().length() > 100) {
            throw new ServerException(ErrorType.INVALID_IMAGE_URL_LENGTH, coupon.toString());
        }

        if (!isCategoryIdExists(coupon)) {
            throw new ServerException(ErrorType.INVALID_CATEGORY_ID, coupon.toString());
        }
    }

    private boolean isCategoryIdExists(Coupon coupon) throws ServerException {

        Category categoryToCheck = categoriesLogic.getCategory(coupon.getCategoryId());
        if (categoryToCheck == null) {
            return false;
        }
        return true;
    }

    void decreaseCouponAmount(int purchaseAmount, int couponId) throws ServerException {
        Coupon coupon = getCoupon(couponId);
        int newAmount = coupon.getAmount() - purchaseAmount;

        coupon.setAmount(newAmount);
        this.couponsDal.updateCoupon(coupon);
    }

    void increaseCouponAmount(int purchaseAmount, int couponId) throws ServerException {
        Coupon coupon = getCoupon(couponId);
        int newAmount = coupon.getAmount() + purchaseAmount;
        coupon.setAmount(newAmount);
        this.couponsDal.updateCoupon(coupon);
    }

    public void deleteCouponsByCategoryId(int id) throws ServerException {
        couponsDal.deleteCouponsByCategoryId(id);
    }

    public void deleteExpiredCoupons() throws ServerException {
        purchasesLogic.deletePurchasesOfExpiredCoupons();
        couponsDal.deleteExpiredCoupons();
    }
}

