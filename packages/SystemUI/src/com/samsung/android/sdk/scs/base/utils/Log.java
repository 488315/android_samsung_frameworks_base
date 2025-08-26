package com.samsung.android.sdk.scs.base.utils;

/* loaded from: classes4.dex */
public class Log {
    public static String concatPrefixTag(String str) {
        return "ScsApi@".concat(str != null ? str.replace("ScsApi@", "") : "");
    }

    public static void d(String str, String str2) {
        android.util.Log.d(concatPrefixTag(str), str2);
    }

    public static void e(String str, String str2) {
        android.util.Log.e(concatPrefixTag(str), str2);
    }

    public static void i(String str, String str2) {
        android.util.Log.i(concatPrefixTag(str), str2);
    }
}
