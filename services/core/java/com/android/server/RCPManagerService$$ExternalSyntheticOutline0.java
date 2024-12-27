package com.android.server;

import android.util.Log;

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
