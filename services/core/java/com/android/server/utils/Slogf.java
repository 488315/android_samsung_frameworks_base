package com.android.server.utils;

import android.util.Slog;
import android.util.TimingsTraceLog;

import java.util.Formatter;
import java.util.Locale;

public final class Slogf {
    private static final Formatter sFormatter;
    private static final StringBuilder sMessageBuilder;

    static {
        TimingsTraceLog timingsTraceLog = new TimingsTraceLog("SLog", 524288L);
        timingsTraceLog.traceBegin("static_init");
        StringBuilder sb = new StringBuilder();
        sMessageBuilder = sb;
        sFormatter = new Formatter(sb, Locale.ENGLISH);
        timingsTraceLog.traceEnd();
    }

    private Slogf() {
        throw new UnsupportedOperationException("provides only static methods");
    }

    public static int d(String str, String str2) {
        return Slog.d(str, str2);
    }

    public static int d(String str, String str2, Throwable th) {
        return Slog.d(str, str2, th);
    }

    public static void d(String str, String str2, Object... objArr) {
        d(str, getMessage(str2, objArr));
    }

    public static void d(String str, Throwable th, String str2, Object... objArr) {
        d(str, getMessage(str2, objArr), th);
    }

    public static int e(String str, String str2) {
        return Slog.e(str, str2);
    }

    public static int e(String str, String str2, Throwable th) {
        return Slog.e(str, str2, th);
    }

    public static void e(String str, String str2, Object... objArr) {
        e(str, getMessage(str2, objArr));
    }

    public static void e(String str, Throwable th, String str2, Object... objArr) {
        e(str, getMessage(str2, objArr), th);
    }

    private static String getMessage(String str, Object... objArr) {
        String sb;
        StringBuilder sb2 = sMessageBuilder;
        synchronized (sb2) {
            sFormatter.format(str, objArr);
            sb = sb2.toString();
            sb2.setLength(0);
        }
        return sb;
    }

    public static int i(String str, String str2) {
        return Slog.i(str, str2);
    }

    public static int i(String str, String str2, Throwable th) {
        return Slog.i(str, str2, th);
    }

    public static void i(String str, String str2, Object... objArr) {
        i(str, getMessage(str2, objArr));
    }

    public static void i(String str, Throwable th, String str2, Object... objArr) {
        i(str, getMessage(str2, objArr), th);
    }

    public static int println(int i, String str, String str2) {
        return Slog.println(i, str, str2);
    }

    public static int v(String str, String str2) {
        return Slog.v(str, str2);
    }

    public static int v(String str, String str2, Throwable th) {
        return Slog.v(str, str2, th);
    }

    public static void v(String str, String str2, Object... objArr) {
        v(str, getMessage(str2, objArr));
    }

    public static void v(String str, Throwable th, String str2, Object... objArr) {
        v(str, getMessage(str2, objArr), th);
    }

    public static int w(String str, String str2) {
        return Slog.w(str, str2);
    }

    public static int w(String str, String str2, Throwable th) {
        return Slog.w(str, str2, th);
    }

    public static int w(String str, Throwable th) {
        return Slog.w(str, th);
    }

    public static void w(String str, String str2, Object... objArr) {
        w(str, getMessage(str2, objArr));
    }

    public static void w(String str, Throwable th, String str2, Object... objArr) {
        w(str, getMessage(str2, objArr), th);
    }

    public static int wtf(String str, String str2) {
        return Slog.wtf(str, str2);
    }

    public static int wtf(String str, String str2, Throwable th) {
        return Slog.wtf(str, str2, th);
    }

    public static int wtf(String str, Throwable th) {
        return Slog.wtf(str, th);
    }

    public static void wtf(String str, String str2, Object... objArr) {
        wtf(str, getMessage(str2, objArr));
    }

    public static void wtf(String str, Throwable th, String str2, Object... objArr) {
        wtf(str, getMessage(str2, objArr), th);
    }

    public static void wtfQuiet(String str, String str2) {
        Slog.wtfQuiet(str, str2);
    }

    public static int wtfStack(String str, String str2) {
        return Slog.wtfStack(str, str2);
    }
}
