package com.git.msgintercept.internet;

import android.util.Log;

import com.git.msgintercept.internet.interceptor.AddCookiesInterceptor;
import com.git.msgintercept.internet.interceptor.ReceivedCookiesInterceptor;
import com.git.msgintercept.utils.JsonUtil;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Wings on 2017/11/20.
 */

public class HttpUtils {

    /**
     * 默认 test-a环境
     */
    public static String BASE_URL = "http://127.0.0.1:8080/";


    public static void setBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
    }

    static class HttpLogger implements HttpLoggingInterceptor.Logger {
        private StringBuilder mMessage = new StringBuilder();

        @Override
        public void log(String message) {
            // 请求或者响应开始
            if (message.startsWith("--> POST")) {
                mMessage.setLength(0);
            }
            // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
            boolean isJson = (message.startsWith("{") && message.endsWith("}"))
                    || (message.startsWith("[") && message.endsWith("]"));
            if (isJson) {
                message = JsonUtil.formatJson(message);
            }
            mMessage.append(message.concat("\n"));
            // 请求或者响应结束，打印整条日志
            if (message.startsWith("<-- END HTTP")) {
                Log.i("HttpUtils", mMessage.toString());
            }
        }
    }


    public static Retrofit getOnlineCookieRetrofit() {

        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLogger());
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = httpBuilder
                .addInterceptor(new ReceivedCookiesInterceptor())
                .addInterceptor(new AddCookiesInterceptor())
                .addInterceptor(logInterceptor)
                .readTimeout(10000, TimeUnit.SECONDS)
                .connectTimeout(10000, TimeUnit.SECONDS)
                .writeTimeout(10000, TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder().baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
