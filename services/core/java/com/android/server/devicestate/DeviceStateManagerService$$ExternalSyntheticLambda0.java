package com.android.server.devicestate;

import android.os.SystemProperties;

public final /* synthetic */ class DeviceStateManagerService$$ExternalSyntheticLambda0
        implements DeviceStateManagerService.SystemPropertySetter {
    @Override // com.android.server.devicestate.DeviceStateManagerService.SystemPropertySetter
    public final void setDebugTracingDeviceStateProperty(String str) {
        SystemProperties.set("debug.tracing.device_state", str);
    }
}
