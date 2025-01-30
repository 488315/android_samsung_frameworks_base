package com.android.systemui.statusbar.notification;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessage;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract /* synthetic */ class NotificationClicker$$ExternalSyntheticOutline0 {
    /* renamed from: m */
    public static void m202m(NotificationEntry notificationEntry, LogMessage logMessage, LogBuffer logBuffer, LogMessage logMessage2) {
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logBuffer.commit(logMessage2);
    }
}
