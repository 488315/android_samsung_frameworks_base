package com.android.systemui.statusbar.notification;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;

public final class ConversationNotificationManager$onEntryViewBound$1 {
    public final /* synthetic */ NotificationEntry $entry;
    public final /* synthetic */ ConversationNotificationManager this$0;

    public ConversationNotificationManager$onEntryViewBound$1(NotificationEntry notificationEntry, ConversationNotificationManager conversationNotificationManager) {
        this.$entry = notificationEntry;
        this.this$0 = conversationNotificationManager;
    }

    public final void onExpansionChanged(final boolean z) {
        final NotificationEntry notificationEntry = this.$entry;
        ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
        final ConversationNotificationManager conversationNotificationManager = this.this$0;
        if (expandableNotificationRow == null || !expandableNotificationRow.isShown() || !z) {
            ConversationNotificationManager.onEntryViewBound$updateCount(z, conversationNotificationManager, notificationEntry);
            return;
        }
        ExpandableNotificationRow expandableNotificationRow2 = notificationEntry.row;
        expandableNotificationRow2.mOnIntrinsicHeightReachedRunnable = new Runnable() { // from class: com.android.systemui.statusbar.notification.ConversationNotificationManager$onEntryViewBound$1.1
            @Override // java.lang.Runnable
            public final void run() {
                ConversationNotificationManager.onEntryViewBound$updateCount(z, conversationNotificationManager, notificationEntry);
            }
        };
        if (expandableNotificationRow2.mActualHeight == expandableNotificationRow2.getIntrinsicHeight()) {
            expandableNotificationRow2.mOnIntrinsicHeightReachedRunnable.run();
            expandableNotificationRow2.mOnIntrinsicHeightReachedRunnable = null;
        }
    }
}
