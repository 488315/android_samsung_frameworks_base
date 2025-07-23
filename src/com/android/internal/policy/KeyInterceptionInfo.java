package com.android.internal.policy;

/* loaded from: classes5.dex */
public class KeyInterceptionInfo {
    public final int layoutParamsPrivateFlags;
    public final int layoutParamsType;
    public final int windowOwnerUid;
    public final String windowTitle;

    public KeyInterceptionInfo(int i, int i2, String str, int i3) {
        this.layoutParamsType = i;
        this.layoutParamsPrivateFlags = i2;
        this.windowTitle = str;
        this.windowOwnerUid = i3;
    }
}
