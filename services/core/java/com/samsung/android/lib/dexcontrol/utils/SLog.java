package com.samsung.android.lib.dexcontrol.utils;

import android.os.Build;
import android.util.Log;

public abstract class SLog {
    public static void d(String str, String str2) {
        if ("eng".equals(Build.TYPE)) {
            Log.d("DexControl__" + str, str2);
        }
    }

    public static void e(String str, String str2) {
        Log.e("DexControl__" + str, str2);
    }

    public static void i(String str, String str2) {
        Log.i("DexControl__" + str, str2);
    }
}
