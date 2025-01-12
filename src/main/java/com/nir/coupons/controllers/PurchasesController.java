package com.nir.coupons.controllers;

import com.nir.coupons.dto.Purchase;
import com.nir.coupons.dto.PurchaseDetails;
import com.nir.coupons.dto.UserLoginDetails;
import com.nir.coupons.exceptions.ServerException;
import com.nir.coupons.logic.PurchasesLogic;
import com.nir.coupons.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/purchases")
public class PurchasesController {

    private PurchasesLogic purchasesLogic;
    private UserLoginDetails userLoginDetails;

    @Autowired
    public PurchasesController(PurchasesLogic purchasesLogic) {
        this.purchasesLogic = purchasesLogic;
    }

    @PostMapping
    public void createPurchases(@RequestHeader("Authorization") String token, @RequestBody Purchase purchase) throws Exception {
        userLoginDetails = JWTUtils.decodeJWT(token);
        purchase.setCustomerId(userLoginDetails.getUserId());
        purchase.setTimestamp(Timestamp.from(Instant.now()));
        this.purchasesLogic.createPurchases(purchase, userLoginDetails.getUserType());
    }


    @DeleteMapping("/MyPurchase/{id}")
    public void deleteMyPurchase(@RequestHeader("Authorization") String token, @PathVariable("id") int purchaseId) throws Exception {
        userLoginDetails = JWTUtils.decodeJWT(token);
        this.purchasesLogic.deletePurchase(purchaseId, userLoginDetails.getUserId());
    }

    @DeleteMapping("/ByAdmin/{id}")
    public void deletePurchaseByAdmin(@RequestHeader("Authorization") String token, @PathVariable("id") int purchaseId) throws Exception {
        userLoginDetails = JWTUtils.decodeJWT(token);
        this.purchasesLogic.deletePurchaseByAdmin(purchaseId, userLoginDetails.getUserType());
    }


    @GetMapping
    public Page<PurchasesDetails> getPurchases(@RequestHeader("Authorization") String token,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size) throws Exception {
        userLogin = JWTUtils.decodeJWT(token);
        return purchasesLogic.getPurchases(userLoginDetails.getUserType(), page, size);
    }

    @GetMapping("/MyPurchases/{id}")
    public PurchasesDetails getMyPurchaseDetailsById(@RequestHeader("Authorization") String token, @PathVariable("id") int id) throws Exception {
        userLoginDetails = JWTUtils.decodeJWT(token);
        return this.purchasesLogic.getPurchaseDetailsById(id, userLoginDetails.getUserId());
    }

    @GetMapping("admin/{id}")
    public PurchaseDetails getPurchaseDetailsByPurchaseIdByAdmin(@RequestHeader("Authorization") String token, @PathVariable("id") int id) throws Exception {
        userLoginDetails = JWTUtils.decodeJWT(token);
        return this.purchasesLogic.getPurchaseDetailsByIdByAdmin(id, userLoginDetails.getUserType());
    }

    @GetMapping("/Admin/{id}")
    public PurchasesDetails getPurchaseDetailsToAdminById(@RequestHeader("Authorization") String token, @PathVariable("id") int id) throws Exception {
        userLoginDetails = JWTUtils.decodeJWT(token);
        return this.purchasesLogic.getPurchaseDetailsByIdToUserAdmin(id, userLoginDetails.getUserType());
    }


    @GetMapping("/byCustomer")
    public Page<PurchasesDetails> getPurchaseDetailsByCustomerId(@RequestHeader("Authorization") String token,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size) throws Exception {
        userLoginDetails = JWTUtils.decodeJWT(token);
        return this.purchasesLogic.getPurchaseDetailsByCustomerId(userLoginDetails.getUserId(), page, size);
    }
}
