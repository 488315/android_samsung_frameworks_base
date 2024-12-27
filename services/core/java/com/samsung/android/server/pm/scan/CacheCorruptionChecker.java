package com.samsung.android.server.pm.scan;

import android.content.Context;
import android.os.Environment;

import com.samsung.android.server.pm.PmSharedPreferences;

import java.io.File;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
