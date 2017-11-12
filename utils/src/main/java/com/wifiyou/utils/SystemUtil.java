package com.wifiyou.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.os.StatFs;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;

public class SystemUtil {
    private static final String BUILD_PROP_FILE = "/system/build.prop";
    private static final String PROP_NAME_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static String appVersionName = null;
    private static int versionCode = 0;

    private SystemUtil() {
    }

    public static boolean aboveApiLevel(int sdkInt) {
        return getApiLevel() >= sdkInt;
    }

    public static int getApiLevel() {
        return Build.VERSION.SDK_INT;
    }

    public static boolean isBlur() {
        try {
            return Build.BRAND.toLowerCase().contains("blur");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isZTE() {
        try {
            return Build.BRAND.toLowerCase().contains("zte");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isGalaxyS2() {
        try {
            String lowerCaseModel = Build.MODEL.toLowerCase();
            return lowerCaseModel.contains("gt-i9100")
                    || lowerCaseModel.contains("gt-i9108")
                    || lowerCaseModel.contains("gt-i9103");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isMX() {
        try {
            String lowerCaseModel = Build.MODEL.toLowerCase();
            return lowerCaseModel.contains("m353")
                    || lowerCaseModel.contains("mx4")
                    || lowerCaseModel.contains("m040");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isNexusS() {
        try {
            String lowerCaseModel = Build.MODEL.toLowerCase();
            return lowerCaseModel.contains("nexus s");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isKindleFire() {
        try {
            return Build.MODEL.toLowerCase().contains("kindle fire");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isMIUI() {
        File buildPropFile = new File(BUILD_PROP_FILE);
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(buildPropFile));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(PROP_NAME_MIUI_VERSION_CODE)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isSmartisan() {
        try {
            return Build.BRAND.toLowerCase().contains("smartisan");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isFlymeOs() {
        try {
            return Build.FINGERPRINT.toLowerCase().contains("flyme");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isHuaWeiG520() {
        try {
            return Build.MODEL.toUpperCase().contains("HUAWEI G520");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isSDCardMounted() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    public static boolean isExternalSDCardMounted() {
        try {
            if (Build.VERSION.SDK_INT < 11) {
                return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
            } else {
                return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                        && !Environment.isExternalStorageEmulated();
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static String getMacAddress(Context context) {
        WifiManager wifi = null;
        try {
            wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        } catch (Throwable th) {
            // in some special case or rom, it maybe throw exception.
            th.printStackTrace();
        }
        if (wifi == null) {
            return null;
        }
        WifiInfo info = null;
        try {
            // here maybe throw exception in android framework
            info = wifi.getConnectionInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (info != null) {
            return info.getMacAddress();
        } else {
            return null;
        }
    }

    public static String getWifiIPAddress(Context context) {
        try {
            WifiManager mgr = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            if (mgr == null) {
                return null;
            }

            WifiInfo info = mgr.getConnectionInfo();
            if (info == null) {
                return null;
            }
            // if (info.getSSID() == null) return null;

            int ipAddress = info.getIpAddress();
            if (ipAddress == 0) {
                return null;
            }

            String ip = String.format(Locale.US, "%d.%d.%d.%d", (ipAddress & 0xff),
                    (ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff),
                    (ipAddress >> 24 & 0xff));

            return ip;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getSecureAndroidID(Context context) {
        return Secure
                .getString(context.getContentResolver(), Secure.ANDROID_ID);
    }

    public static String getSdkVersion() {
        try {
            return Build.VERSION.SDK;
        } catch (Exception e) {
            e.printStackTrace();
            return String.valueOf(getSdkVersionInt());
        }
    }

    public static String getSdkReleaseVersion() {
        try {
            return Build.VERSION.RELEASE;
        } catch (Exception e) {
            e.printStackTrace();
            return getSdkVersion();
        }
    }

    public static int getSdkVersionInt() {
        try {
            return Build.VERSION.SDK_INT;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * if the external storage device which is emulated, that mean the devices
     * does not have real external storage ,result includes that devices.
     *
     * @return
     */
    public static long getAvailableExternalStorage() {
        try {
            File file = Environment.getExternalStorageDirectory();
            if (file != null && file.exists()) {
                StatFs sdFs = new StatFs(file.getPath());
                if (sdFs != null) {
                    long sdBlockSize = sdFs.getBlockSize();
                    long sdAvailCount = sdFs.getAvailableBlocks();
                    return sdAvailCount * sdBlockSize;
                }
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static long getTotalExternalMemorySize() {
        try {
            File file = Environment.getExternalStorageDirectory();
            if (file != null && file.exists()) {
                StatFs sdFs = new StatFs(file.getPath());
                if (sdFs != null) {
                    long sdBlockSize = sdFs.getBlockSize();
                    long sdTotalCount = sdFs.getBlockCount();
                    return sdTotalCount * sdBlockSize;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long getAvailableInternalStorage() {
        File file = Environment.getDataDirectory();
        if (file != null && file.exists()) {
            StatFs sdFs = new StatFs(file.getPath());
            if (sdFs != null) {
                long sdBlockSize = sdFs.getBlockSize();
                long sdAvailCount = sdFs.getAvailableBlocks();
                return sdAvailCount * sdBlockSize;
            }
        }
        return 0;
    }

    public static long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        if (path != null && path.exists()) {
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return totalBlocks * blockSize;
        }
        return 0;
    }

    public static float getTotalMemorySize() {
        return getMemInfoValue("MemTotal");
    }

    private static float getMemInfoValue(String key) {
        RandomAccessFile reader = null;
        try {
            reader = new RandomAccessFile("/proc/meminfo", "r");
            String load;
            while ((load = reader.readLine()) != null) {
                if (load.contains(key)) {
                    return Float.valueOf(load.replaceAll("[^\\d]", ""));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
        }
        return 0;
    }

    public static float getFreeMemory() {
        return getMemInfoValue("MemFree");
    }

    public static float getUsedMemeory() {
        return getTotalMemorySize() - getFreeMemory();
    }

    public static boolean checkSdCardStatusOk() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public static boolean checkAvailableInternalStorage(long size) {
        long availabelStorage = SystemUtil.getAvailableInternalStorage();
        // if apkSize is -1 , do not check
        if (size < 0) {
            return true;
        }
        if (availabelStorage <= 0) {
            return false;
        }
        return availabelStorage >= size;
    }

    public static boolean checkAvailableExternalStorage(long size) {
        long availabelStorage = SystemUtil.getAvailableExternalStorage();
        // if apkSize is -1 , do not check
        if (size < 0) {
            return true;
        }
        if (availabelStorage <= 0) {
            return false;
        }
        return availabelStorage >= size;
    }

    public static boolean checkSpaceEnough(String path, InstallOption installOpition) {

        if (TextUtils.isEmpty(path) || installOpition == null) {
            return false;
        }
        if (installOpition == InstallOption.AUTO) {
            return true;
        }
        File file = new File(path);
        if (installOpition == InstallOption.INTERNAL) {
            return SystemUtil.checkAvailableInternalStorage(file.length());
        }
        if (installOpition == InstallOption.EXTERNAL) {
            return SystemUtil.checkAvailableStorage(file.length());
        }

        return false;
    }

    public static boolean checkAvailableStorage(long size) {
        long availabelStorage = SystemUtil.getAvailableExternalStorage();
        // if apkSize is -1 , do not check
        if (size < 0) {
            return true;
        }
        if (availabelStorage <= 0) {
            return false;
        }
        return availabelStorage >= size;
    }

    public static int getVersionCode(Context context) {
        if (versionCode != 0) {
            return versionCode;
        }
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            versionCode = packageInfo.versionCode;
            return versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void sleepZero() {
        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String getNonNullModel() {
        if (TextUtils.isEmpty(Build.MODEL)) {
            return "";
        } else {
            return Build.MODEL;
        }
    }

    public static IBinder invokeGetService(String name) {
        Method method;
        try {
            method = Class.forName("android.os.ServiceManager").getMethod(
                    "getService", String.class);
        } catch (SecurityException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        IBinder binder;
        try {
            binder = (IBinder) method.invoke(null, name);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
        return binder;
    }

    /**
     * Gets external cache dir.
     *
     * @return cache dir. Can be null is external storage is unmounted
     */
    @TargetApi(Build.VERSION_CODES.FROYO)
    public static File getDeviceExternalCacheDir(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            return context.getExternalCacheDir();
        } else {
            return new File(Environment.getExternalStorageDirectory() + "/Android/data/"
                    + context.getPackageName() + "/cache/");
        }
    }

    public static String getImei(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            return telephonyManager.getDeviceId();
        } catch (Exception e) {
            // In some devices, we are not able to get device id, and may cause some exception,
            // so catch it.
            return "";
        }
    }

    public static String getVersionName(Context context) {
        if (appVersionName == null && context != null) {
            PackageInfo packageInfo = getPackageInfo(context, context.getPackageName(), 0);
            if (packageInfo != null) {
                appVersionName = packageInfo.versionName;
            } else {
                appVersionName = "";
            }

        }
        return appVersionName;
    }

    public static String getFullVersion(Context context) {
        return getVersionName(context)
                + "." + getVersionCode(context);
    }

    public static PackageInfo getPackageInfo(Context context, String packageName, int flag) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(packageName, flag);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            // In some ROM, there will be a PackageManager has died exception. So we catch it here.
            e.printStackTrace();
        }
        return packageInfo;
    }

    /**
     * get user config locale, if null, return default locale.
     *
     * @param context
     * @return locale
     */
    public static Locale getLocale(Context context) {
        Locale locale = null;
        try {
            Configuration userConfig = new Configuration();
            Settings.System.getConfiguration(context.getContentResolver(), userConfig);
            locale = userConfig.locale;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (locale == null) {
            locale = Locale.getDefault();
        }
        return locale;
    }

    /**
     * get android id, use content provider, don't use this method on main thread.
     *
     * @param context
     * @return android id
     */
    public static String getAndroidId(Context context) {
        return Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
    }

    @TargetApi(Build.VERSION_CODES.FROYO)
    public static String getScreenResolution(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) (context
                .getSystemService(Context.WINDOW_SERVICE));
        String resolution = "unknown";
        if (wm != null && wm.getDefaultDisplay() != null) {
            wm.getDefaultDisplay().getMetrics(metrics);
            if (SystemUtil.aboveApiLevel(Build.VERSION_CODES.FROYO)) {
                switch (wm.getDefaultDisplay().getRotation()) {
                    case Surface.ROTATION_90:
                    case Surface.ROTATION_270:
                        resolution = String.valueOf(metrics.heightPixels) + "*"
                                + String.valueOf(metrics.widthPixels);
                        break;
                    default:
                        resolution = String.valueOf(metrics.widthPixels) + "*"
                                + String.valueOf(metrics.heightPixels);
                }
            } else {
                resolution = String.valueOf(metrics.widthPixels) + "*"
                        + String.valueOf(metrics.heightPixels);
            }
        }
        return resolution;
    }

    public static boolean hasSoftKeys(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }
        return false;
    }

    /**
     * judge device is a smartphone or tablet.
     *
     * @param context application context.
     * @return whether device is tablet.
     */
    public static boolean isDeviceTablet(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context can't be null");
        }
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public enum InstallOption {
        AUTO, EXTERNAL, INTERNAL, ERROR
    }

    /**
     * check if the mobile has been rooted
     *
     * @return the mobile has been rooted
     * @throws IOException
     * @author TQS
     */
    public static boolean isRooted() {
        boolean rooted = false;
        boolean hasSuFile = false;
        String command = "ls -l /%s/su";
        File su = new File("/system/bin/su");
        if (su.exists()) {
            hasSuFile = true;
            command = String.format(command, "system/bin");
        } else {
            su = new File("/system/xbin/su");
            if (su.exists()) {
                hasSuFile = true;
                command = String.format(command, "system/xbin");
            } else {
                su = new File("/data/bin/su");
                if (su.exists()) {
                    hasSuFile = true;
                    command = String.format(command, "data/bin");
                }
            }
        }

        if (hasSuFile == true) {
            rooted = true;
        }

        return rooted;
    }

    public static String getCPU() {
        String cpuInfo = null;
        FileReader fileReader;
        BufferedReader in = null;
        try {
            fileReader = new FileReader("/proc/cpuinfo");
            try {
                in = new BufferedReader(fileReader, 1024);
                cpuInfo = in.readLine();
            } catch (IOException e) {
                return "unknown";
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                    fileReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            return "unknown";
        }

        if (cpuInfo != null) {
            int start = cpuInfo.indexOf(':') + 1;
            cpuInfo = cpuInfo.substring(start);
            return cpuInfo.trim();
        }
        return "unknown";
    }

    public static String getSystemDisplayId() {
        if (TextUtils.isEmpty(Build.DISPLAY)) {
            return "";
        } else {
            return Build.DISPLAY;
        }
    }

    public static String getBrand() {
        if (TextUtils.isEmpty(Build.BRAND)) {
            return "";
        } else {
            return Build.BRAND;
        }
    }

    @TargetApi(Build.VERSION_CODES.DONUT)
    public static int getMetricsSize(WindowManager windowManager) {
        if (windowManager == null) {
            return 0;
        }
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return metrics.densityDpi;
    }

    @TargetApi(Build.VERSION_CODES.DONUT)
    public static String getDpi(WindowManager windowManager) {
        if (windowManager == null) {
            return "";
        }
        int densityDpi = getMetricsSize(windowManager);
        switch (densityDpi) {
            case DisplayMetrics.DENSITY_LOW:
                return "ldpi";
            case DisplayMetrics.DENSITY_MEDIUM:
                return "mdpi";
            case DisplayMetrics.DENSITY_HIGH:
                return "hdpi";
            case DisplayMetrics.DENSITY_XHIGH:
                return "xhpdi";
            default:
                return "xxhdpi";
        }
    }

    /**
     * get current process name
     *
     * @param context
     * @return
     */
    public static String getProcessName(Context context) {
        int myPid = android.os.Process.myPid();
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processes = manager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo process : processes) {
            if (process.pid == myPid) {
                return process.processName;
            }
        }
        return "";
    }

    public static boolean hasSimCard(Context context) {
        TelephonyManager tm =
                (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return !(tm.getSimState() == TelephonyManager.SIM_STATE_ABSENT);
    }

    public static int getStatusBarHeight(Activity context) {
        int result = 0;
        //顶部状态栏(Status Bar)
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        } else {
            Rect rectangle = new Rect();
            Window window = context.getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(rectangle);//顶级的view，取到程序显示的区域，包括标题栏，但不包括状态栏。
            // 内容栏居上的高度 = 状态栏高度+标题栏的高度
            result = rectangle.top;//状态栏高度
//            int contentViewTop = window.findViewById(Window.ID_ANDROID_CONTENT).getTop();//内容栏居上的高度
//            result = contentViewTop - statusBarHeight;//标题栏的高度。
        }


        return result;
    }
}
