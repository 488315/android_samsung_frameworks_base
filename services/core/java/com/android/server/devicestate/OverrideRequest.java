package com.android.server.devicestate;

import android.hardware.devicestate.DeviceState;
import android.os.IBinder;

public final class OverrideRequest {
    public final int mFlags;
    public final int mPid;
    public final int mRequestType;
    public final DeviceState mRequestedState;
    public final IBinder mToken;
    public final int mUid;

    public OverrideRequest(
            IBinder iBinder, int i, int i2, DeviceState deviceState, int i3, int i4) {
        this.mToken = iBinder;
        this.mPid = i;
        this.mUid = i2;
        this.mRequestedState = deviceState;
        this.mFlags = i3;
        this.mRequestType = i4;
    }
}
