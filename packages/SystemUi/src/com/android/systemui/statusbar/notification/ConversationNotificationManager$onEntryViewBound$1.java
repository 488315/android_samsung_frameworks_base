package com.android.systemui.statusbar.notification;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ConversationNotificationManager$onEntryViewBound$1 {
    public final /* synthetic */ NotificationEntry $entry;
    public final /* synthetic */ ConversationNotificationManager this$0;

    public ConversationNotificationManager$onEntryViewBound$1(NotificationEntry notificationEntry, ConversationNotificationManager conversationNotificationManager) {
        this.$entry = notificationEntry;
        this.this$0 = conversationNotificationManager;
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x000b, code lost:
    
        if (r1.isShown() == true) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onExpansionChanged(final boolean z) {
        final NotificationEntry notificationEntry = this.$entry;
        ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
        boolean z2 = expandableNotificationRow != null;
        final ConversationNotificationManager conversationNotificationManager = this.this$0;
        if (!z2 || !z) {
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
