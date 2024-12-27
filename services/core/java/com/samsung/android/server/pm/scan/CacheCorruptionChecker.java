package com.samsung.android.server.pm.scan;

import android.content.Context;
import android.os.Environment;

import com.samsung.android.server.pm.PmSharedPreferences;

import java.io.File;

public abstract class CacheCorruptionChecker {
    public static void setPackageScanStarted(Context context, boolean z) {
        synchronized (PmSharedPreferences.class) {
            context.createDeviceProtectedStorageContext()
                    .getSharedPreferences(
                            new File(
                                    Environment.getDataSystemDirectory(),
                                    "samsung_pm_settings.xml"),
                            0)
                    .edit()
                    .putBoolean("key_scan_started", z)
                    .apply();
        }
    }
}
