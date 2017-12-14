package ch.qos.logback.classic;


import org.slf4j.*;
import org.slf4j.Logger;

/**
 * Created by zero on 2017/10/19.
 * Describe: function
 */

public class BL {

    private static final BLog bLog = (BLog) LoggerFactory.getLogger(BL.class);

    private BL() {

    }

    public static BLog getLogger(){
        return bLog;
    }

    public static BLog getLogger(String name) {
        org.slf4j.Logger logger = LoggerFactory.getLogger(name);
        final BLog bLog = (BLog) logger;
        return bLog;
    }

    public static BLog getLogger(Class<?> clazz) {
        Logger logger = LoggerFactory.getLogger(clazz);
        final BLog bLog = (BLog) logger;
        return bLog;
    }
}
