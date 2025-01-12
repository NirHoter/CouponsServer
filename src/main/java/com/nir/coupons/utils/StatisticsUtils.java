package com.nir.coupons.utils;

import com.nir.coupons.dto.SendStatisticsThreads;

public class StatisticsUtils {


    public static void sendStatistics(String userName, String actionType) {
        SendStatisticsThreads sendStatisticsThreads = new SendStatisticsThreads(userName, actionType);
        sendStatisticsThreads.start();
    }
}

