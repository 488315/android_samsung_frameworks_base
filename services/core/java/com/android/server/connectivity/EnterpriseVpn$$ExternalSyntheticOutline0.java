package com.android.server.connectivity;

import android.util.Log;

public abstract /* synthetic */ class EnterpriseVpn$$ExternalSyntheticOutline0 {
    public static void m(Exception exc, StringBuilder sb, String str) {
        sb.append(Log.getStackTraceString(exc));
        Log.d(str, sb.toString());
    }
}
