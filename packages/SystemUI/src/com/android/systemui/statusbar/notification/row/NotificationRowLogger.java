package com.android.systemui.statusbar.notification.row;

import com.android.systemui.log.LogBuffer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NotificationRowLogger {
    public final LogBuffer buffer;
    public final LogBuffer notificationRenderBuffer;

    public NotificationRowLogger(LogBuffer logBuffer, LogBuffer logBuffer2) {
        this.buffer = logBuffer;
        this.notificationRenderBuffer = logBuffer2;
    }
}
