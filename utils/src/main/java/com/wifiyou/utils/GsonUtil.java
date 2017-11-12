package com.wifiyou.utils;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * @author dengzhiren@wifiyou.net (Dean)
 *         5/26/16
 */
public class GsonUtil {
    public static Gson mGson = new Gson();

    public static <T> T fromJson(String jsonString, Class<T> classOfT) {
        if (TextUtils.isEmpty(jsonString) || classOfT == null) {
            return null;
        }
        if (!isImplementSerializable(classOfT)) {
            throw new RuntimeException(classOfT.getName() + " need implement Serializable");
        }
        return mGson.fromJson(jsonString, classOfT);
    }

    public static String toJson(Object param) {
        if (param == null) {
            return null;
        }
        if (!isImplementSerializable(param.getClass())) {
            throw new RuntimeException(param.getClass().getName() + " need implement Serializable");
        }
        return mGson.toJson(param);
    }

    private static <T> boolean isImplementSerializable(Class<T> classOfT) {
        if (classOfT == null) {
            return false;
        }
        Class<?>[] inters;
        if (classOfT.isArray()) {
            inters = classOfT.getComponentType().getInterfaces();
        } else {
            inters = classOfT.getInterfaces();
        }
        boolean isImplement = false;
        if (inters != null) {
            for (Class c : inters) {
                if (c instanceof Serializable) {
                    isImplement = true;
                    break;
                }
            }
        }
        return isImplement;
    }



}
