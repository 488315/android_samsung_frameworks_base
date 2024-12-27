package com.samsung.android.biometrics.app.setting;

import android.content.Context;
import android.os.Build;
import android.util.Log;

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
