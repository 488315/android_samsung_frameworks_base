package com.android.server.biometrics.sensors;

import android.util.SparseIntArray;

import com.android.server.asks.ASKSManagerService$$ExternalSyntheticOutline0;

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
        ASKSManagerService$$ExternalSyntheticOutline0.m(
                i, i2, "Lockout for user: ", " is ", "LockoutCache");
        synchronized (this) {
            this.mUserLockoutStates.put(i, i2);
        }
    }
}
