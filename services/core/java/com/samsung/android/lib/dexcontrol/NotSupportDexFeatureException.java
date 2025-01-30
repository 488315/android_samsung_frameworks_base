package com.samsung.android.lib.dexcontrol;

/* loaded from: classes2.dex */
public class NotSupportDexFeatureException extends Exception {
    private int pid;

    public NotSupportDexFeatureException(int i) {
        this.pid = i;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        int i = this.pid;
        return i != 40992 ? i != 41001 ? "your dex does not support this feature" : "DeX Pad does not support this feature" : "DeX Station does not support this feature";
    }
}
