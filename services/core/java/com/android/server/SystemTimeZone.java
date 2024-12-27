package com.android.server;

import android.os.SystemProperties;
import android.util.LocalLog;
import android.util.Slog;

import com.android.i18n.timezone.ZoneInfoDb;

public abstract class SystemTimeZone {
    public static final LocalLog sTimeZoneDebugLog = new LocalLog(30, false);

    public static void initializeTimeZoneSettingsIfRequired() {
        String str = SystemProperties.get("persist.sys.timezone");
        if (str == null || str.isEmpty() || !ZoneInfoDb.getInstance().hasTimeZone(str)) {
            String str2 =
                    "initializeTimeZoneSettingsIfRequired():persist.sys.timezone is not valid ("
                            + str
                            + "); setting to GMT";
            Slog.w("SystemTimeZone", str2);
            setTimeZoneId(0, "GMT", str2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x004b A[Catch: all -> 0x0031, TryCatch #0 {all -> 0x0031, blocks: (B:11:0x001e, B:13:0x0028, B:17:0x003a, B:23:0x004b, B:26:0x007b, B:30:0x005a, B:32:0x0033), top: B:10:0x001e }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean setTimeZoneId(int r7, java.lang.String r8, java.lang.String r9) {
        /*
            java.lang.String r0 = "Time zone or confidence set:  (new) timeZoneId="
            boolean r1 = android.text.TextUtils.isEmpty(r8)
            r2 = 0
            if (r1 != 0) goto L7f
            if (r8 == 0) goto L7f
            boolean r1 = r8.isEmpty()
            if (r1 != 0) goto L7f
            com.android.i18n.timezone.ZoneInfoDb r1 = com.android.i18n.timezone.ZoneInfoDb.getInstance()
            boolean r1 = r1.hasTimeZone(r8)
            if (r1 == 0) goto L7f
            java.lang.Class<com.android.server.SystemTimeZone> r1 = com.android.server.SystemTimeZone.class
            monitor-enter(r1)
            java.lang.String r3 = "persist.sys.timezone"
            java.lang.String r3 = android.os.SystemProperties.get(r3)     // Catch: java.lang.Throwable -> L31
            r4 = 1
            if (r3 == 0) goto L33
            boolean r3 = r3.equals(r8)     // Catch: java.lang.Throwable -> L31
            if (r3 != 0) goto L2f
            goto L33
        L2f:
            r3 = r2
            goto L3a
        L31:
            r7 = move-exception
            goto L7d
        L33:
            java.lang.String r3 = "persist.sys.timezone"
            android.os.SystemProperties.set(r3, r8)     // Catch: java.lang.Throwable -> L31
            r3 = r4
        L3a:
            java.lang.String r5 = "persist.sys.timezone_confidence"
            int r5 = android.os.SystemProperties.getInt(r5, r2)     // Catch: java.lang.Throwable -> L31
            if (r5 < 0) goto L48
            r6 = 100
            if (r5 > r6) goto L48
            goto L49
        L48:
            r5 = r2
        L49:
            if (r5 == r7) goto L56
            java.lang.String r2 = "persist.sys.timezone_confidence"
            java.lang.String r5 = java.lang.Integer.toString(r7)     // Catch: java.lang.Throwable -> L31
            android.os.SystemProperties.set(r2, r5)     // Catch: java.lang.Throwable -> L31
            r2 = r4
        L56:
            if (r3 != 0) goto L5a
            if (r2 == 0) goto L7b
        L5a:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L31
            r2.<init>(r0)     // Catch: java.lang.Throwable -> L31
            r2.append(r8)     // Catch: java.lang.Throwable -> L31
            java.lang.String r8 = ", (new) confidence="
            r2.append(r8)     // Catch: java.lang.Throwable -> L31
            r2.append(r7)     // Catch: java.lang.Throwable -> L31
            java.lang.String r7 = ", logInfo="
            r2.append(r7)     // Catch: java.lang.Throwable -> L31
            r2.append(r9)     // Catch: java.lang.Throwable -> L31
            java.lang.String r7 = r2.toString()     // Catch: java.lang.Throwable -> L31
            android.util.LocalLog r8 = com.android.server.SystemTimeZone.sTimeZoneDebugLog     // Catch: java.lang.Throwable -> L31
            r8.log(r7)     // Catch: java.lang.Throwable -> L31
        L7b:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L31
            return r3
        L7d:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L31
            throw r7
        L7f:
            java.lang.String r0 = "setTimeZoneId: Invalid time zone ID. timeZoneId="
            java.lang.String r1 = ", confidence="
            java.lang.String r3 = ", logInfo="
            java.lang.StringBuilder r7 = com.android.server.StorageManagerService$$ExternalSyntheticOutline0.m(r7, r0, r8, r1, r3)
            r7.append(r9)
            java.lang.String r7 = r7.toString()
            android.util.LocalLog r8 = com.android.server.SystemTimeZone.sTimeZoneDebugLog
            r8.log(r7)
            return r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: com.android.server.SystemTimeZone.setTimeZoneId(int,"
                    + " java.lang.String, java.lang.String):boolean");
    }
}
