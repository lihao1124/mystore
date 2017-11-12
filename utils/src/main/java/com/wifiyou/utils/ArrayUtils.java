package com.wifiyou.utils;

/**
 * @author dengzhiren@wifiyou.net (Dean)
 *         5/26/17
 */

public class ArrayUtils {
    public static void reverse(byte[] bytes) {
        if (bytes == null || bytes.length == 1) {
            return;
        }
        for (int k = 0; k < bytes.length / 2; k++) {
            byte temp = bytes[k];
            bytes[k] = bytes[bytes.length - (1 + k)];
            bytes[bytes.length - (1 + k)] = temp;
        }
    }
}
