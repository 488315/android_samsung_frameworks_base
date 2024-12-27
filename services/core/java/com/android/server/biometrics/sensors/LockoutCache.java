package com.android.server.biometrics.sensors;

import android.util.SparseIntArray;
import com.android.server.asks.ASKSManagerService$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class LockoutCache implements LockoutTracker {
    public final SparseIntArray mUserLockoutStates = new SparseIntArray();

    @Override // com.android.server.biometrics.sensors.LockoutTracker
    public final int getLockoutModeForUser(int i) {
        int i2;
        synchronized (this) {
            i2 = this.mUserLockoutStates.get(i, 0);
        }
        return i2;
    }

    @Override // com.android.server.biometrics.sensors.LockoutTracker
    public final void setLockoutModeForUser(int i, int i2) {
        ASKSManagerService$$ExternalSyntheticOutline0.m(i, i2, "Lockout for user: ", " is ", "LockoutCache");
        synchronized (this) {
            this.mUserLockoutStates.put(i, i2);
        }
    }
}
