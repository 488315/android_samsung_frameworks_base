package com.samsung.android.server.pm.scan;

import android.content.Context;
import android.os.FileUtils;
import com.samsung.android.server.pm.PmLog;
import com.samsung.android.server.pm.PmSharedPreferences;
import java.io.File;

/* loaded from: classes2.dex */
public abstract class CacheCorruptionChecker {
    public static boolean isPackageCacheCorrupted(Context context) {
        return PmSharedPreferences.getBoolean(context, "key_scan_started", false);
    }

    public static void setPackageScanStarted(Context context, boolean z) {
        PmSharedPreferences.putBoolean(context, "key_scan_started", z);
    }

    public static void deletePackageCaches(File file) {
        if (file != null) {
            PmLog.logCriticalInfoAndLogcat("Delete package caches due to corruption");
            FileUtils.deleteContents(file);
        }
    }
}
