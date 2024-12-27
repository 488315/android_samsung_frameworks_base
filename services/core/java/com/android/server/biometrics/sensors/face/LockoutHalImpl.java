package com.android.server.biometrics.sensors.face;

import com.android.server.biometrics.sensors.LockoutTracker;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
