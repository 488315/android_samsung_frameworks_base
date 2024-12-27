package com.android.systemui.navigationbar;

import com.android.systemui.log.LogBuffer;

public final class NavbarOrientationTrackingLogger {
    public final LogBuffer buffer;

    public NavbarOrientationTrackingLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }
}
