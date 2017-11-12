package com.wifiyou.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;


public class UDIDUtil {

    private static final String UDID_KEY = "udid";
    private static final String UDID_FILE_NAME = ".udid";
    private static final String INNER_ROOT_DIR = "/data/local/tmp";
    private static final String OLD_INNER_UDID_DIR_PATH = "/data/local/tmp/.config";
    private static final String OLD_INNER_UDID_FILE_PATH = "/data/local/tmp/.config/.udid";
    private static final String INNER_UDID_DIR_PATH = "/data/local/tmp/.wifiyou_config";
    private static final String INNER_UDID_FILE_PATH = "/data/local/tmp/.wifiyou_config/.udid";
    private static final String FIRST_CHANNEL_FILE_NAME = ".channel";
    private static final String UDID_FILE_PATH = getFilePath(UDID_FILE_NAME);
    private static final String FIRST_CHANNEL_FILE_PATH = getFilePath(FIRST_CHANNEL_FILE_NAME);
    private static Handler mainHandler = new Handler(Looper.getMainLooper());
    private static final long DELAY_WRITE_FS_TIME = 5000l;
    private static final int TYPE_UDID = 0;
    private static final int TYPE_FIRST_CHANNEL = 1;
    private static final int READ_TYPE_NONE = 0;
    private static final int READ_TYPE_INNER = 1;
    private static final int READ_TYPE_SP = 2;
    private static final int READ_TYPE_FS = 3;
    private static final int READ_TYPE_OLD = 4;
    private static final int READ_TYPE_CREATE = 5;

    private static native String generateUDIDNative(String uuid);

    public static String generateUDID(Context context, String uuid) {
        LibraryLoaderHelper.loadLibrarySafety(context, "udid");
        try {
            return generateUDIDNative(uuid);
        } catch (Exception e) {
            Logcat.e("UDIDUtil generateUDID exception", e);
        }

        return "";
    }

    private static native boolean isUDIDValidNative(String udid);

    public static boolean isUDIDValid(Context context, String uuid) {
        LibraryLoaderHelper.loadLibrarySafety(context, "udid");
        try {
            return isUDIDValidNative(uuid);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String UDID = null;

    private static String loadUDIDFromFS(Context context) {
        String filePath = UDID_FILE_PATH;
        if (!TextUtils.isEmpty(filePath) && FileUtil.exists(filePath)) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(filePath));
                String line = reader.readLine();
                reader.close();

                if (TextUtils.isEmpty(line)) {
                    return "";
                }
                String udid = "";
                String[] parts = line.split("\t");
                if (parts.length >= 2) {
                    String imei = SystemUtil.getImei(context);
                    if (TextUtils.isEmpty(imei)) {
                        if (TextUtils.isEmpty(parts[1])
                                && !TextUtils.isEmpty(parts[0])) {
                            udid = parts[0];
                        }
                    } else {
                        if (parts[1].equals(DigestUtils.getStringMD5(imei))
                                && !TextUtils.isEmpty(parts[0])) {
                            udid = parts[0];
                        }
                    }
                } else {
                    udid = line;
                }
                return udid;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    private static String getFilePath(String fileName) {
        if (SystemUtil.isSDCardMounted()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/wifiyou/.config/" + fileName;
        } else {
            return "";
        }
    }

    private static String loadUDIDFromInternalStorage(Context context) {
        changeInnerRootDirPermission();
        if (FileUtil.exists(INNER_UDID_FILE_PATH)) {
            changeInnerFilePermission();
            FileUtil.deletePath(OLD_INNER_UDID_DIR_PATH);
            return FileUtil.readFileFirstLine(INNER_UDID_FILE_PATH);
        } else if (FileUtil.exists(OLD_INNER_UDID_FILE_PATH)) {
            String oldUdid = FileUtil.readFileFirstLine(OLD_INNER_UDID_FILE_PATH);
            if (!TextUtils.isEmpty(UDID) && isUDIDValid(context, oldUdid)) {
                asyncSaveUDIDToInternalStorage(oldUdid);
                return oldUdid;
            } else {
                FileUtil.deletePath(OLD_INNER_UDID_DIR_PATH);
            }
        }
        return "";
    }

    private static void saveUDIDToFS(Context context, String udid) {
        String filePath = UDID_FILE_PATH;
        if (!TextUtils.isEmpty(filePath)) {
            File file = new File(filePath);
            file.getParentFile().mkdirs();

            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                StringBuilder line = new StringBuilder();
                String imei = SystemUtil.getImei(context);

                line.append(udid);
                if (!TextUtils.isEmpty(imei)) {
                    String imeiHashed = DigestUtils.getStringMD5(imei);
                    if (!TextUtils.isEmpty(imeiHashed)) {
                        line.append("\t");
                        line.append(imeiHashed);
                    }
                }
                writer.write(line.toString());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void saveFirstChannelToFS(String firstChannel) {
        String filePath = FIRST_CHANNEL_FILE_PATH;
        if (!TextUtils.isEmpty(filePath)) {
            File file = new File(filePath);
            file.getParentFile().mkdirs();

            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(firstChannel);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String generateUDID(Context context) {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("-", "");
        return generateUDID(context, uuid);
    }

    private static String loadFromSP(Context context, String key) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getString(key, "");
    }

    private static void asyncSaveToSP(final Context context, final String key, final String content) {
        (new Thread() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(content)) {
                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
                    Editor editor = pref.edit();
                    editor.putString(key, content);
                    SharePrefSubmitor.submit(editor);
                }
            }
        }).start();
    }

    private static int readUDID(Context context) {
        if (!TextUtils.isEmpty(UDID)) {
            return READ_TYPE_NONE;
        }
        UDID = loadUDIDFromInternalStorage(context);
        if (!TextUtils.isEmpty(UDID) && isUDIDValid(context, UDID)) {
            return READ_TYPE_INNER;
        }
        UDID = loadFromSP(context, UDID_KEY);
        if (!TextUtils.isEmpty(UDID) && isUDIDValid(context, UDID)) {
            return READ_TYPE_SP;
        }
        UDID = loadUDIDFromFS(context);
        if (!TextUtils.isEmpty(UDID) && isUDIDValid(context, UDID)) {
            return READ_TYPE_FS;
        }
        UDID = generateUDID(context);
        return READ_TYPE_CREATE;
    }

    private static void saveUDID(Context context, int type) {
        switch (type) {
            case READ_TYPE_INNER:
                asyncSaveToSP(context, UDID_KEY, UDID);
                asyncWriteToFSDelayed(context.getApplicationContext(), UDID, TYPE_UDID);
                break;
            case READ_TYPE_SP:
                asyncSaveUDIDToInternalStorage(UDID);
                asyncWriteToFSDelayed(context.getApplicationContext(), UDID, TYPE_UDID);
                break;
            case READ_TYPE_FS:
                asyncSaveUDIDToInternalStorage(UDID);
                asyncSaveToSP(context, UDID_KEY, UDID);
                break;
            case READ_TYPE_CREATE:
                asyncSaveUDIDToInternalStorage(UDID);
                asyncSaveToSP(context, UDID_KEY, UDID);
                asyncWriteToFSDelayed(context.getApplicationContext(), UDID, TYPE_UDID);
                break;
            default:
                break;
        }
    }

    public static String getUDID(Context context) {
        synchronized (UDIDUtil.class) {
            int type = readUDID(context);
            saveUDID(context, type);
        }
        return UDID;
    }

    /**
     * Delay Write UDID to FS because it will cost 80 ms, and delay the UI show time.
     *
     * @param context
     * @param content
     */
    private static void asyncWriteToFSDelayed(final Context context, final String content,
                                              final int type) {
        mainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                asyncWriteToFS(context, content, type);
            }
        }, DELAY_WRITE_FS_TIME);
    }

    private static void asyncWriteToFS(final Context context, final String content, final int type) {
        new Thread() {
            @Override
            public void run() {
                synchronized (UDIDUtil.class) {
                    switch (type) {
                        case TYPE_UDID:
                            saveUDIDToFS(context, content);
                            break;
                        case TYPE_FIRST_CHANNEL:
                            saveFirstChannelToFS(content);
                            break;
                        default:
                            break;
                    }
                }
            }
        }.start();
    }

    private static void changeInnerFilePermission() {
        try {
            Runtime.getRuntime().exec("chmod 777 " + INNER_UDID_DIR_PATH);
            Runtime.getRuntime().exec("chmod 666 " + INNER_UDID_FILE_PATH);
        } catch (Exception e) {
        }
    }

    private static void changeInnerRootDirPermission() {
        try {
            Runtime.getRuntime().exec("chmod 777 " + INNER_ROOT_DIR);
        } catch (Exception e) {
        }
    }

    private static void asyncSaveUDIDToInternalStorage(final String udid) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    changeInnerRootDirPermission();
                    FileUtil.deletePath(OLD_INNER_UDID_DIR_PATH);
                    FileUtil.deleteFile(INNER_UDID_FILE_PATH);
                    File file = new File(INNER_UDID_FILE_PATH);
                    file.getParentFile().mkdirs();
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                    writer.write(udid);
                    writer.close();
                    changeInnerFilePermission();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
