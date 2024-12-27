package com.samsung.android.authnrservice.service;

import android.util.Log;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class AuthnrLog {
    public static void e(String str, String str2) {
        Log.e("SASvc_".concat(str), str2);
    }

    public static void w(String str, String str2) {
        Log.w("SASvc_".concat(str), str2);
    }
}
