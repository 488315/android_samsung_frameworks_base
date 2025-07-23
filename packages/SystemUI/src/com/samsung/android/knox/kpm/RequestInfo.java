package com.samsung.android.knox.kpm;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
class RequestInfo {
    public static final int CMD_IS_REGISTERED = 3;
    public static final int CMD_REGISTER = 1;
    public static final int CMD_UNREGISTER = 2;
    public int mCmd;
    public boolean mForce;

    public RequestInfo(int i) {
        this.mCmd = i;
        this.mForce = false;
    }

    public int getCmd() {
        return this.mCmd;
    }

    public boolean isForce() {
        return this.mForce;
    }

    public RequestInfo(int i, boolean z) {
        this.mCmd = i;
        this.mForce = z;
    }
}
