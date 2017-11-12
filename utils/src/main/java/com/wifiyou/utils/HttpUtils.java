package com.wifiyou.utils;

/**
 * @author dengzhiren@wifiyou.net (Dean)
 *         4/29/16
 */
public class HttpUtils {

    public static final int SC_OK = 200;

    public static boolean checkStatusCode(String code) {
        if (String.valueOf(SC_OK).equals(code)) {
            return true;
        }
        return false;
    }

    public static boolean checkStatusCode(int code) {
        if (SC_OK == code) {
            return true;
        }
        return false;
    }

}
