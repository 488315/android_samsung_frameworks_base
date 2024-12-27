package com.android.systemui.statusbar.notification;

import com.android.systemui.log.LogBuffer;

public final class NotificationClickerLogger {
    public final LogBuffer buffer;

    public NotificationClickerLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }
}
