package com.samsung.android.game;

import android.os.Build;
import android.util.Slog;

/* loaded from: classes6.dex */
public class GmsLog {
    private static final boolean DEBUG_BINARY;
    private static final String LOG_TAG_PREFIX = "SGM:";

    static {
        DEBUG_BINARY = Build.TYPE.equals("userdebug") || Build.TYPE.equals("eng");
    }

    public static void v(String str, String str2) {
        if (DEBUG_BINARY) {
            Slog.v(replaceForbiddenString(LOG_TAG_PREFIX + str), replaceForbiddenString(str2));
        }
    }

    public static void d(String str, String str2) {
        Slog.d(replaceForbiddenString(LOG_TAG_PREFIX + str), replaceForbiddenString(str2));
    }

    public static void i(String str, String str2) {
        Slog.i(replaceForbiddenString(LOG_TAG_PREFIX + str), replaceForbiddenString(str2));
    }

    public static void w(String str, String str2) {
        Slog.w(replaceForbiddenString(LOG_TAG_PREFIX + str), replaceForbiddenString(str2));
    }

    public static void w(String str, String str2, Throwable th) {
        Slog.w(replaceForbiddenString(LOG_TAG_PREFIX + str), replaceForbiddenString(str2), th);
    }

    public static void w(String str, Throwable th) {
        Slog.w(replaceForbiddenString(LOG_TAG_PREFIX + str), th);
    }

    public static void e(String str, String str2) {
        Slog.e(replaceForbiddenString(LOG_TAG_PREFIX + str), replaceForbiddenString(str2));
    }

    public static void e(String str, String str2, Throwable th) {
        Slog.e(replaceForbiddenString(LOG_TAG_PREFIX + str), replaceForbiddenString(str2), th);
    }

    static String replaceForbiddenString(String str) {
        if (str == null) {
            return null;
        }
        return str.replace("com.att.iqi", "PKG_01");
    }
}
