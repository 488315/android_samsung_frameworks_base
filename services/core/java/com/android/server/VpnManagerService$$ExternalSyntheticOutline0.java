package com.android.server;

import android.util.Log;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
