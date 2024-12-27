package com.android.server.biometrics.sensors.face;

import com.android.server.biometrics.sensors.LockoutTracker;

public final class LockoutHalImpl implements LockoutTracker {
    public int mCurrentUserLockoutMode;

    @Override // com.android.server.biometrics.sensors.LockoutTracker
    public final int getLockoutModeForUser(int i) {
        return this.mCurrentUserLockoutMode;
    }

    @Override // com.android.server.biometrics.sensors.LockoutTracker
    public final void setLockoutModeForUser(int i, int i2) {
        this.mCurrentUserLockoutMode = i2;
    }
}
