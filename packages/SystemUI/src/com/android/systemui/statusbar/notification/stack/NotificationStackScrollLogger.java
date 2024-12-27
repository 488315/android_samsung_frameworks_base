package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.log.LogBuffer;

public final class NotificationStackScrollLogger {
    public final LogBuffer buffer;
    public final LogBuffer notificationRenderBuffer;
    public final LogBuffer shadeLogBuffer;

    public NotificationStackScrollLogger(LogBuffer logBuffer, LogBuffer logBuffer2, LogBuffer logBuffer3) {
        this.buffer = logBuffer;
        this.notificationRenderBuffer = logBuffer2;
        this.shadeLogBuffer = logBuffer3;
    }
}
