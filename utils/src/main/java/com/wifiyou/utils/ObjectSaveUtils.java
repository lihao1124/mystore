package com.wifiyou.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author dengzhiren@wifiyou.net (Dean)
 *         7/21/16
 */
public class ObjectSaveUtils {

    public static synchronized void saveObject(Context context, String key, Object object) throws IOException {
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (!sdCardExist) {
            return;
        }
        File file = new File(context.getFilesDir(), key);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(object);
        objectOutputStream.flush();
        IOUtils.close(fileOutputStream);
        IOUtils.close(objectOutputStream);
    }

    public static synchronized Object retrieveObject(Context context, String key) throws IOException, ClassNotFoundException {
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (!sdCardExist) {
            return null;
        }
        File file = new File(context.getFilesDir(), key);
        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Object object = objectInputStream.readObject();
        IOUtils.close(fileInputStream);
        IOUtils.close(objectInputStream);
        return object;
    }

}
