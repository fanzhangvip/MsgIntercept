package com.git.msgintercept.utils;

import com.google.gson.Gson;

/**
 *  json 和 bean 之间相互转换的工具类
 *
 * @author Wings
 * @date 2017/12/6.
 */

public class JsonTransUtils {

    public static final String TAG = "JsonTransUtils";

    public static <T> T transJsonToBean(String json, Class<T> clazz){
        Gson gson = new Gson();
        T t = gson.fromJson(json, clazz);
        return t;
    }

    public static <T> String transBeanToJson(T t){
        Gson gson = new Gson();
        String json = gson.toJson(t);
        return json;
    }
}
