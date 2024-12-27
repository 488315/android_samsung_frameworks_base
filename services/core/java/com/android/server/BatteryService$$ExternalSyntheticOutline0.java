package com.android.server;

import android.content.Intent;
import android.content.IntentFilter;
import com.android.server.power.Slog;
import java.io.File;
import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract /* synthetic */ class BatteryService$$ExternalSyntheticOutline0 {
    public static Intent m(int i, String str) {
        Intent intent = new Intent(str);
        intent.addFlags(i);
        return intent;
    }

    public static IntentFilter m(String str) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(str);
        return intentFilter;
    }

    public static StringBuilder m(int i, String str, String str2) {
        StringBuilder sb = new StringBuilder(str);
        sb.append(i);
        sb.append(str2);
        return sb;
    }

    public static StringBuilder m(String str, long j, String str2) {
        StringBuilder sb = new StringBuilder(str);
        sb.append(j);
        sb.append(str2);
        return sb;
    }

    public static StringBuilder m(String str, String str2, boolean z) {
        StringBuilder sb = new StringBuilder(str);
        sb.append(z);
        sb.append(str2);
        return sb;
    }

    public static void m(int i, StringBuilder sb, String str) {
        sb.append(Integer.toHexString(i));
        sb.append(str);
    }

    public static void m(PrintWriter printWriter, String str, String str2, String str3) {
        printWriter.println(str);
        printWriter.println(str2);
        printWriter.println(str3);
    }

    public static void m(PrintWriter printWriter, String str, String str2, String str3, String str4) {
        printWriter.println(str);
        printWriter.println(str2);
        printWriter.println(str3);
        printWriter.println(str4);
    }

    public static void m(StringBuilder sb, int i, String str) {
        sb.append(i);
        Slog.d(str, sb.toString());
    }

    public static void m(StringBuilder sb, long j, String str) {
        sb.append(j);
        android.util.Slog.d(str, sb.toString());
    }

    public static void m(StringBuilder sb, boolean z, String str, boolean z2, String str2) {
        sb.append(z);
        sb.append(str);
        sb.append(z2);
        sb.append(str2);
    }

    /* renamed from: m, reason: collision with other method in class */
    public static boolean m45m(String str) {
        return new File(str).exists();
    }
}
