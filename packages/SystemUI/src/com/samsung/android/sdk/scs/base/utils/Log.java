package com.samsung.android.sdk.scs.base.utils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
