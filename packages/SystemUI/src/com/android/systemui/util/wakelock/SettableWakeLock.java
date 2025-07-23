package com.android.systemui.util.wakelock;

import java.util.Objects;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class SettableWakeLock {
    private boolean mAcquired;
    private final WakeLock mInner;
    private final String mWhy;

    public SettableWakeLock(WakeLock wakeLock, String str) {
        Objects.requireNonNull(wakeLock, "inner wakelock required");
        this.mInner = wakeLock;
        this.mWhy = str;
    }

    public synchronized boolean isAcquired() {
        return this.mAcquired;
    }

    public synchronized void setAcquired(boolean z) {
        try {
            if (this.mAcquired != z) {
                if (z) {
                    this.mInner.acquire(this.mWhy);
                } else {
                    this.mInner.release(this.mWhy);
                }
                this.mAcquired = z;
            }
        } catch (Throwable th) {
            throw th;
        }
    }
}
