package com.android.server.location.injector;

import android.os.Binder;

import com.android.internal.util.Preconditions;
import com.android.server.DeviceIdleInternal;

public final class SystemDeviceStationaryHelper {
    public DeviceIdleInternal mDeviceIdle;

    public final void addListener(DeviceIdleInternal.StationaryListener stationaryListener) {
        Preconditions.checkState(this.mDeviceIdle != null);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mDeviceIdle.registerStationaryListener(stationaryListener);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
