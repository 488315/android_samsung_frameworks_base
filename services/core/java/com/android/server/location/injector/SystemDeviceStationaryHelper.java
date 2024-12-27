package com.android.server.location.injector;

import android.os.Binder;

import com.android.internal.util.Preconditions;
import com.android.server.DeviceIdleInternal;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
