package com.android.systemui.statusbar.notification;

import android.app.Notification;
import android.content.pm.LauncherApps;
import android.content.pm.ShortcutInfo;
import com.android.systemui.statusbar.notification.ConversationNotificationManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ConversationNotificationProcessor {
    public final ConversationNotificationManager conversationNotificationManager;
    public final LauncherApps launcherApps;

    public ConversationNotificationProcessor(LauncherApps launcherApps, ConversationNotificationManager conversationNotificationManager) {
        this.launcherApps = launcherApps;
        this.conversationNotificationManager = conversationNotificationManager;
    }

    public final void processNotification(NotificationEntry notificationEntry, Notification.Builder builder) {
        Notification.Style style = builder.getStyle();
        Notification.MessagingStyle messagingStyle = style instanceof Notification.MessagingStyle ? (Notification.MessagingStyle) style : null;
        if (messagingStyle == null) {
            return;
        }
        messagingStyle.setConversationType(notificationEntry.mRanking.getChannel().isImportantConversation() ? 2 : 1);
        ShortcutInfo conversationShortcutInfo = notificationEntry.mRanking.getConversationShortcutInfo();
        if (conversationShortcutInfo != null) {
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
