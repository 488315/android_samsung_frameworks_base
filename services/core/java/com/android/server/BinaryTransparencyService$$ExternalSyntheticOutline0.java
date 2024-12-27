package com.android.server;

import android.util.Slog;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract /* synthetic */ class BinaryTransparencyService$$ExternalSyntheticOutline0 {
    public static String m(int i, String str, String str2) {
        return str + i + str2;
    }

    public static StringBuilder m(PrintWriter printWriter, String str, String str2) {
        printWriter.print(str);
        return new StringBuilder(str2);
    }

    public static StringBuilder m(
            PrintWriter printWriter, String str, String str2, StringBuilder sb) {
        sb.append(str);
        printWriter.println(sb.toString());
        return new StringBuilder(str2);
    }

    public static StringBuilder m(StringBuilder sb, int i, PrintWriter printWriter, String str) {
        sb.append(i);
        printWriter.println(sb.toString());
        return new StringBuilder(str);
    }

    public static StringBuilder m(StringBuilder sb, long j, PrintWriter printWriter, String str) {
        sb.append(j);
        printWriter.println(sb.toString());
        return new StringBuilder(str);
    }

    public static StringBuilder m(
            StringBuilder sb, boolean z, PrintWriter printWriter, String str) {
        sb.append(z);
        printWriter.println(sb.toString());
        return new StringBuilder(str);
    }

    /* renamed from: m, reason: collision with other method in class */
    public static void m50m(PrintWriter printWriter, String str, String str2) {
        printWriter.println(str + str2);
    }

    public static void m(PrintWriter printWriter, String str, String str2, String str3) {
        printWriter.println(str + str2 + str3);
    }

    public static void m(String str, String str2, String str3) {
        Slog.d(str3, str + str2);
    }

    public static void m(StringBuilder sb, int i, String str, String str2) {
        sb.append(i);
        sb.append(str);
        Slog.d(str2, sb.toString());
    }

    public static void m(StringBuilder sb, String str, PrintWriter printWriter) {
        sb.append(str);
        printWriter.println(sb.toString());
    }

    public static void m(StringBuilder sb, String str, String str2) {
        sb.append(str);
        Slog.w(str2, sb.toString());
    }

    public static void m(StringBuilder sb, boolean z, PrintWriter printWriter) {
        sb.append(z);
        printWriter.println(sb.toString());
    }

    public static StringBuilder m$1(PrintWriter printWriter, String str, String str2) {
        printWriter.println(str);
        return new StringBuilder(str2);
    }

    public static void m$1(StringBuilder sb, String str, String str2) {
        sb.append(str);
        Slog.e(str2, sb.toString());
    }
}
