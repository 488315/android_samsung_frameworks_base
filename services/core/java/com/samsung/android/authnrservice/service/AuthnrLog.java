package com.samsung.android.authnrservice.service;

import android.util.Log;

/* loaded from: classes2.dex */
public abstract class AuthnrLog {
    /* renamed from: d */
    public static void m120d(String str, String str2) {
    }

    /* renamed from: v */
    public static void m123v(String str, String str2) {
    }

    /* renamed from: i */
    public static void m122i(String str, String str2) {
        Log.i("SASvc_" + str, str2);
    }

    /* renamed from: w */
    public static void m124w(String str, String str2) {
        Log.w("SASvc_" + str, str2);
    }

    /* renamed from: e */
    public static void m121e(String str, String str2) {
        Log.e("SASvc_" + str, str2);
    }
}
