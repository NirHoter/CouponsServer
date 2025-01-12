package com.nir.coupons.controllers;

import com.nir.coupons.exceptions.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/verification")
public class VerificationController {

    private VerificationLogic verification;

    @Autowired
    public VerificationController(VerificationLogic emailLogic) {
        this.verification = emailLogic;
    }

    @GetMapping
    public void verifyEmail(@RequestParam("emailToken") String emailToken) throws ServerException {
        verification.verifyEmailToken(emailToken);
    }
}