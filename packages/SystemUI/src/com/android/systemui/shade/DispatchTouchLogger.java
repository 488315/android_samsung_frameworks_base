package com.android.systemui.shade;

import com.android.systemui.log.LogBuffer;

public final class DispatchTouchLogger {
    public final LogBuffer buffer;

    public DispatchTouchLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }
}
