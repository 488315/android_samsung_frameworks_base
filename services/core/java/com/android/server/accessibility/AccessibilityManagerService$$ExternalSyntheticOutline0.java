package com.android.server.accessibility;

import android.util.Log;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract /* synthetic */ class AccessibilityManagerService$$ExternalSyntheticOutline0 {
    public static String m(int i, String str, String str2, String str3) {
        return str + i + str2 + str3;
    }

    public static StringBuilder m(int i, String str, String str2, String str3, boolean z) {
        StringBuilder sb = new StringBuilder(str);
        sb.append(z);
        sb.append(str2);
        sb.append(i);
        sb.append(str3);
        return sb;
    }

    public static void m(int i, int i2, String str, String str2, String str3) {
        Log.d(str3, str + i + str2 + i2);
    }

    public static void m(int i, String str, String str2, String str3, StringBuilder sb) {
        sb.append(str);
        sb.append(str2);
        sb.append(i);
        sb.append(str3);
    }

    public static void m(String str, String str2, boolean z) {
        Log.d(str2, str + z);
    }

    public static void m(StringBuilder sb, int i, PrintWriter printWriter) {
        sb.append(i);
        printWriter.println(sb.toString());
    }
}
