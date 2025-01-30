package com.samsung.android.sdk.scs.base.utils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class Log {
    public static String concatPrefixTag(String str) {
        return "ScsApi@".concat(str.replace("ScsApi@", ""));
    }

    /* renamed from: d */
    public static void m266d(String str, String str2) {
        android.util.Log.d(concatPrefixTag(str), str2);
    }

    /* renamed from: e */
    public static void m267e(String str, String str2) {
        android.util.Log.e(concatPrefixTag(str), str2);
    }

    /* renamed from: i */
    public static void m268i(String str, String str2) {
        android.util.Log.i(concatPrefixTag(str), str2);
    }
}
