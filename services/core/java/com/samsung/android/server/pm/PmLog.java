package com.samsung.android.server.pm;

import android.os.Environment;
import android.os.FileUtils;
import android.os.UserHandle;
import android.util.EventLog;
import android.util.Slog;
import com.android.internal.util.FastPrintWriter;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.PackageSetting;
import com.android.server.pm.pkg.PackageUserStateInternal;
import com.android.server.utils.WatchedArrayMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public abstract class PmLog {
    public static void logCriticalInfoAndLogcat(String str) {
        PackageManagerService.reportSettingsProblem(3, str);
    }

    public static void logDebugInfoAndLogcat(String str) {
        logDebugInfoAndLogcat(str, "PackageManager");
    }

    public static void logDebugInfoAndLogcat(String str, String str2) {
        if (str2 == null) {
            str2 = "PackageManager";
        }
        Slog.i(str2, str);
        logDebugInfo(str);
    }

    public static void logDebugInfo(String str) {
        logToFile("pm_debug_info.txt", str);
    }

    public static void logToFile(String str, String str2) {
        File dumpFile = getDumpFile(str);
        try {
            FastPrintWriter fastPrintWriter = new FastPrintWriter(new FileOutputStream(dumpFile, true));
            try {
                fastPrintWriter.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(System.currentTimeMillis())) + ": " + str2);
                FileUtils.setPermissions(dumpFile.toString(), 508, -1, -1);
                backupDumpIfNeeded(dumpFile);
                fastPrintWriter.close();
            } finally {
            }
        } catch (IOException unused) {
        }
    }

    public static void dumpDebugInfos(PrintWriter printWriter) {
        File dumpFile = getDumpFile("pm_debug_info.txt");
        if (!dumpFile.exists()) {
            return;
        }
        printWriter.println("\npm_debug_info.txt:");
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(dumpFile));
            while (true) {
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine != null) {
                        printWriter.println(readLine);
                    } else {
                        bufferedReader.close();
                        return;
                    }
                } finally {
                }
            }
        } catch (Exception unused) {
        }
    }

    public static File getDumpFile(String str) {
        return new File(new File(Environment.getDataDirectory(), "log"), str);
    }

    public static void backupDumpIfNeeded(File file) {
        if (file == null || file.length() <= 3145728) {
            return;
        }
        file.renameTo(new File(file.getPath() + ".old"));
    }

    public static void logRoleHoldersChanged(String str, List list, UserHandle userHandle) {
        logDebugInfoAndLogcat("Role holders for " + str + " changed. holders: " + list + ", UserId: " + userHandle.getIdentifier());
    }

    public static void logFinishedScanningInfo(String str, long j, int i, int i2, int i3) {
        StringBuilder sb = new StringBuilder();
        sb.append("Finished scanning ");
        sb.append(str);
        sb.append(" apps. Time: ");
        sb.append(j);
        sb.append(" ms, packageCount: ");
        sb.append(i);
        sb.append(" , timePerPackage: ");
        sb.append(i == 0 ? 0L : j / i);
        sb.append(" , cached: ");
        sb.append(i2);
        EventLog.writeEvent(i3, sb.toString());
    }

    public static void throwPackageState(PackageSetting packageSetting, WatchedArrayMap watchedArrayMap) {
        if (packageSetting == null) {
            return;
        }
        PackageUserStateInternal readUserState = packageSetting.readUserState(0);
        boolean isHidden = readUserState.isHidden();
        boolean isInstalled = readUserState.isInstalled();
        boolean isSuspended = readUserState.isSuspended();
        throw new RuntimeException("RequiredPackage can not be queried. pkg: " + packageSetting.getPackageName() + ", enabled: " + readUserState.getEnabledState() + ", lastDisableCaller: " + getParsedLastDisablerPackageName(readUserState.getLastDisableAppCaller(), watchedArrayMap) + ", isHidden: " + isHidden + ", isInstalled: " + isInstalled + ", isSuspended: " + isSuspended);
    }

    public static String getParsedLastDisablerPackageName(String str, WatchedArrayMap watchedArrayMap) {
        if (str != null) {
            try {
                if (str.startsWith("ApplicationPolicy")) {
                    int parseInt = Integer.parseInt(str.substring(20));
                    for (Map.Entry entry : watchedArrayMap.entrySet()) {
                        if (((PackageSetting) entry.getValue()).getAppId() == parseInt) {
                            return ((PackageSetting) entry.getValue()).getPackageName();
                        }
                    }
                }
            } catch (Exception e) {
                logDebugInfo("Fail to find Required Package Disabler : " + e);
            }
        }
        return str;
    }
}
