package com.android.keyguard;

import android.util.Log;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract /* synthetic */ class EmergencyButtonController$$ExternalSyntheticOutline0 {
    public static StringBuilder m(String str, String str2, String str3, boolean z, boolean z2) {
        StringBuilder sb = new StringBuilder(str);
        sb.append(z);
        sb.append(str2);
        sb.append(z2);
        sb.append(str3);
        return sb;
    }

    public static void m(String str, String str2, boolean z) {
        Log.d(str2, str + z);
    }

    public static void m(StringBuilder sb, int i, String str, String str2) {
        sb.append(i);
        sb.append(str);
        Log.d(str2, sb.toString());
    }
}
