package com.android.server.rollback;

import android.content.Context;
import android.content.pm.VersionedPackage;
import android.os.SystemProperties;
import android.util.ArraySet;
import android.util.Slog;

import com.android.server.crashrecovery.proto.CrashRecoveryStatsLog;

import java.util.Iterator;
import java.util.List;

public abstract class WatchdogRollbackLogger {
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.content.pm.VersionedPackage getLogPackage(
            android.content.Context r5, android.content.pm.VersionedPackage r6) {
        /*
            java.lang.String r6 = r6.getPackageName()
            android.content.pm.PackageManager r0 = r5.getPackageManager()
            r1 = 1073741952(0x40000080, float:2.0000305)
            r2 = 0
            android.content.pm.PackageInfo r0 = r0.getPackageInfo(r6, r1)     // Catch: java.lang.Exception -> L1f
            android.content.pm.ApplicationInfo r0 = r0.applicationInfo     // Catch: java.lang.Exception -> L1f
            android.os.Bundle r0 = r0.metaData     // Catch: java.lang.Exception -> L1f
            if (r0 != 0) goto L18
        L16:
            r6 = r2
            goto L34
        L18:
            java.lang.String r1 = "android.content.pm.LOGGING_PARENT"
            java.lang.String r6 = r0.getString(r1)     // Catch: java.lang.Exception -> L1f
            goto L34
        L1f:
            r0 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r3 = "Unable to discover logging parent package: "
            r1.<init>(r3)
            r1.append(r6)
            java.lang.String r6 = r1.toString()
            java.lang.String r1 = "WatchdogRollbackLogger"
            android.util.Slog.w(r1, r6, r0)
            goto L16
        L34:
            if (r6 != 0) goto L37
            return r2
        L37:
            android.content.pm.VersionedPackage r0 = new android.content.pm.VersionedPackage     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L4a
            android.content.pm.PackageManager r5 = r5.getPackageManager()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L4a
            r1 = 0
            android.content.pm.PackageInfo r5 = r5.getPackageInfo(r6, r1)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L4a
            long r3 = r5.getLongVersionCode()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L4a
            r0.<init>(r6, r3)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L4a
            return r0
        L4a:
            return r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.rollback.WatchdogRollbackLogger.getLogPackage(android.content.Context,"
                    + " android.content.pm.VersionedPackage):android.content.pm.VersionedPackage");
    }

    public static void logApexdRevert(Context context, List list, String str) {
        ArraySet arraySet = new ArraySet();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arraySet.add(getLogPackage(context, new VersionedPackage((String) it.next(), 0)));
        }
        Iterator it2 = arraySet.iterator();
        while (it2.hasNext()) {
            logEvent((VersionedPackage) it2.next(), 2, 5, str);
        }
    }

    public static void logEvent(VersionedPackage versionedPackage, int i, int i2, String str) {
        boolean z;
        boolean z2;
        StringBuilder sb = new StringBuilder("Watchdog event occurred with type: ");
        sb.append(
                i != 1
                        ? i != 2
                                ? i != 3
                                        ? i != 4 ? "UNKNOWN" : "ROLLBACK_BOOT_TRIGGERED"
                                        : "ROLLBACK_FAILURE"
                                : "ROLLBACK_SUCCESS"
                        : "ROLLBACK_INITIATE");
        sb.append(" logPackage: ");
        sb.append(versionedPackage);
        sb.append(" rollbackReason: ");
        sb.append(rollbackReasonToString(i2));
        sb.append(" failedPackageName: ");
        sb.append(str);
        Slog.i("WatchdogRollbackLogger", sb.toString());
        if (versionedPackage != null) {
            z = false;
            z2 = true;
            CrashRecoveryStatsLog.write(
                    i,
                    versionedPackage.getPackageName(),
                    versionedPackage.getVersionCode(),
                    i2,
                    str,
                    new byte[0]);
        } else {
            z = false;
            z2 = true;
            CrashRecoveryStatsLog.write(i, "", 0, i2, str, new byte[0]);
        }
        if (SystemProperties.getBoolean("persist.sys.rollbacktest.enabled", z)) {
            String concat =
                    "persist.sys.rollbacktest."
                            .concat(
                                    i != z2
                                            ? i != 2
                                                    ? i != 3
                                                            ? i != 4
                                                                    ? "UNKNOWN"
                                                                    : "ROLLBACK_BOOT_TRIGGERED"
                                                            : "ROLLBACK_FAILURE"
                                                    : "ROLLBACK_SUCCESS"
                                            : "ROLLBACK_INITIATE");
            SystemProperties.set(concat, String.valueOf(z2));
            SystemProperties.set(
                    concat + ".logPackage",
                    versionedPackage != null ? versionedPackage.toString() : "");
            SystemProperties.set(concat + ".rollbackReason", rollbackReasonToString(i2));
            SystemProperties.set(concat + ".failedPackageName", str);
        }
    }

    public static int mapFailureReasonToMetric(int i) {
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                i2 = 3;
                if (i != 3) {
                    i2 = 4;
                    if (i != 4) {
                        return i != 5 ? 0 : 7;
                    }
                }
            }
        }
        return i2;
    }

    public static String rollbackReasonToString(int i) {
        return i != 1
                ? i != 2
                        ? i != 3
                                ? i != 4
                                        ? i != 5
                                                ? i != 7 ? "UNKNOWN" : "REASON_BOOT_LOOP"
                                                : "REASON_NATIVE_CRASH_DURING_BOOT"
                                        : "REASON_APP_NOT_RESPONDING"
                                : "REASON_APP_CRASH"
                        : "REASON_EXPLICIT_HEALTH_CHECK"
                : "REASON_NATIVE_CRASH";
    }
}
