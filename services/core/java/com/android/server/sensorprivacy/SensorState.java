package com.android.server.sensorprivacy;

import android.os.SystemClock;

public final class SensorState {
    public long mLastChange;
    public int mStateType;

    public SensorState(int i) {
        this.mStateType = i;
        String str = SensorPrivacyService.ACTION_DISABLE_TOGGLE_SENSOR_PRIVACY;
        this.mLastChange = SystemClock.elapsedRealtime();
    }

    public SensorState(SensorState sensorState) {
        this.mStateType = sensorState.mStateType;
        this.mLastChange = sensorState.mLastChange;
    }

    public SensorState(boolean z) {
        this(z ? 1 : 2);
    }

    public final boolean isEnabled() {
        return this.mStateType == 1;
    }
}
