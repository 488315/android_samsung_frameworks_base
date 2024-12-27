package com.android.systemui.statusbar.notification.row;

import com.android.systemui.log.LogBuffer;

public final class NotificationRowLogger {
    public final LogBuffer buffer;
    public final LogBuffer notificationRenderBuffer;

    public NotificationRowLogger(LogBuffer logBuffer, LogBuffer logBuffer2) {
        this.buffer = logBuffer;
        this.notificationRenderBuffer = logBuffer2;
    }
}
