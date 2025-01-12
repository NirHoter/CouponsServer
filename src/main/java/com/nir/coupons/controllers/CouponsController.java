package com.nir.coupons.controllers;

import com.nir.coupons.dto.Coupon;
import com.nir.coupons.dto.UserLoginDetails;
import com.nir.coupons.exceptions.ServerException;
import com.nir.coupons.logic.CouponsLogic;
import com.nir.coupons.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupons")
public class CouponsController {

    private CouponsLogic couponsLogic;
    private UserLoginDetails userLoginDetails;

    @Autowired
    public CouponsController(CouponsLogic couponsLogic) {
        this.couponsLogic = couponsLogic;
    }

    @GetMapping("{id}")
    public Coupon getCouponById(@PathVariable("id") int couponId) throws ServerException {
        return this.couponsLogic.getCoupon(couponId);
    }

    @PostMapping
    public void createCouponByUserCompany(@RequestHeader("Authorization") String token, @RequestBody Coupon coupon) throws Exception {
        userLoginDetails = JWTUtils.decodeJWT(token);
        this.couponsLogic.createCoupon(coupon, userLoginDetails);
    }

    @PutMapping
    public void updateCouponByUserCompany(@RequestHeader("Authorization") String token, @RequestBody Coupon coupon) throws Exception {
        userLoginDetails = JWTUtils.decodeJWT(token);
        this.couponsLogic.updateCoupon(coupon, userLoginDetails);
    }

    //admin
    @DeleteMapping("/Admin/{id}")
    public void deleteCouponByAdmin(@RequestHeader("Authorization") String token, @PathVariable("id") int id) throws Exception {
        userLoginDetails = JWTUtils.decodeJWT(token);
        this.couponsLogic.deleteCouponByAdmin(id, userLoginDetails.getUserType());
    }

    @GetMapping
    public Page<CouponsDetails> getCoupons(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size) throws ServerException {
        return this.couponsLogic.getCoupons(page, size);
    }


    @GetMapping("/byCompanyId")
    public Page<CouponsDetails> getCouponsByCompanyId(@RequestParam("companyId") int companyId,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size) throws ServerException {
        return this.couponsLogic.getCouponsByCompanyId(companyId, page, size);
    }

    //http://localhost:8080/coupons/byMaxPrice?price=100
    //http://localhost:8080/coupons/byMaxPrice?price=100&page=0&size=5
    //TODO עובד
    @GetMapping("/byMaxPrice")
    public Page<CouponsDetails> getCouponsBelowPrice(@RequestParam("price") float price,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) throws ServerException {
        return this.couponsLogic.getCouponsBelowPrice(price, page, size);
    }
}
