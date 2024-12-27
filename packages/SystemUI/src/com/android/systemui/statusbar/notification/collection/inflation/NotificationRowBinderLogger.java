package com.android.systemui.statusbar.notification.collection.inflation;

import com.android.systemui.log.LogBuffer;

public final class NotificationRowBinderLogger {
    public final LogBuffer buffer;

    public NotificationRowBinderLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }
}
