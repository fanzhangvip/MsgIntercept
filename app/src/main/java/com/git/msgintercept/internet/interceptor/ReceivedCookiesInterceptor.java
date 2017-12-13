package com.git.msgintercept.internet.interceptor;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by zero on 2017/11/30.
 * Describe: function
 */

public class ReceivedCookiesInterceptor implements Interceptor {

    private static final String COOKIE = "Set-Cookie";

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        if (!originalResponse.headers(COOKIE).isEmpty()) {
            List<String> list = originalResponse.headers().values("Set-Cookie");
            if (list != null || list.size() > 0) {
            }
        }
        return originalResponse;
    }
}
