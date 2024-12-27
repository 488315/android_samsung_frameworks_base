package com.android.systemui.statusbar.notification.row;

import com.android.systemui.log.LogBuffer;

public final class RowInflaterTaskLogger {
    public final LogBuffer buffer;

    public RowInflaterTaskLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }
}
