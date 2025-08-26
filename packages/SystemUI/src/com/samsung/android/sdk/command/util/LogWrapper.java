package com.samsung.android.sdk.command.util;

import android.util.Log;

/* loaded from: classes4.dex */
public class LogWrapper {
    public static void e(String str, String str2) {
        Log.e("[CmdL-2.0.8]".concat(str), str2);
    }

    public static void i(String str) {
        Log.i("[CmdL-2.0.8]CommandProvider", str);
    }
}
