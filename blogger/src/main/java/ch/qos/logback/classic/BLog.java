package ch.qos.logback.classic;



/**
 * Created by zero on 2017/10/17.
 * Describe: function
 */

public interface BLog {

    void d(String message, Object... args);

    void d(Object object);

    void e(String message, Object... args);

    void e(Object object);

    void w(String message, Object... args);

    void w(Object object);

    void i(String message, Object... args);

    void i(Object object);

    void v(String message, Object... args);

    void v(Object object);

    void wtf(String message, Object... args);

    void wtf(Object object);

    void json(String json);

    void xml(String xml);

    public BLog tag(String tag);
}
