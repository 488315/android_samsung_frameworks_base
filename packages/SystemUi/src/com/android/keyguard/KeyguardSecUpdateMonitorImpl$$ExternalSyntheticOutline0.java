package com.android.keyguard;

import android.content.Context;
import android.os.Debug;
import android.util.Log;
import java.io.PrintWriter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract /* synthetic */ class KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0 {
    /* renamed from: m */
    public static String m73m(Context context, int i, StringBuilder sb) {
        sb.append(context.getString(i));
        return sb.toString();
    }

    /* renamed from: m */
    public static String m74m(StringBuilder sb, boolean z, String str, boolean z2) {
        sb.append(z);
        sb.append(str);
        sb.append(z2);
        return sb.toString();
    }

    /* renamed from: m */
    public static StringBuilder m75m(PrintWriter printWriter, String str, String str2) {
        printWriter.println(str);
        return new StringBuilder(str2);
    }

    /* renamed from: m */
    public static StringBuilder m76m(String str, int i, String str2, boolean z, String str3) {
        StringBuilder sb = new StringBuilder(str);
        sb.append(i);
        sb.append(str2);
        sb.append(z);
        sb.append(str3);
        return sb;
    }

    /* renamed from: m */
    public static StringBuilder m77m(StringBuilder sb, int i, PrintWriter printWriter, String str) {
        sb.append(i);
        printWriter.println(sb.toString());
        return new StringBuilder(str);
    }

    /* renamed from: m */
    public static void m78m(int i, StringBuilder sb, String str) {
        sb.append(Debug.getCallers(i));
        Log.d(str, sb.toString());
    }

    /* renamed from: m */
    public static void m79m(StringBuilder sb, int i, String str, int i2, String str2) {
        sb.append(i);
        sb.append(str);
        sb.append(i2);
        Log.d(str2, sb.toString());
    }
}
