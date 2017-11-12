package com.wifiyou.utils;

import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PreferenceHelper {
    private static final String MODULE_DEFAULT = "default";

    private SharedPreferences mSharedPreferences;

    private PreferenceHelper() {
    }

    public static PreferenceHelper getInstance() {
//        MainThreadPostUtils.throwExceptionInUiThread();
        return Holder.sInstance;
    }

//    public static PreferenceHelper getInstanceMainThread() {
//        return Holder.sInstance;
//    }

    private void ensureInit() {

        if (mSharedPreferences != null) {
            return;
        }

        mSharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(GlobalConfig.getAppContext());
    }

    public String getString(String moduleName, String key, String defValue) {
        ensureInit();
        return mSharedPreferences.getString(moduleName + "_" + key, defValue);
    }

    public String getString(String key, String defValue) {
        ensureInit();
        return getString(MODULE_DEFAULT, key, defValue);
    }

    public void saveString(String moduleName, String key, String value) {
        ensureInit();
        saveString(moduleName, key, value, false);
    }

    public void saveString(String moduleName, String key, String value, boolean isSync) {
        if (moduleName != null && key != null) {
            ensureInit();
            if (isSync || isBelowGingerBread()) {
                mSharedPreferences.edit().putString(moduleName + "_" + key, value).commit();
            } else {
                mSharedPreferences.edit().putString(moduleName + "_" + key, value).apply();
            }
        }
    }

    public String saveString(String key, String value) {
        ensureInit();
        saveString(MODULE_DEFAULT, key, value);
        return key;
    }

    public Set<String> getStringSet(String moduleName, String key, Set<String> defValues) {
        ensureInit();
        return mSharedPreferences.getStringSet(moduleName + "_" + key, defValues);
    }

    public Set<String> getStringSet(String key, Set<String> defValues) {
        ensureInit();
        return getStringSet(MODULE_DEFAULT, key, defValues);
    }

    public void saveStringSet(String moduleName, String key, Set<String> values) {
        if (moduleName != null && key != null) {
            ensureInit();
            if (isBelowGingerBread()) {
                mSharedPreferences.edit().putStringSet(moduleName + "_" + key, values).commit();
            } else {
                mSharedPreferences.edit().putStringSet(moduleName + "_" + key, values).apply();
            }
        }
    }

    public void saveStringSet(String key, Set<String> values) {
        ensureInit();
        saveStringSet(MODULE_DEFAULT, key, values);
    }

    public int getInt(String moduleName, String key, int defValue) {
        ensureInit();
        return mSharedPreferences.getInt(moduleName + "_" + key, defValue);
    }

    public int getInt(String key, int defValue) {
        ensureInit();
        return getInt(MODULE_DEFAULT, key, defValue);
    }

    public void saveInt(String moduleName, String key, int value) {
        ensureInit();
        saveInt(moduleName, key, value, false);
    }

    public void saveInt(String moduleName, String key, int value, boolean isSync) {
        if (moduleName != null && key != null) {
            ensureInit();
            if (isSync || isBelowGingerBread()) {
                mSharedPreferences.edit().putInt(moduleName + "_" + key, value).commit();
            } else {
                mSharedPreferences.edit().putInt(moduleName + "_" + key, value).apply();
            }
        }
    }


    public void saveInt(String key, int value) {
        ensureInit();
        saveInt(MODULE_DEFAULT, key, value);
    }

    public long getLong(String moduleName, String key, long defValue) {
        ensureInit();
        return mSharedPreferences.getLong(moduleName + "_" + key, defValue);
    }

    public long getLong(String key, long defValue) {
        ensureInit();
        return getLong(MODULE_DEFAULT, key, defValue);
    }

    public void saveLong(String moduleName, String key, long value, boolean isSync) {
        if (moduleName != null && key != null) {
            ensureInit();
            if (isSync || isBelowGingerBread()) {
                mSharedPreferences.edit().putLong(moduleName + "_" + key, value).commit();
            } else {
                mSharedPreferences.edit().putLong(moduleName + "_" + key, value).apply();
            }
        }
    }

    public void saveLong(String moduleName, String key, long value) {
        saveLong(moduleName, key, value, false);
    }

    public void saveLong(String key, long value) {
        saveLong(MODULE_DEFAULT, key, value);
    }

    public float getFloat(String moduleName, String key, float defValue) {
        return mSharedPreferences.getFloat(moduleName + "_" + key, defValue);
    }

    public float getFloat(String key, float defValue) {
        return getFloat(MODULE_DEFAULT, key, defValue);
    }

    public void saveFloat(String moduleName, String key, float value) {
        saveFloat(moduleName, key, value, false);
    }

    public void saveFloat(String moduleName, String key, float value, boolean isSync) {
        if (moduleName != null && key != null) {
            if (isSync || isBelowGingerBread()) {
                mSharedPreferences.edit().putFloat(moduleName + "_" + key, value).commit();
            } else {
                mSharedPreferences.edit().putFloat(moduleName + "_" + key, value).apply();
            }
        }
    }


    public boolean getBoolean(String moduleName, String key, boolean defValue) {
        ensureInit();
        return mSharedPreferences.getBoolean(moduleName + "_" + key, defValue);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return getBoolean(MODULE_DEFAULT, key, defValue);
    }

    public void saveBoolean(String moduleName, String key, boolean value) {
        saveBoolean(moduleName, key, value, false);
    }

    public void saveBoolean(String moduleName, String key, boolean value, boolean isSync) {
        ensureInit();
        if (moduleName != null && key != null) {
            if (isSync || isBelowGingerBread()) {
                mSharedPreferences.edit().putBoolean(moduleName + "_" + key, value).commit();
            } else {
                mSharedPreferences.edit().putBoolean(moduleName + "_" + key, value).apply();
            }
        }
    }

    public void saveBoolean(String key, boolean value) {
        saveBoolean(MODULE_DEFAULT, key, value);
    }

    private Map<String, ?> getAll(String moduleName, boolean ignoreKeyPrefix) {
        Map<String, ?> allData = mSharedPreferences.getAll();
        Map<String, Object> moduleData = new HashMap<String, Object>();

        Set<String> keySet = allData.keySet();
        for (String key : keySet) {
            String indexStr = moduleName + "_";
            if (key != null && key.startsWith(indexStr)) {
                String k = ignoreKeyPrefix ? key.substring(indexStr.length()) : key;
                moduleData.put(k, allData.get(key));
            }
        }
        return moduleData;
    }

    public Map<String, ?> getAll(String moduleName) {
        return getAll(moduleName, false);
    }

    public Map<String, ?> getAllIgnoreKeyPrefix(String moduleName) {
        return getAll(moduleName, true);
    }

    public void remove(String moduleName, String key, boolean isSync) {
        if (moduleName != null && key != null) {
            if (isBelowGingerBread()) {
                mSharedPreferences.edit().remove(moduleName + "_" + key).commit();
            } else {
                mSharedPreferences.edit().remove(moduleName + "_" + key).apply();
            }
        }
    }

    public void remove(String moduleName, String key) {
        remove(moduleName, key, false);
    }

    public boolean contains(String moduleName, String key) {
        if (moduleName != null && key != null) {
            return mSharedPreferences.contains(moduleName + "_" + key);
        }
        return false;
    }

    public void clear(String moduleName) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();

        Map<String, ?> moduleData = getAll(moduleName);
        Set<String> keySet = moduleData.keySet();
        for (String key : keySet) {
            editor.remove(key);
        }

        if (isBelowGingerBread()) {
            editor.commit();
        } else {
            editor.apply();
        }
    }

    /**
     * below API level 9 should not use apply method
     *
     * @return
     */
    private boolean isBelowGingerBread() {
        return SystemUtil.getApiLevel() < Build.VERSION_CODES.GINGERBREAD;
    }

    public void registerOnSharedPreferenceChangeListener(
            SharedPreferences.OnSharedPreferenceChangeListener listener) {
        mSharedPreferences.registerOnSharedPreferenceChangeListener(listener);
    }

    public void unregisterOnSharedPreferenceChangeListener(
            SharedPreferences.OnSharedPreferenceChangeListener listener) {
        mSharedPreferences.unregisterOnSharedPreferenceChangeListener(listener);
    }

    private static class Holder {
        private static PreferenceHelper sInstance = new PreferenceHelper();
    }


}
