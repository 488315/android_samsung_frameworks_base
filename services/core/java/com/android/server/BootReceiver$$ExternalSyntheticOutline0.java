package com.android.server;

import android.util.Slog;

import java.io.IOException;

public abstract /* synthetic */ class BootReceiver$$ExternalSyntheticOutline0 {
    public static String m(String str, String str2, String str3, String str4) {
        return str + str2 + str3 + str4;
    }

    public static String m(StringBuilder sb, String str, String str2, String str3) {
        sb.append(str);
        sb.append(str2);
        sb.append(str3);
        return sb.toString();
    }

    public static StringBuilder m(int i, String str) {
        StringBuilder sb = new StringBuilder(i);
        sb.append(str);
        return sb;
    }

    public static StringBuilder m(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        return sb;
    }

    public static void m(int i, String str, String str2, String str3) {
        Slog.i(str3, str + i + str2);
    }

    public static void m(Exception exc, String str, String str2) {
        Slog.e(str2, str + exc);
    }

    public static void m(String str, IOException iOException, String str2) {
        Slog.e(str2, str + iOException);
    }

    public static void m(String str, String str2, String str3) {
        Slog.e(str3, str + str2);
    }

    /* renamed from: m, reason: collision with other method in class */
    public static void m58m(String str, String str2, String str3, String str4) {
        Slog.i(str4, str + str2 + str3);
    }

    public static void m(StringBuilder sb, String str, long j, String str2) {
        sb.append(str);
        sb.append(j);
        sb.append(str2);
    }

    public static void m(StringBuilder sb, String str, String str2) {
        sb.append(str);
        Slog.d(str2, sb.toString());
    }

    /* renamed from: m, reason: collision with other method in class */
    public static void m59m(StringBuilder sb, String str, String str2, String str3) {
        sb.append(str);
        sb.append(str2);
        Slog.i(str3, sb.toString());
    }
}
