package com.nir.coupons.timerTask;

import com.nir.coupons.exceptions.ServerException;
import com.nir.coupons.logic.CouponsLogic;

import java.util.TimerTask;

public class DeleteExpiredCouponsTimerTask extends TimerTask {

    private CouponsLogic couponsLogic;

    @Override
    public void run() {
        try {
            couponsLogic.deleteExpiredCoupons();
            System.out.println("Expired coupons deleted");
        } catch (ServerException e) {
            e.printStackTrace();
        }
    }
}