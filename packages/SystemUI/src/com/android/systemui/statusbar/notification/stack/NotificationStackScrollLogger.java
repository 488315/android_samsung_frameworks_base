package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.log.LogBuffer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
