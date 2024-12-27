package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.log.LogBuffer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
