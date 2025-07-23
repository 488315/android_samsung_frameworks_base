package com.android.systemui.statusbar.notification.row;

import com.android.systemui.log.LogBuffer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationRowLogger {
    public final LogBuffer buffer;
    public final LogBuffer notificationRenderBuffer;

    public NotificationRowLogger(LogBuffer logBuffer, LogBuffer logBuffer2) {
        this.buffer = logBuffer;
        this.notificationRenderBuffer = logBuffer2;
    }
}
