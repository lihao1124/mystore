package com.wifiyou.utils;

/**
 * @author dengzhiren@wifiyou.net (Dean)
 *         4/28/16
 */
public class StringUtil {

    public static String getString(int id) {
        return GlobalConfig.getAppContext().getString(id);
    }

    public static String getString(int id, Object... args) {
        return GlobalConfig.getAppContext().getString(id, args);
    }

    public static boolean containsKeyword(String targetString, String... keys) {
        if (targetString == null) {
            return false;
        }
        if (keys != null) {
            for (int i = 0; i < keys.length; i++) {
                if (targetString.contains(keys[i])) {
                    return true;
                }
            }
            return false;
        } else {
            return true;
        }
    }

    public static String findFirstEnglishWord(String str) {
        if (str == null || str.equals("")) {
            return str;
        }
        char[] chars = str.toCharArray();
        boolean startBreak = false;
        boolean hasLowCase = false;
        int subPosition = chars.length;
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (!('a' < c && c < 'z') && startBreak && hasLowCase) {
                subPosition = i;
                break;
            } else if ('a' < c && c < 'z') {
                hasLowCase = true;
                startBreak = true;
            } else {
                startBreak = true;
            }
        }
        return str.substring(0, subPosition);
    }

    public static String ellipsizeEnd(String str, int length) {
        if (str == null || str.equals("")) {
            return str;
        }
        if (length <= 3) {
            return "...";
        }
        if (str.length() < length - 3) {
            return str;
        }
        return str.substring(0, length - 3) + "...";
    }
}
