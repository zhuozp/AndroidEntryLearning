package com.example.zhipengzhuo.entrytask.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

public class PreferenceUtils {

    public static final String KEY_INIT_DATA = "key_INIT_DATA";

    public static void saveBoolean(Context context, String key, boolean value) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        if (pref != null) {
            SharedPreferences.Editor edit = pref.edit();
            edit.putBoolean(key, value);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                edit.apply();
            } else {
                edit.commit();
            }
        }
    }

    public static boolean getBoolean(Context context, String key, boolean value) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        if (pref != null) {
            return pref.getBoolean(key, value);
        }

        return value;
    }

    public static void saveString(Context context, String key, String value) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        if (pref != null) {
            SharedPreferences.Editor edit = pref.edit();
            edit.putString(key, value);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                edit.apply();
            } else {
                edit.commit();
            }
        }
    }

    public static String getString(Context context, String key, String value) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        if (pref != null) {
            return pref.getString(key, value);
        }

        return value;
    }

    public static void saveLong(Context context, String key, long value) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        if (pref != null) {
            SharedPreferences.Editor edit = pref.edit();
            edit.putLong(key, value);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                edit.apply();
            } else {
                edit.commit();
            }
        }
    }

    public static long getLong(Context context, String key, long value) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        if (pref != null) {
            return pref.getLong(key, value);
        }

        return value;
    }

    public static void saveInt(Context context, String key, int value) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        if (pref != null) {
            SharedPreferences.Editor edit = pref.edit();
            edit.putInt(key, value);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                edit.apply();
            } else {
                edit.commit();
            }
        }
    }

    public static int getInt(Context context, String key, int value) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        if (pref != null) {
            return pref.getInt(key, value);
        }

        return value;
    }
}
