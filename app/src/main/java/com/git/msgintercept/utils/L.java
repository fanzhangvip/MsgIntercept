package com.git.msgintercept.utils;


import ch.qos.logback.classic.BL;
import ch.qos.logback.classic.BLog;

/**
 * Created by zero on 2017/11/28.
 * Describe: function
 */

public class L {
    private final static BLog logger = BL.getLogger(L.class);

    private L() {
    }

    public static BLog getLogger() {
        return logger;
    }

}
