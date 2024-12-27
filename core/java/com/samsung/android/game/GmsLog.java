package com.samsung.android.game;

import android.util.Slog;

public class GmsLog {
    private static final String LOG_TAG_PREFIX = "SGM:";

    public static void v(String tag, String msg) {
        Slog.v(replaceForbiddenString(LOG_TAG_PREFIX + tag), replaceForbiddenString(msg));
    }

    public static void d(String tag, String msg) {
        Slog.d(replaceForbiddenString(LOG_TAG_PREFIX + tag), replaceForbiddenString(msg));
    }

    public static void i(String tag, String msg) {
        Slog.i(replaceForbiddenString(LOG_TAG_PREFIX + tag), replaceForbiddenString(msg));
    }

    public static void w(String tag, String msg) {
        Slog.w(replaceForbiddenString(LOG_TAG_PREFIX + tag), replaceForbiddenString(msg));
    }

    public static void w(String tag, String msg, Throwable tr) {
        Slog.w(replaceForbiddenString(LOG_TAG_PREFIX + tag), replaceForbiddenString(msg), tr);
    }

    public static void w(String tag, Throwable tr) {
        Slog.w(replaceForbiddenString(LOG_TAG_PREFIX + tag), tr);
    }

    public static void e(String tag, String msg) {
        Slog.e(replaceForbiddenString(LOG_TAG_PREFIX + tag), replaceForbiddenString(msg));
    }

    public static void e(String tag, String msg, Throwable tr) {
        Slog.e(replaceForbiddenString(LOG_TAG_PREFIX + tag), replaceForbiddenString(msg), tr);
    }

    static String replaceForbiddenString(String str) {
        if (str == null) {
            return null;
        }
        return str.replace("com.att.iqi", "PKG_01");
    }
}
