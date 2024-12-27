package com.android.systemui.statusbar.notification;

import android.app.Notification;
import android.content.pm.LauncherApps;
import android.content.pm.ShortcutInfo;
import com.android.systemui.statusbar.notification.ConversationNotificationManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.NotificationRowContentBinderLogger;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ConversationNotificationProcessor {
    public final ConversationNotificationManager conversationNotificationManager;
    public final LauncherApps launcherApps;

    public ConversationNotificationProcessor(LauncherApps launcherApps, ConversationNotificationManager conversationNotificationManager) {
        this.launcherApps = launcherApps;
        this.conversationNotificationManager = conversationNotificationManager;
    }

    public final void processNotification(NotificationEntry notificationEntry, Notification.Builder builder, NotificationRowContentBinderLogger notificationRowContentBinderLogger) {
        Notification.Style style = builder.getStyle();
        Notification.MessagingStyle messagingStyle = style instanceof Notification.MessagingStyle ? (Notification.MessagingStyle) style : null;
        if (messagingStyle == null) {
            return;
        }
        messagingStyle.setConversationType(notificationEntry.mRanking.getChannel().isImportantConversation() ? 2 : 1);
        ShortcutInfo conversationShortcutInfo = notificationEntry.mRanking.getConversationShortcutInfo();
        if (conversationShortcutInfo != null) {
            notificationRowContentBinderLogger.logAsyncTaskProgress(notificationEntry, "getting shortcut icon");
            messagingStyle.setShortcutIcon(this.launcherApps.getShortcutIcon(conversationShortcutInfo));
            CharSequence label = conversationShortcutInfo.getLabel();
            if (label != null) {
                messagingStyle.setConversationTitle(label);
            }
        }
        ConversationNotificationManager conversationNotificationManager = this.conversationNotificationManager;
        Object compute = conversationNotificationManager.states.compute(notificationEntry.mKey, new ConversationNotificationManager$getUnreadCount$1(notificationEntry, conversationNotificationManager, builder));
        Intrinsics.checkNotNull(compute);
        messagingStyle.setUnreadMessageCount(((ConversationNotificationManager.ConversationState) compute).unreadCount);
    }
}
