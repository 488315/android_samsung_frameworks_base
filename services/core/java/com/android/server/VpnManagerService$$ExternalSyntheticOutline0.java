package com.android.server;

import android.util.Log;

public abstract /* synthetic */ class VpnManagerService$$ExternalSyntheticOutline0 {
    public static String m(int i, String str, String str2) {
        return str + str2 + i;
    }

    public static void m(Exception exc, StringBuilder sb, String str) {
        sb.append(Log.getStackTraceString(exc));
        Log.e(str, sb.toString());
    }

    public static void m(StringBuilder sb, String str, String str2) {
        sb.append(str);
        Log.d(str2, sb.toString());
    }
}
