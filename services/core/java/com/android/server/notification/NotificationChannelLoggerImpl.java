package com.android.server.notification;

import android.app.NotificationChannel;

import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.FrameworkStatsLog;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class NotificationChannelLoggerImpl implements NotificationChannelLogger {
    public UiEventLogger mUiEventLogger;

    public final void logNotificationChannel(
            NotificationChannelLogger.NotificationChannelEvent notificationChannelEvent,
            NotificationChannel notificationChannel,
            int i,
            String str,
            int i2,
            int i3) {
        FrameworkStatsLog.write(
                FrameworkStatsLog.NOTIFICATION_CHANNEL_MODIFIED,
                notificationChannelEvent.getId(),
                i,
                str,
                Math.floorMod(Objects.hashCode(notificationChannel.getId()), 8192),
                i2,
                i3,
                notificationChannel.isConversation(),
                Math.floorMod(Objects.hashCode(notificationChannel.getConversationId()), 8192),
                notificationChannel.isDemoted(),
                notificationChannel.isImportantConversation());
    }
}
