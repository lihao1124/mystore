package com.wifiyou.utils;

import android.text.TextUtils;

/**
 * @author dengzhiren@wifiyou.net (Dean)
 *         12/15/16
 */

public class UnicodeUtil {
    private static final StringBuffer sStrBuf = new StringBuffer();

    public static String decode(String unicode) {
        if (TextUtils.isEmpty(unicode)) {
            return unicode;
        }
        sStrBuf.delete(0, sStrBuf.length());
        char[] chars = unicode.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '\\' && chars[i + 1] == 'u') {
                char cc = 0;
                for (int j = 0; j < 4; j++) {
                    char ch = Character.toLowerCase(chars[i + 2 + j]);
                    if ('0' <= ch && ch <= '9' || 'a' <= ch && ch <= 'f') {
                        cc |= (Character.digit(ch, 16) << (3 - j) * 4);
                    } else {
                        cc = 0;
                        break;
                    }
                }
                if (cc > 0) {
                    i += 5;
                    sStrBuf.append(cc);
                    continue;
                }
            }
            sStrBuf.append(c);
        }
        return sStrBuf.toString();
    }
}
