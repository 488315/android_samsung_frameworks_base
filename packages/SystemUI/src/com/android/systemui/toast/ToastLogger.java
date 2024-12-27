package com.android.systemui.toast;

import com.android.systemui.log.LogBuffer;

public final class ToastLogger {
    public final LogBuffer buffer;

    public ToastLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }
}
