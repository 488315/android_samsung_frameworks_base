package com.samsung.android.server.pm;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import java.io.File;

/* loaded from: classes2.dex */
public abstract class PmSharedPreferences {
    public static boolean getBoolean(Context context, String str, boolean z) {
        boolean z2;
        synchronized (PmSharedPreferences.class) {
            z2 = context.createDeviceProtectedStorageContext().getSharedPreferences(new File(Environment.getDataSystemDirectory(), "samsung_pm_settings.xml"), 0).getBoolean(str, z);
        }
        return z2;
    }

    public static void putBoolean(Context context, String str, boolean z) {
        synchronized (PmSharedPreferences.class) {
            context.createDeviceProtectedStorageContext().getSharedPreferences(new File(Environment.getDataSystemDirectory(), "samsung_pm_settings.xml"), 0).edit().putBoolean(str, z).apply();
        }
    }

    public static void putLong(Context context, String str, long j, boolean z) {
        synchronized (PmSharedPreferences.class) {
            SharedPreferences sharedPreferences = context.createDeviceProtectedStorageContext().getSharedPreferences(new File(Environment.getDataSystemDirectory(), "samsung_pm_settings.xml"), 0);
            if (z) {
                sharedPreferences.edit().putLong(str, j).apply();
            } else {
                sharedPreferences.edit().putLong(str, j).commit();
            }
        }
    }

    public static Long getLong(Context context, String str, long j) {
        Long valueOf;
        synchronized (PmSharedPreferences.class) {
            valueOf = Long.valueOf(context.createDeviceProtectedStorageContext().getSharedPreferences(new File(Environment.getDataSystemDirectory(), "samsung_pm_settings.xml"), 0).getLong(str, j));
        }
        return valueOf;
    }

    public static void putString(Context context, String str, String str2, boolean z) {
        synchronized (PmSharedPreferences.class) {
            SharedPreferences sharedPreferences = context.createDeviceProtectedStorageContext().getSharedPreferences(new File(Environment.getDataSystemDirectory(), "samsung_pm_settings.xml"), 0);
            if (z) {
                sharedPreferences.edit().putString(str, str2).apply();
            } else {
                sharedPreferences.edit().putString(str, str2).commit();
            }
        }
    }

    public static String getString(Context context, String str, String str2) {
        String string;
        synchronized (PmSharedPreferences.class) {
            string = context.createDeviceProtectedStorageContext().getSharedPreferences(new File(Environment.getDataSystemDirectory(), "samsung_pm_settings.xml"), 0).getString(str, str2);
        }
        return string;
    }
}
