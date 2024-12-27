package com.samsung.android.server.pm;

import android.os.Environment;
import android.os.FileUtils;
import android.util.EventLog;
import android.util.Slog;

import com.android.internal.util.FastPrintWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class PmLog {
    public static void logDebugInfo(String str) {
        File file = new File(new File(Environment.getDataDirectory(), "log"), "pm_debug_info.txt");
        try {
            FastPrintWriter fastPrintWriter = new FastPrintWriter(new FileOutputStream(file, true));
            try {
                fastPrintWriter.println(
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
                                        .format(new Date(System.currentTimeMillis()))
                                + ": "
                                + str);
                FileUtils.setPermissions(file.toString(), 508, -1, -1);
                if (file.length() > 3145728) {
                    file.renameTo(new File(file.getPath() + ".old"));
                }
                fastPrintWriter.close();
            } finally {
            }
        } catch (IOException unused) {
        }
    }

    public static void logDebugInfoAndLogcat(String str) {
        logDebugInfoAndLogcat(str, "PackageManager");
    }

    public static void logDebugInfoAndLogcat(String str, String str2) {
        Slog.i(str2, str);
        logDebugInfo(str);
    }

    public static void logFinishedScanningInfo(int i, int i2, long j, String str) {
        StringBuilder sb = new StringBuilder("Finished scanning ");
        sb.append(str);
        sb.append(" apps. Time: ");
        sb.append(j);
        sb.append(" ms, packageCount: ");
        sb.append(i);
        sb.append(" , timePerPackage: ");
        sb.append(i == 0 ? 0L : j / i);
        sb.append(" , cached: ");
        sb.append(i2);
        EventLog.writeEvent(3090, sb.toString());
    }
}
