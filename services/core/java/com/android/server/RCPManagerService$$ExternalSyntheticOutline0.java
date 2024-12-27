package com.android.server;

import android.util.Log;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract /* synthetic */ class RCPManagerService$$ExternalSyntheticOutline0 {
    public static void m(Exception exc, StringBuilder sb, String str) {
        sb.append(exc.getMessage());
        Log.e(str, sb.toString());
    }

    public static void m(String str, StringBuilder sb, boolean z) {
        sb.append(z);
        Log.d(str, sb.toString());
    }

    public static void m(StringBuilder sb, String str, String str2, String str3) {
        sb.append(str);
        sb.append(str2);
        Log.d(str3, sb.toString());
    }

    public static void m$1(StringBuilder sb, String str, String str2, String str3) {
        sb.append(str);
        sb.append(str2);
        sb.append(str3);
    }
}
