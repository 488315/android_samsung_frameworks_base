package com.samsung.android.biometrics.app.setting;

import android.content.Context;
import android.os.Build;
import android.util.Log;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
public abstract class SALoggingHelper {
    public static String getUiVersion(Context context) {
        int i;
        boolean z =
                context != null
                        && (context.getPackageManager()
                                        .hasSystemFeature(
                                                "com.samsung.feature.samsung_experience_mobile")
                                || context.getPackageManager()
                                        .hasSystemFeature(
                                                "com.samsung.feature.samsung_experience_mobile_lite"));
        Log.secD("BSS_SALoggingHelper", "isSemAvailable: " + z);
        if (!z || (i = Build.VERSION.SEM_PLATFORM_INT) < 80100) {
            return "";
        }
        return String.valueOf(i / 10000) + "." + String.valueOf((i % 10000) / 100);
    }

    public static void insertEventLogging(int i, int i2) {
        insertSALog(-9999L, String.valueOf(8234), String.valueOf(i), String.valueOf(i2));
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x00ba A[Catch: AnalyticsException -> 0x0135, TryCatch #0 {AnalyticsException -> 0x0135, blocks: (B:18:0x0064, B:20:0x006f, B:21:0x0076, B:23:0x007c, B:24:0x0081, B:26:0x0088, B:29:0x008f, B:30:0x0098, B:32:0x009c, B:34:0x00a7, B:35:0x00ae, B:37:0x00ba, B:38:0x00bf, B:39:0x00d8, B:44:0x00ac, B:45:0x0073, B:46:0x00de, B:48:0x00e9, B:49:0x00f0, B:51:0x00f4, B:53:0x00ff, B:54:0x0106, B:56:0x0112, B:57:0x011f, B:58:0x012f, B:59:0x0118, B:60:0x0104, B:61:0x00ed), top: B:16:0x0062 }] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0112 A[Catch: AnalyticsException -> 0x0135, TryCatch #0 {AnalyticsException -> 0x0135, blocks: (B:18:0x0064, B:20:0x006f, B:21:0x0076, B:23:0x007c, B:24:0x0081, B:26:0x0088, B:29:0x008f, B:30:0x0098, B:32:0x009c, B:34:0x00a7, B:35:0x00ae, B:37:0x00ba, B:38:0x00bf, B:39:0x00d8, B:44:0x00ac, B:45:0x0073, B:46:0x00de, B:48:0x00e9, B:49:0x00f0, B:51:0x00f4, B:53:0x00ff, B:54:0x0106, B:56:0x0112, B:57:0x011f, B:58:0x012f, B:59:0x0118, B:60:0x0104, B:61:0x00ed), top: B:16:0x0062 }] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0118 A[Catch: AnalyticsException -> 0x0135, TryCatch #0 {AnalyticsException -> 0x0135, blocks: (B:18:0x0064, B:20:0x006f, B:21:0x0076, B:23:0x007c, B:24:0x0081, B:26:0x0088, B:29:0x008f, B:30:0x0098, B:32:0x009c, B:34:0x00a7, B:35:0x00ae, B:37:0x00ba, B:38:0x00bf, B:39:0x00d8, B:44:0x00ac, B:45:0x0073, B:46:0x00de, B:48:0x00e9, B:49:0x00f0, B:51:0x00f4, B:53:0x00ff, B:54:0x0106, B:56:0x0112, B:57:0x011f, B:58:0x012f, B:59:0x0118, B:60:0x0104, B:61:0x00ed), top: B:16:0x0062 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void insertSALog(
            long r11, java.lang.String r13, java.lang.String r14, java.lang.String r15) {
        /*
            Method dump skipped, instructions count: 315
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.biometrics.app.setting.SALoggingHelper.insertSALog(long,"
                    + " java.lang.String, java.lang.String, java.lang.String):void");
    }

    public static void insertEventLogging(int i, long j) {
        insertSALog(j, String.valueOf(8255), String.valueOf(8256), String.valueOf(i));
    }
}
