package com.sec.android.diagmonagent.common;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class NativeHelper {
    static {
        System.loadLibrary("DiagMonKey");
    }

    public static native char[] getRandomId();
}
