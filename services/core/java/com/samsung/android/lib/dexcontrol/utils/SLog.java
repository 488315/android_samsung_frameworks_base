package com.samsung.android.lib.dexcontrol.utils;

import android.os.Build;
import android.util.Log;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
