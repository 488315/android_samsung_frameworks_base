package com.android.systemui.util.wakelock;

import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SettableWakeLock {
    public boolean mAcquired;
    public final WakeLock mInner;
    public final String mWhy;

    public SettableWakeLock(WakeLock wakeLock, String str) {
        Objects.requireNonNull(wakeLock, "inner wakelock required");
        this.mInner = wakeLock;
        this.mWhy = str;
    }

    public final synchronized void setAcquired(boolean z) {
        if (this.mAcquired != z) {
            if (z) {
                this.mInner.acquire(this.mWhy);
            } else {
                this.mInner.release(this.mWhy);
            }
            this.mAcquired = z;
        }
    }
}
