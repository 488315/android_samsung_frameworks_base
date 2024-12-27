package com.android.systemui.biometrics;

import com.android.systemui.log.LogBuffer;

public final class UdfpsLogger {
    public final LogBuffer logBuffer;

    public UdfpsLogger(LogBuffer logBuffer) {
        this.logBuffer = logBuffer;
    }
}
