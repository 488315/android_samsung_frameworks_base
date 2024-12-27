package com.android.systemui.statusbar.gesture;

import com.android.systemui.log.LogBuffer;

public final class SwipeUpGestureLogger {
    public final LogBuffer buffer;

    public SwipeUpGestureLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }
}
