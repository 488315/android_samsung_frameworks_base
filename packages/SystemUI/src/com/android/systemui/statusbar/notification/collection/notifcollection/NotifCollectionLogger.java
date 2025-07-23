package com.android.systemui.statusbar.notification.collection.notifcollection;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotifCollectionLogger {
    public final LogBuffer buffer;

    public NotifCollectionLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logFutureDismissalDismissing(NotifCollection.FutureDismissal futureDismissal, String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        NotifCollectionLogger$$ExternalSyntheticLambda0 notifCollectionLogger$$ExternalSyntheticLambda0 = new NotifCollectionLogger$$ExternalSyntheticLambda0(28);
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = futureDismissal.mLabel;
        logMessageImpl.str2 = str;
        logBuffer.commit(obtain);
    }

    public final void logFutureDismissalMismatchedEntry(NotifCollection.FutureDismissal futureDismissal, String str, NotificationEntry notificationEntry) {
        LogLevel logLevel = LogLevel.WARNING;
        NotifCollectionLogger$$ExternalSyntheticLambda3 notifCollectionLogger$$ExternalSyntheticLambda3 = new NotifCollectionLogger$$ExternalSyntheticLambda3(8);
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$$ExternalSyntheticLambda3, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = futureDismissal.mLabel;
        logMessageImpl.str2 = str;
        logMessageImpl.str3 = NotificationUtilsKt.getLogKey(notificationEntry);
        logBuffer.commit(obtain);
    }
}
