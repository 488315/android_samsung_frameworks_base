package com.android.server;

import android.util.Slog;
import android.util.TimeUtils;

import java.io.IOException;
import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract /* synthetic */ class DeviceIdleController$$ExternalSyntheticOutline0 {
    public static String m(long j, String str) {
        return str + j;
    }

    public static void m(int i, String str, String str2) {
        Slog.w(str2, str + i);
    }

    public static void m(int i, String str, String str2, String str3) {
        Slog.d(str3, str + i + str2);
    }

    public static void m(long j, PrintWriter printWriter, String str, String str2) {
        TimeUtils.formatDuration(j, printWriter);
        printWriter.println();
        printWriter.print(str);
        printWriter.print(str2);
    }

    public static void m(PrintWriter printWriter, boolean z, String str, String str2, String str3) {
        printWriter.println(z);
        printWriter.print(str);
        printWriter.print(str2);
        printWriter.print(str3);
    }

    public static void m(String str, IOException iOException, String str2) {
        Slog.w(str2, str + iOException);
    }

    public static void m(String str, String str2, String str3, String str4) {
        Slog.d(str4, str + str2 + str3);
    }

    public static void m(String str, String str2, boolean z) {
        Slog.d(str2, str + z);
    }

    public static void m(StringBuilder sb, int i, String str) {
        sb.append(i);
        Slog.d(str, sb.toString());
    }

    public static void m(StringBuilder sb, String str, String str2) {
        sb.append(str);
        Slog.i(str2, sb.toString());
    }

    public static void m(StringBuilder sb, String str, String str2, String str3) {
        sb.append(str);
        sb.append(str2);
        Slog.d(str3, sb.toString());
    }
}
