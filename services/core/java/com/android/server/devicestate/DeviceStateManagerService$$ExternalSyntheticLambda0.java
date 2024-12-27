package com.android.server.devicestate;

import android.os.SystemProperties;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class DeviceStateManagerService$$ExternalSyntheticLambda0
        implements DeviceStateManagerService.SystemPropertySetter {
    @Override // com.android.server.devicestate.DeviceStateManagerService.SystemPropertySetter
    public final void setDebugTracingDeviceStateProperty(String str) {
        SystemProperties.set("debug.tracing.device_state", str);
    }
}
