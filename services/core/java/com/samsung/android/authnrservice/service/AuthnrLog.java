package com.samsung.android.authnrservice.service;

import android.util.Log;

public abstract class AuthnrLog {
    public static void e(String str, String str2) {
        Log.e("SASvc_".concat(str), str2);
    }

    public static void w(String str, String str2) {
        Log.w("SASvc_".concat(str), str2);
    }
}
