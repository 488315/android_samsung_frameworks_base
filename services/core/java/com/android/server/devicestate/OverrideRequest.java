package com.android.server.devicestate;

import android.hardware.devicestate.DeviceState;
import android.os.IBinder;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
