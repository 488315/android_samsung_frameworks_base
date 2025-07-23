package com.android.systemui.statusbar.notification.interruption;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationInterruptLogger {
    public final LogBuffer buffer;

    public NotificationInterruptLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logMaybeHeadsUpDespiteOldWhen(NotificationEntry notificationEntry, long j, long j2, String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        NotificationInterruptLogger$$ExternalSyntheticLambda0 notificationInterruptLogger$$ExternalSyntheticLambda0 = new NotificationInterruptLogger$$ExternalSyntheticLambda0(2);
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("InterruptionStateProvider", logLevel, notificationInterruptLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logMessageImpl.str2 = str;
        logMessageImpl.long1 = j;
        logMessageImpl.long2 = j2;
        logBuffer.commit(obtain);
    }

    public final void logNoAlertingSuppressedBy(NotificationEntry notificationEntry, NotificationInterruptSuppressor notificationInterruptSuppressor, boolean z) {
        LogLevel logLevel = LogLevel.DEBUG;
        NotificationInterruptLogger$$ExternalSyntheticLambda0 notificationInterruptLogger$$ExternalSyntheticLambda0 = new NotificationInterruptLogger$$ExternalSyntheticLambda0(21);
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("InterruptionStateProvider", logLevel, notificationInterruptLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        notificationInterruptSuppressor.getClass();
        logMessageImpl.str2 = "StatusBarNotificationPresenter";
        logMessageImpl.bool1 = z;
        logBuffer.commit(obtain);
    }

    public final void logNoFullscreenWarning(NotificationEntry notificationEntry, String str) {
        LogLevel logLevel = LogLevel.WARNING;
        NotificationInterruptLogger$$ExternalSyntheticLambda0 notificationInterruptLogger$$ExternalSyntheticLambda0 = new NotificationInterruptLogger$$ExternalSyntheticLambda0(9);
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("InterruptionStateProvider", logLevel, notificationInterruptLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) obtain).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        ((LogMessageImpl) obtain).str2 = str;
        logBuffer.commit(obtain);
    }
}
