package com.android.systemui.statusbar.notification.interruption;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;

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
        LogMessage logMessageObtain = logBuffer.obtain("InterruptionStateProvider", logLevel, notificationInterruptLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logMessageImpl.str2 = str;
        logMessageImpl.long1 = j;
        logMessageImpl.long2 = j2;
        logBuffer.commit(logMessageObtain);
    }

    public final void logNoAlertingSuppressedBy(NotificationEntry notificationEntry, NotificationInterruptSuppressor notificationInterruptSuppressor, boolean z) {
        LogLevel logLevel = LogLevel.DEBUG;
        NotificationInterruptLogger$$ExternalSyntheticLambda0 notificationInterruptLogger$$ExternalSyntheticLambda0 = new NotificationInterruptLogger$$ExternalSyntheticLambda0(21);
        LogBuffer logBuffer = this.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("InterruptionStateProvider", logLevel, notificationInterruptLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        notificationInterruptSuppressor.getClass();
        logMessageImpl.str2 = "StatusBarNotificationPresenter";
        logMessageImpl.bool1 = z;
        logBuffer.commit(logMessageObtain);
    }

    public final void logNoFullscreenWarning(NotificationEntry notificationEntry, String str) {
        LogLevel logLevel = LogLevel.WARNING;
        NotificationInterruptLogger$$ExternalSyntheticLambda0 notificationInterruptLogger$$ExternalSyntheticLambda0 = new NotificationInterruptLogger$$ExternalSyntheticLambda0(9);
        LogBuffer logBuffer = this.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("InterruptionStateProvider", logLevel, notificationInterruptLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        ((LogMessageImpl) logMessageObtain).str2 = str;
        logBuffer.commit(logMessageObtain);
    }
}
