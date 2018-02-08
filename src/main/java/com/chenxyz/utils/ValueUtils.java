package com.chenxyz.utils;

import java.util.Map;

/**
 * 获取值的工具
 *
 * @author chenlinchao
 * @version 1.0
 * @date 2018-02-08
 */
public class ValueUtils {

    public static int getInt(Object obj) {
        return getInt(obj, -1);
    }

    public static int getInt(Object obj, int defaultValue) {
        int value = defaultValue;
        try {
            value = Integer.parseInt(obj.toString());
        } catch (Exception ex) {
        }
        return value;
    }

    public static int getInt(Map<String, Object> map, String key, int defaultValue) {
        return getInt(map.get(key), defaultValue);
    }

    public static int getInt(Map<String, Object> map, String key) {
        return getInt(map.get(key), -1);
    }

    public static String getString(Object obj) {
        return getString(obj, "");
    }

    public static String getString(Object obj, String defaultValue) {
        String value = defaultValue;
        if (obj == null) {
            return value;
        }
        try {
            value = obj.toString();
        } catch (Exception ex) {
        }
        return value;
    }

    public static String getString(Map<String, Object> map, String key, String defaultValue) {
        return getString(map.get(key), defaultValue);
    }

    public static String getString(Map<String, Object> map, String key) {
        return getString(map.get(key), "");
    }
}
