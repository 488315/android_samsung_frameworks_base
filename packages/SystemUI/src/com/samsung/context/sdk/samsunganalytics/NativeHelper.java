package com.samsung.context.sdk.samsunganalytics;

/* loaded from: classes4.dex */
public class NativeHelper {
    static {
        System.loadLibrary("DiagMonKey");
    }

    public static native char[] getSALTKey();
}
