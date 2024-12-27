package com.samsung.android.sdk.scs.base.utils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class Log {
    public static String concatPrefixTag(String str) {
        return "ScsApi@".concat(str.replace("ScsApi@", ""));
    }

    public static void d(String str, String str2) {
        android.util.Log.d(concatPrefixTag(str), str2);
    }

    public static void e(String str, String str2) {
        android.util.Log.e(concatPrefixTag(str), str2);
    }
}
