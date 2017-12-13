package com.git.msgintercept.utils;

import android.app.Application;
import android.content.Context;


/**
 * Created by zero on 2017/11/21.
 * Describe: function
 */

public class Config {


    public static void init(Context context) {
        SpfUtils.setApplication((Application) context.getApplicationContext());
    }


    public static String getKeyWord() {
        return SpfUtils.getString("key_word");
    }

    public static void setKeyWord(String keyWord) {
        SpfUtils.putString("key_word", keyWord);
    }

    public static String getBaseUrl() {
        return SpfUtils.getString("base_url", "http://127.0.0.1:8080/update/");
    }

    public static void setBaseUrl(String baseUrl) {
        SpfUtils.putString("base_url", baseUrl);
    }

}
