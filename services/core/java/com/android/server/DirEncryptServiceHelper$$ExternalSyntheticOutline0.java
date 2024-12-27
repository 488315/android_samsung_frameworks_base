package com.android.server;

import android.content.IntentFilter;
import android.util.Log;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract /* synthetic */ class DirEncryptServiceHelper$$ExternalSyntheticOutline0 {
    public static IntentFilter m(String str, String str2) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(str);
        intentFilter.addAction(str2);
        return intentFilter;
    }

    public static void m(Exception exc, String str, String str2) {
        Log.e(str2, str + exc);
    }

    public static void m(StringBuilder sb, String str, String str2) {
        sb.append(str);
        Log.i(str2, sb.toString());
    }

    public static void m(StringBuilder sb, String str, String str2, String str3, String str4) {
        sb.append(str);
        sb.append(str2);
        sb.append(str3);
        sb.append(str4);
    }
}
