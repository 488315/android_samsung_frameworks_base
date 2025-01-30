package com.samsung.android.game;

import android.util.Slog;

/* loaded from: classes5.dex */
public class GmsLog {
    private static final String LOG_TAG_PREFIX = "SGM:";

    /* renamed from: v */
    public static void m260v(String tag, String msg) {
        Slog.m119v(replaceForbiddenString(LOG_TAG_PREFIX + tag), replaceForbiddenString(msg));
    }

    /* renamed from: d */
    public static void m256d(String tag, String msg) {
        Slog.m113d(replaceForbiddenString(LOG_TAG_PREFIX + tag), replaceForbiddenString(msg));
    }

    /* renamed from: i */
    public static void m259i(String tag, String msg) {
        Slog.m117i(replaceForbiddenString(LOG_TAG_PREFIX + tag), replaceForbiddenString(msg));
    }

    /* renamed from: w */
    public static void m261w(String tag, String msg) {
        Slog.m121w(replaceForbiddenString(LOG_TAG_PREFIX + tag), replaceForbiddenString(msg));
    }

    /* renamed from: w */
    public static void m262w(String tag, String msg, Throwable tr) {
        Slog.m122w(replaceForbiddenString(LOG_TAG_PREFIX + tag), replaceForbiddenString(msg), tr);
    }

    /* renamed from: w */
    public static void m263w(String tag, Throwable tr) {
        Slog.m123w(replaceForbiddenString(LOG_TAG_PREFIX + tag), tr);
    }

    /* renamed from: e */
    public static void m257e(String tag, String msg) {
        Slog.m115e(replaceForbiddenString(LOG_TAG_PREFIX + tag), replaceForbiddenString(msg));
    }

    /* renamed from: e */
    public static void m258e(String tag, String msg, Throwable tr) {
        Slog.m116e(replaceForbiddenString(LOG_TAG_PREFIX + tag), replaceForbiddenString(msg), tr);
    }

    static String replaceForbiddenString(String str) {
        if (str == null) {
            return null;
        }
        return str.replace("com.att.iqi", "PKG_01");
    }
}
