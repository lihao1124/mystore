package com.wifiyou.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by 45011 on 2017/7/12.
 */

public class ShortCutUtils {
    public static final String APPWIDGET_IS_SHORTCUT_INSTALLED = "app_widget_is_shortcut_intalled";// 是否已经创建快捷方式
    public static final String ACTION_ADD_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";
    public static final String ACTION_DEL_SHORTCUT = "com.android.launcher.action.UNINSTALL_SHORTCUT";

    public static void installShortCut(Context context, String name, @NonNull int img, Intent launcherIntent) {
        Intent intent = new Intent(ACTION_ADD_SHORTCUT);
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON, BitmapFactory.decodeResource(context.getResources(), img));
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, launcherIntent);
        intent.putExtra("duplicate", false);
        context.sendBroadcast(intent);
    }

    public static void uninstallShortCut(Context context, String name, Intent launcherIntent) {
        Intent intent = new Intent(ACTION_DEL_SHORTCUT);
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, launcherIntent);
        intent.putExtra("duplicate", false);
        context.sendBroadcast(intent);
    }

    public static boolean hasShortcut(Context activity, String shortcutName) {
        String url = "";
        url = "content://" + getAuthorityFromPermission(activity, "com.android.launcher.permission.READ_SETTINGS") + "/favorites?notify=true";
        ContentResolver resolver = activity.getContentResolver();
        Cursor cursor = resolver.query(Uri.parse(url), new String[]{"title", "iconResource"}, "title=?", new String[]{shortcutName}, null);
        if (cursor != null && cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        return false;
    }

    private static String getAuthorityFromPermission(Context context, String permission) {
        if (permission == null)
            return null;
        List<PackageInfo> packs = context.getPackageManager().getInstalledPackages(PackageManager.GET_PROVIDERS);
        if (packs != null) {
            for (PackageInfo pack : packs) {
                ProviderInfo[] providers = pack.providers;
                if (providers != null) {
                    for (ProviderInfo provider : providers) {
                        if (permission.equals(provider.readPermission))
                            return provider.authority;
                        if (permission.equals(provider.writePermission))
                            return provider.authority;
                    }
                }
            }
        }
        return null;
    }
}
