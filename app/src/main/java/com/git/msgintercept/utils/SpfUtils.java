package com.git.msgintercept.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;


/**
 * @author Ou Weibin
 * @version 1.0
 */
public class SpfUtils {
    /**
     * The tag for debug.
     */
    private final static String TAG = SpfUtils.class.getSimpleName();

    /**
     * The name for default sharedPreferences file name.
     */
    private static String sSpfFileName = "config";

    /**
     * The mode for default sharePreferences file.
     */
    private final static int SPF_MODE = Context.MODE_PRIVATE;
    /**
     * The application for convenient get sharePreferences at anywhere.
     */
    private static Application application = null;

    public static void setSpfFileName(String name) {
        sSpfFileName = name;
    }

    public static void setApplication(Application application) {
        if (application == null) {
            return;
        }
        SpfUtils.application = application;
    }

    public static void setApplication(Activity activity) {
        if (activity == null) {
            return;
        }
        SpfUtils.application = activity.getApplication();
    }

    public static SharedPreferences getSpf(Application application, String name) {
        if (application == null) {
            throw new RuntimeException("Application was null, please check this.");
        }
        return application.getSharedPreferences(name, SPF_MODE);
    }

    private static boolean checkObject(Object object) {
        return (object != null);
    }

    private static boolean checkString(String key) {
        return (checkObject(key) && key.trim().length() != 0);
    }

    private static boolean checkApplicationAndString(Application application, String name) {
        return (checkObject(application) && checkString(name));
    }

    private static boolean checkApplicationAndStrings(Application application, String name, String key) {
        return (checkApplicationAndOject(application, name) && checkString(key));
    }

    private static boolean checkApplicationAndStrings(Application application, String name, String key, String value) {
        return (checkApplicationAndStrings(application, name, key) && checkString(value));
    }

    private static boolean checkApplicationAndOject(Application application, Object object) {
        return (checkObject(application) && checkObject(object));
    }

    public static String getString(Application application, String name, String key, String defValue) {
        defValue = (defValue == null ? "" : defValue);
        if (checkApplicationAndStrings(application, name, key)) {
            return getSpf(application, name).getString(key, defValue).trim();
        }
        return defValue;
    }

    public static String getString(Application application, String key) {
        return getString(application, sSpfFileName, key, "");
    }

    public static String getString(Application application, String key, String defValue) {
        return getString(application, sSpfFileName, key, defValue);
    }

    public static String getString(Activity activity, String key) {
        return getString(activity.getApplication(), sSpfFileName, key);
    }

    public static String getString(Activity activity, String key, String defValue) {
        return getString(activity.getApplication(), sSpfFileName, key, defValue);
    }

    public static String getString(String name, String key) {
        return getString(application, name, key);
    }

    public static String getString(String name, String key, String defValue) {
        return getString(application, name, key, defValue);
    }

    public static String getString(String key) {
        return getString(application, sSpfFileName, key, "");
    }


    public static void putString(Application application, String name, String key, String value) {
        if (checkApplicationAndStrings(application, name, key, value)) {
            getSpf(application, name).edit().putString(key, value.trim()).commit();
        }
    }

    public static void putString(Application application, String key, String value) {
        putString(application, sSpfFileName, key, value);
    }

    public static void putString(Activity activity, String key, String value) {
        putString(activity.getApplication(), sSpfFileName, key, value);
    }

    public static void putString(String name, String key, String value) {
        putString(application, name, key, value);
    }

    public static void putString(String key, String value) {
        putString(application, sSpfFileName, key, value);
    }

    public static int getInt(Application application, String name, String key) {
        return getInt(application, name, key, 0);
    }

    public static int getInt(Application application, String name, String key, int defValue) {
        if (checkApplicationAndStrings(application, name, key)) {
            return getSpf(application, name).getInt(key, defValue);
        }
        return defValue;
    }

    public static int getInt(Application application, String key) {
        return getInt(application, sSpfFileName, key);
    }

    public static int getInt(Application application, String key, int defValue) {
        return getInt(application, sSpfFileName, key, defValue);
    }

    public static int getInt(Activity activity, String key) {
        return getInt(activity.getApplication(), sSpfFileName, key);
    }

    public static int getInt(Activity activity, String key, int defValue) {
        return getInt(activity.getApplication(), sSpfFileName, key, defValue);
    }

    public static int getInt(String name, String key) {
        return getInt(application, name, key);
    }

    public static int getInt(String name, String key, int defValue) {
        return getInt(application, name, key, defValue);
    }

    public static int getInt(String key) {
        return getInt(application, sSpfFileName, key);
    }

    public static int getInt(String key, int defValue) {
        return getInt(application, sSpfFileName, key, defValue);
    }

    public static void putInt(Application application, String name, String key, int value) {
        if (checkApplicationAndStrings(application, name, key)) {
            getSpf(application, name).edit().putInt(key, value).commit();
        }
    }

    public static void putInt(Application application, String key, int value) {
        putInt(application, sSpfFileName, key, value);
    }

    public static void putInt(Activity activity, String key, int value) {
        putInt(activity.getApplication(), sSpfFileName, key, value);
    }

    public static void putInt(String name, String key, int value) {
        putInt(application, name, key, value);
    }

    public static void putInt(String key, int value) {
        putInt(application, sSpfFileName, key, value);
    }

    public static float getFloat(Application application, String name, String key) {
        return getFloat(application, name, key, 0);
    }

    public static float getFloat(Application application, String name, String key, float defValue) {
        if (checkApplicationAndStrings(application, name, key)) {
            return getSpf(application, name).getFloat(key, defValue);
        }
        return defValue;
    }

    public static float getFloat(Application application, String key) {
        return getFloat(application, sSpfFileName, key);
    }

    public static float getFloat(Application application, String key, float defValue) {
        return getFloat(application, sSpfFileName, key, defValue);
    }

    public static float getFloat(Activity activity, String key) {
        return getFloat(activity.getApplication(), sSpfFileName, key);
    }

    public static float getFloat(Activity activity, String key, float defValue) {
        return getFloat(activity.getApplication(), sSpfFileName, key, defValue);
    }

    public static float getFloat(String name, String key) {
        return getFloat(application, name, key);
    }

    public static float getFloat(String name, String key, float defValue) {
        return getFloat(application, name, key, defValue);
    }

    public static float getFloat(String key) {
        return getFloat(application, sSpfFileName, key);
    }

    public static float getFloat(String key, float defValue) {
        return getFloat(application, sSpfFileName, key, defValue);
    }

    public static void putFloat(Application application, String name, String key, float value) {
        if (checkApplicationAndStrings(application, name, key)) {
            getSpf(application, name).edit().putFloat(key, value).commit();
        }
    }

    public static void putFloat(Application application, String key, float value) {
        putFloat(application, sSpfFileName, key, value);
    }

    public static void putFloat(Activity activity, String key, float value) {
        putFloat(activity.getApplication(), sSpfFileName, key, value);
    }

    public static void putFloat(String name, String key, float value) {
        putFloat(application, name, key, value);
    }

    public static void putFloat(String key, float value) {
        putFloat(application, sSpfFileName, key, value);
    }

    public static long getLong(Application application, String name, String key, long defValue) {
        if (checkApplicationAndStrings(application, name, key)) {
            return getSpf(application, name).getLong(key, defValue);
        }
        return defValue;
    }

    public static long getLong(Application application, String name, String key) {
        return getLong(application, name, key, 0);
    }

    public static long getLong(Application application, String key) {
        return getLong(application, sSpfFileName, key);
    }

    public static long getLong(Application application, String key, long defValue) {
        return getLong(application, sSpfFileName, key, defValue);
    }

    public static long getLong(Activity activity, String key) {
        return getLong(activity.getApplication(), sSpfFileName, key);
    }

    public static long getLong(Activity activity, String key, long defValue) {
        return getLong(activity.getApplication(), sSpfFileName, key, defValue);
    }

    public static long getLong(String name, String key) {
        return getLong(application, name, key);
    }

    public static long getLong(String name, String key, long defValue) {
        return getLong(application, name, key, defValue);
    }

    public static long getLong(String key) {
        return getLong(application, sSpfFileName, key);
    }

    public static long getLong(String key, long defValue) {
        return getLong(application, sSpfFileName, key, defValue);
    }

    public static void putLong(Application application, String name, String key, long value) {
        if (checkApplicationAndStrings(application, name, key)) {
            getSpf(application, name).edit().putLong(key, value).commit();
        }
    }

    public static void putLong(Application application, String key, long value) {
        putLong(application, sSpfFileName, key, value);
    }

    public static void putLong(Activity activity, String key, long value) {
        putLong(activity.getApplication(), sSpfFileName, key, value);
    }

    public static void putLong(String name, String key, long value) {
        putLong(application, name, key, value);
    }

    public static void putLong(String key, long value) {
        putLong(application, sSpfFileName, key, value);
    }

    public static boolean getBoolean(Application application, String name, String key, boolean defValue) {
        if (checkApplicationAndStrings(application, name, key)) {
            return getSpf(application, name).getBoolean(key, defValue);
        }
        return defValue;
    }

    public static boolean getBoolean(Application application, String name, String key) {
        return getBoolean(application, name, key, false);
    }

    public static boolean getBoolean(Application application, String key) {
        return getBoolean(application, sSpfFileName, key);
    }

    public static boolean getBoolean(Application application, String key, boolean defValue) {
        return getBoolean(application, sSpfFileName, key, defValue);
    }

    public static boolean getBoolean(Activity activity, String key) {
        return getBoolean(activity.getApplication(), sSpfFileName, key);
    }

    public static boolean getBoolean(Activity activity, String key, boolean defValue) {
        return getBoolean(activity.getApplication(), sSpfFileName, key, defValue);
    }

    public static boolean getBoolean(String name, String key) {
        return getBoolean(application, name, key);
    }

    public static boolean getBoolean(String name, String key, boolean defValue) {
        return getBoolean(application, name, key, defValue);
    }

    public static boolean getBoolean(String key) {
        return getBoolean(application, sSpfFileName, key);
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return getBoolean(application, sSpfFileName, key, defValue);
    }

    public static void putBoolean(Application application, String name, String key, boolean value) {
        if (checkApplicationAndStrings(application, name, key)) {
            getSpf(application, name).edit().putBoolean(key, value).commit();
        }
    }

    public static void putBoolean(Application application, String key, boolean value) {
        putBoolean(application, sSpfFileName, key, value);
    }

    public static void putBoolean(Activity activity, String key, boolean value) {
        putBoolean(activity.getApplication(), sSpfFileName, key, value);
    }

    public static void putBoolean(String name, String key, boolean value) {
        putBoolean(application, name, key, value);
    }

    public static void putBoolean(String key, boolean value) {
        putBoolean(application, sSpfFileName, key, value);
    }

    private static void putToSP(Editor editor, String key, Object value) {
        if (value instanceof String) {
            editor.putString(key, ((String) value).trim());
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        }
    }

    public static void putValue(Application application, String name, String key, Object value) {
        if (checkApplicationAndStrings(application, name, key)) {

            Editor editor = getSpf(application, name).edit();

            putToSP(editor, key, value);

            editor.commit();
        }
    }

    public static void putValue(Application application, String key, Object value) {
        putValue(application, sSpfFileName, key, value);
    }

    public static void putValue(Activity activity, String key, Object value) {
        putValue(activity.getApplication(), sSpfFileName, key, value);
    }

    public static void putValue(String name, String key, Object value) {
        putValue(application, name, key, value);
    }

    public static void putValue(String key, Object value) {
        putValue(application, sSpfFileName, key, value);
    }

    public static void putValues(Application application, String name, HashMap<String, Object> keyValues) {
        if (checkApplicationAndOject(application, keyValues)) {

            Editor editor = getSpf(application, name).edit();

            Iterator<Entry<String, Object>> iterator = keyValues.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, Object> entry = (Entry<String, Object>) iterator.next();
                String key = (String) entry.getKey();
                Object value = entry.getValue();

                putToSP(editor, key, value);
            }

            editor.commit();
        }
    }

    public static void putValues(Application application, HashMap<String, Object> keyValues) {
        putValues(application, sSpfFileName, keyValues);
    }

    public static void putValues(Activity activity, HashMap<String, Object> keyValues) {
        putValues(activity.getApplication(), sSpfFileName, keyValues);
    }

    public static void putValues(String name, HashMap<String, Object> keyValues) {
        putValues(application, name, keyValues);
    }

    public static void putValues(HashMap<String, Object> keyValues) {
        putValues(application, sSpfFileName, keyValues);
    }

    private static Object getFromSP(SharedPreferences sp, String key, Object type) {
        Object value = null;

        if (type instanceof String) {
            value = sp.getString(key, "").trim();
        } else if (type instanceof Integer) {
            value = sp.getInt(key, 0);
        } else if (type instanceof Float) {
            value = sp.getFloat(key, 0);
        } else if (type instanceof Long) {
            value = sp.getLong(key, 0);
        } else if (type instanceof Boolean) {
            value = sp.getBoolean(key, false);
        }

        return value;
    }

    public static Object getValue(Application application, String name, String key, Object type) {
        if (checkApplicationAndStrings(application, name, key)) {
            SharedPreferences sp = getSpf(application, name);

            return getFromSP(sp, key, type);
        }
        return null;
    }

    public static Object getValue(Application application, String key, Object type) {
        return getValue(application, sSpfFileName, key, type);
    }

    public static Object getValue(Activity activity, String key, Object type) {
        return getValue(activity.getApplication(), sSpfFileName, key, type);
    }

    public static Object getValue(String name, String key, Object type) {
        return getValue(application, name, key, type);
    }

    public static Object getValue(String key, Object type) {
        return getValue(application, sSpfFileName, key, type);
    }

    public static HashMap<String, Object> getValues(Application application, String name, HashMap<String, Object> keyTypes) {
        HashMap<String, Object> keyValues = new HashMap<String, Object>();
        if (checkApplicationAndString(application, name)) {
            SharedPreferences sp = getSpf(application, name);

            Iterator<Entry<String, Object>> iter = keyTypes.entrySet().iterator();
            while (iter.hasNext()) {
                Entry<String, Object> entry = (Entry<String, Object>) iter.next();
                String key = (String) entry.getKey();
                Object type = entry.getValue();

                keyValues.put(key, getFromSP(sp, key, type));
            }

            return keyValues;
        }
        return keyValues;
    }

    public static HashMap<String, Object> getValues(Application application, HashMap<String, Object> keyTypes) {
        return getValues(application, sSpfFileName, keyTypes);
    }

    public static HashMap<String, Object> getValues(Activity activity, HashMap<String, Object> keyTypes) {
        return getValues(activity.getApplication(), sSpfFileName, keyTypes);
    }

    public static HashMap<String, Object> getValues(String name, HashMap<String, Object> keyTypes) {
        return getValues(application, name, keyTypes);
    }

    public static HashMap<String, Object> getValues(HashMap<String, Object> keyTypes) {
        return getValues(application, sSpfFileName, keyTypes);
    }

/*    public static void testSPUtils(Application application) {

        setApplication(application);

        putString("name", "China");
        KLog.e("SPUtils", "name = " + (String) getValue("name", new String()));
        putInt("int", 3);
        KLog.e("SPUtils", "int = " + getValue("int", Integer.valueOf(0)));
        putFloat("float", 3.14f);
        KLog.e("SPUtils", "float = " + getValue("float", Float.valueOf(0)));

        HashMap<String, Object> keyValues = new HashMap<String, Object>();
        keyValues.put("name", "SZ");
        keyValues.put("address", "shenzhen");
        keyValues.put("float", 3.14f);
        keyValues.put("int", 3);
        keyValues.put("boolean", true);

        putValues(keyValues);

        HashMap<String, Object> keyTypes = new HashMap<String, Object>();
        keyTypes.put("name", new String());
        keyTypes.put("address", new String());
        keyTypes.put("float", Float.valueOf(0));
        keyTypes.put("int", Integer.valueOf(0));
        keyTypes.put("boolean", Boolean.valueOf(false));

        KLog.e(TAG, getValues(keyTypes).toString());

        KLog.e("SPUtils", "age = " + getValue("age", Integer.valueOf(0)));
        KLog.e("SPUtils", "int = " + getValue("int", Integer.valueOf(0)));
        KLog.e("SPUtils", "float = " + getValue("float", Float.valueOf(0)));
        KLog.e("SPUtils", "name = " + (String) getValue("name", new String()));

        *//**
     * output:
     *     E/SPUtils(8456): name = China
     *     E/SPUtils(8456): int = 3
     *     E/SPUtils(8456): float = 3.14
     *     E/SPUtils(8456): {boolean=true, address=shenzhen, float=3.14, int=3, name=SZ}
     *     E/SPUtils(8456): age = 0
     *     E/SPUtils(8456): int = 3
     *     E/SPUtils(8456): float = 3.14
     *     E/SPUtils(8633): name = SZ
     *//*

    }*/
}
