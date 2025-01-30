package com.samsung.android.knox.kpm;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class RequestInfo {
    public static final int CMD_IS_REGISTERED = 3;
    public static final int CMD_REGISTER = 1;
    public static final int CMD_UNREGISTER = 2;
    public int mCmd;
    public boolean mForce;

    public RequestInfo(int i) {
        this.mCmd = i;
        this.mForce = false;
    }

    public final int getCmd() {
        return this.mCmd;
    }

    public final boolean isForce() {
        return this.mForce;
    }

    public RequestInfo(int i, boolean z) {
        this.mCmd = i;
        this.mForce = z;
    }
}
