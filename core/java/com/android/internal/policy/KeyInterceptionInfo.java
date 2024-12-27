package com.android.internal.policy;

/* loaded from: classes5.dex */
public class KeyInterceptionInfo {
    public final int layoutParamsPrivateFlags;
    public final int layoutParamsType;
    public final int windowOwnerUid;
    public final String windowTitle;

    public KeyInterceptionInfo(int type, int flags, String title, int uid) {
        this.layoutParamsType = type;
        this.layoutParamsPrivateFlags = flags;
        this.windowTitle = title;
        this.windowOwnerUid = uid;
    }
}
