package com.wifiyou.utils;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class FileUtil {

  public static void deleteFile(String path) {
    if (TextUtils.isEmpty(path)) {
      return;
    }
    File file = new File(path);
    if (!file.exists() || !file.isFile()) {
      return;
    }
    file.delete();
  }

  public static boolean exists(String path) {
    if (TextUtils.isEmpty(path)) {
      return false;
    }
    File file = new File(path);
    return file.exists();
  }

  public static String readFileFirstLine(String filePath) {
    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new FileReader(filePath));
      String line = reader.readLine();
      reader.close();
      return line;
    } catch (Exception e) {
      return "";
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public static void deletePath(String path) {
    File file = new File(path);
    if (!file.exists()) {
      return;
    }
    if (file.isFile()) {
      file.delete();
      return;
    }
    String[] tmpList = file.list();
    if (tmpList == null) {
      return;
    }
    for (String fileName : tmpList) {
      if (fileName == null) {
        continue;
      }
      String tmpPath = null;
      if (path.endsWith(File.separator)) {
        tmpPath = path + fileName;
      } else {
        tmpPath = path + File.separator + fileName;
      }
      File tmpFile = new File(tmpPath);
      if (tmpFile.isFile()) {
        tmpFile.delete();
      }
      if (tmpFile.isDirectory()) {
        deletePath(tmpPath);
      }
    }
    file.delete();
  }

  public static void setPermissions(String path, int permissions) {
    JavaCalls.callStaticMethod("android.os.FileUtils", path, permissions, -1, -1);
  }

  public static void write(String path, String content, boolean append) {
    FileWriter writer = null;
    try {
      File output = new File(path);
      if (!output.exists()) {
        output.getParentFile().mkdirs();
        output.createNewFile();
      }
      writer = new FileWriter(path, append);
      writer.write(content);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Throwable th) {
      th.printStackTrace();
    } finally {
      if (writer != null) {
        try {
          writer.close();
        } catch (Exception e) {
          // ignore
        }
      }
    }
  }

  /**
   * Check If the storage is able to download.
   *
   * @param file
   * @return able to write
   */
  public static boolean canWrite(File file) {
    if (file == null || !file.exists()) {
      return false;
    }

    String testName = "." + System.currentTimeMillis();
    File testFile = new File(file, testName);

    boolean result = testFile.mkdir();
    if (result) {
      result = testFile.delete();
    }
    return result;
  }

  public static boolean renameFile(String originPath, String destPath) {
    File origin = new File(originPath);
    File dest = new File(destPath);
    if (!origin.exists()) {
      return false;
    }
    return origin.renameTo(dest);
  }

  /**
   * <b>return the available size of filesystem<b/>
   *
   * @return the number of bytes available on the filesystem rooted at the
   *         given File
   */
  public static long getAvailableBytes(String root) {
    try {
      if (!TextUtils.isEmpty(root) && new File(root).exists()) {
        StatFs stat = new StatFs(root);
        long availableBlocks = (long) stat.getAvailableBlocks();
        return stat.getBlockSize() * availableBlocks;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return 0;
  }

  /**
   * Whether the Uri authority is DownloadsProvider.
   *
   * @param uri
   *            The Uri to check.
   */
  public static boolean isDownloadsDocument(Uri uri) {
    return "com.android.providers.downloads.documents".equals(uri
            .getAuthority());
  }

  /**
   * Whether the Uri authority is ExternalStorageProvider.
   *
   * @param uri
   *            The Uri to check.
   */
  public static boolean isExternalStorageDocument(Uri uri) {
    return "com.android.externalstorage.documents".equals(uri
            .getAuthority());
  }

  /**
   * Get the value of the data column for this Uri. This is useful for
   * MediaStore Uris, and other file-based ContentProviders.
   *
   * @param context
   *            The context.
   * @param uri
   *            The Uri to query.
   * @param selection
   *            (Optional) Filter used in the query.
   * @param selectionArgs
   *            (Optional) Selection arguments used in the query.
   * @return The value of the _data column, which is typically a file path.
   */
  public static String getDataColumn(Context context, Uri uri,
                                     String selection, String[] selectionArgs) {

    Cursor cursor = null;
    final String column = "_data";
    final String[] projection = { column };

    try {
      cursor = context.getContentResolver().query(uri, projection,
              selection, selectionArgs, null);
      if (cursor != null && cursor.moveToFirst()) {

        final int column_index = cursor.getColumnIndexOrThrow(column);
        return cursor.getString(column_index);
      }
    } finally {
      if (cursor != null)
        cursor.close();
    }
    return null;
  }

  /**
   * @param uri
   *            The Uri to check.
   * @return Whether the Uri authority is MediaProvider.
   * @author paulburke
   */
  public static boolean isMediaDocument(Uri uri) {
    return "com.android.providers.media.documents".equals(uri
            .getAuthority());
  }

  /**
   * Whether the Uri authority is Google Photos.
   *
   * @param uri
   *            The Uri to check.
   */
  public static boolean isGooglePhotosUri(Uri uri) {
    return "com.google.android.apps.photos.content".equals(uri
            .getAuthority());
  }

  /**
   * Get a file path from a Uri. This will get the the path for Storage Access
   * Framework Documents, as well as the _data field for the MediaStore and
   * other file-based ContentProviders.<br>
   * <br>
   * Callers should check whether the path is local before assuming it
   * represents a local file.
   *
   * @param context
   *            The context.
   * @param uri
   *            The Uri to query.
   * @see #isLocal(String)
   * @see #getFile(Context, String, Uri)
   * @author paulburke
   */
  public static String getPath(final Context context,
                               final String localAuthority, final Uri uri) {

    final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

    // DocumentProvider
    if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
      // LocalStorageProvider
      if (!TextUtils.isEmpty(localAuthority)
              && localAuthority.equals(uri.getAuthority())) {
        // The path is the id
        return DocumentsContract.getDocumentId(uri);
      }
      // ExternalStorageProvider
      else if (isExternalStorageDocument(uri)) {
        final String docId = DocumentsContract.getDocumentId(uri);
        final String[] split = docId.split(":");
        final String type = split[0];

        if ("primary".equalsIgnoreCase(type)) {
          return Environment.getExternalStorageDirectory() + "/"
                  + split[1];
        }
        // TODO handle non-primary volumes
      }
      // DownloadsProvider
      else if (isDownloadsDocument(uri)) {

        final String id = DocumentsContract.getDocumentId(uri);
        final Uri contentUri = ContentUris.withAppendedId(
                Uri.parse("content://downloads/public_downloads"),
                Long.valueOf(id));

        return getDataColumn(context, contentUri, null, null);
      }
      // MediaProvider
      else if (isMediaDocument(uri)) {
        final String docId = DocumentsContract.getDocumentId(uri);
        final String[] split = docId.split(":");
        final String type = split[0];

        Uri contentUri = null;
        if ("image".equals(type)) {
          contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        } else if ("video".equals(type)) {
          contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        } else if ("audio".equals(type)) {
          contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        }

        final String selection = "_id=?";
        final String[] selectionArgs = new String[] { split[1] };

        return getDataColumn(context, contentUri, selection,
                selectionArgs);
      }
    }
    // MediaStore (and general)
    else if ("content".equalsIgnoreCase(uri.getScheme())) {

      // Return the remote address
      if (isGooglePhotosUri(uri))
        return uri.getLastPathSegment();

      return getDataColumn(context, uri, null, null);
    }
    // File
    else if ("file".equalsIgnoreCase(uri.getScheme())) {
      return uri.getPath();
    }

    return null;
  }
}
