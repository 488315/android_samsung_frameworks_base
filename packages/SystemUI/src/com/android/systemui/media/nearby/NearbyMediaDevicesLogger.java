package com.android.systemui.media.nearby;

import com.android.systemui.log.LogBuffer;

public final class NearbyMediaDevicesLogger {
    public final LogBuffer buffer;

    public NearbyMediaDevicesLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }
}
