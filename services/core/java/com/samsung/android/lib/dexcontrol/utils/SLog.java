package com.samsung.android.lib.dexcontrol.utils;

import android.os.Build;
import android.util.Log;

/* loaded from: classes2.dex */
public abstract class SLog {
    /* renamed from: d */
    public static void m126d(String str, String str2) {
        if ("eng".equals(Build.TYPE)) {
            Log.d("DexControl__" + str, str2);
        }
    }

    /* renamed from: e */
    public static void m127e(String str, String str2) {
        Log.e("DexControl__" + str, str2);
    }

    /* renamed from: i */
    public static void m128i(String str, String str2) {
        Log.i("DexControl__" + str, str2);
    }
}
