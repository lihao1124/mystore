package com.wifiyou.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CipherUtils {

    // public static byte[] getAESKey(Context context) {
    // byte[] ret = {120, 17, 91, 64, 82, 42, 52, 50, 111, 109, 93, 92, 78, 10, 15, 32};
    // return ret;
    // }
    public static final String NO_PASSWORD = "N/A";
    private static native byte[] getAESKeyNative(Context context);

    public static byte[] getAESKey(Context context) {
        LibraryLoaderHelper.loadLibrarySafety(context, "cipher");
        byte[] ret = null;
        try {
            ret = getAESKeyNative(context);
        } catch (UnsatisfiedLinkError error) {
            ret = null;
        }
        return ret;
    }

    public static byte[] encrypt(String sSrc, byte[] raw)
            throws GeneralSecurityException {
        return encrypt(sSrc.getBytes(), raw);
    }

    public static byte[] encrypt(byte[] sSrc, byte[] raw)
            throws GeneralSecurityException {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] ivBytes = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
        };
        IvParameterSpec iv = new IvParameterSpec(ivBytes);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc);
        return encrypted;
    }

    public static void encrypt(InputStream input, OutputStream output, byte[] raw)
            throws GeneralSecurityException {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] ivBytes = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
        };
        IvParameterSpec iv = new IvParameterSpec(ivBytes);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

        CipherOutputStream cipherOutput = new CipherOutputStream(output, cipher);
        byte[] buffer = new byte[1024];
        try {
            while (true) {
                int readed = input.read(buffer);
                if (readed == -1) {
                    break;
                }
                cipherOutput.write(buffer, 0, readed);
            }
            cipherOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void decrypt(InputStream input, OutputStream output, byte[] raw)
            throws GeneralSecurityException {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] ivBytes = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
        };
        IvParameterSpec iv = new IvParameterSpec(ivBytes);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

        CipherInputStream cipherInput = new CipherInputStream(input, cipher);
        byte[] buffer = new byte[1024];
        try {
            while (true) {
                int readed = cipherInput.read(buffer);
                if (readed == -1) {
                    break;
                }
                output.write(buffer, 0, readed);
            }
            cipherInput.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] decrypt(byte[] encrypted, byte[] raw)
            throws GeneralSecurityException {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] ivBytes = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
        };
        IvParameterSpec iv = new IvParameterSpec(ivBytes);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }

    public static String encryptAndBase64(String str) {
        byte[] key = CipherUtils.getAESKey(GlobalConfig.getAppContext());
        if (key == null) {
            return str;
        }
        try {
            return Base64.encodeToString(encrypt(str, key), Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String decryptBase64String(String encode, Context context) {
        if (TextUtils.isEmpty(encode) || context == null) {
            return null;
        }
        if (NO_PASSWORD.equals(encode)) {
            return encode;
        }
        try {
            byte[] deBase64 = null;
            try {
                deBase64 = Base64.decode(encode, Base64.NO_WRAP);
            } catch (Exception e) {
            }
            byte[] debyte = null;
            if (deBase64 != null) {
                debyte = decrypt(deBase64, getAESKey(context));
            } else {
                debyte = decrypt(Base64.decode(encode, Base64.NO_WRAP), getAESKey(context));
            }

            if (debyte != null) {
                return new String(debyte);
            }
        } catch (Exception e) {
        }
        return null;
    }
}
