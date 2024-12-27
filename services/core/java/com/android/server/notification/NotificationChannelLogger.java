package com.android.server.notification;

import android.app.NotificationChannel;
import com.android.internal.logging.UiEventLogger;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public interface NotificationChannelLogger {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public enum NotificationChannelEvent implements UiEventLogger.UiEventEnum {
        NOTIFICATION_CHANNEL_CREATED("NOTIFICATION_CHANNEL_CREATED"),
        NOTIFICATION_CHANNEL_UPDATED("NOTIFICATION_CHANNEL_UPDATED"),
        NOTIFICATION_CHANNEL_UPDATED_BY_USER("NOTIFICATION_CHANNEL_UPDATED_BY_USER"),
        NOTIFICATION_CHANNEL_DELETED("NOTIFICATION_CHANNEL_DELETED"),
        NOTIFICATION_CHANNEL_GROUP_CREATED("NOTIFICATION_CHANNEL_GROUP_CREATED"),
        /* JADX INFO: Fake field, exist only in values array */
        EF5("NOTIFICATION_CHANNEL_GROUP_UPDATED"),
        NOTIFICATION_CHANNEL_GROUP_DELETED("NOTIFICATION_CHANNEL_GROUP_DELETED"),
        NOTIFICATION_CHANNEL_CONVERSATION_CREATED("NOTIFICATION_CHANNEL_CONVERSATION_CREATED"),
        NOTIFICATION_CHANNEL_CONVERSATION_DELETED("NOTIFICATION_CHANNEL_CONVERSATION_DELETED"),
        APP_NOTIFICATIONS_BLOCKED("APP_NOTIFICATIONS_BLOCKED"),
        APP_NOTIFICATIONS_UNBLOCKED("APP_NOTIFICATIONS_UNBLOCKED");

        private final int mId;

        NotificationChannelEvent(String str) {
            this.mId = r2;
        }

        public final int getId() {
            return this.mId;
        }
    }

    static int getLoggingImportance(NotificationChannel notificationChannel, int i) {
        if (notificationChannel.getConversationId() == null || i < 4 || !notificationChannel.isImportantConversation()) {
            return i;
        }
        return 5;
    }

    default void logNotificationChannelModified(NotificationChannel notificationChannel, int i, String str, int i2, boolean z) {
        ((NotificationChannelLoggerImpl) this).logNotificationChannel(z ? NotificationChannelEvent.NOTIFICATION_CHANNEL_UPDATED_BY_USER : NotificationChannelEvent.NOTIFICATION_CHANNEL_UPDATED, notificationChannel, i, str, i2, getLoggingImportance(notificationChannel, notificationChannel.getImportance()));
    }
}
