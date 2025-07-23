package com.android.systemui.statusbar.notification;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ConversationNotificationManager$onEntryViewBound$1 {
    public final /* synthetic */ NotificationEntry $entry;
    public final /* synthetic */ ConversationNotificationManager this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.notification.ConversationNotificationManager$onEntryViewBound$1$1, reason: invalid class name */
    public final class AnonymousClass1 implements Runnable {
        public final /* synthetic */ NotificationEntry $entry;
        public final /* synthetic */ boolean $isExpanded;
        public final /* synthetic */ ConversationNotificationManager this$0;

        public AnonymousClass1(boolean z, ConversationNotificationManager conversationNotificationManager, NotificationEntry notificationEntry) {
            this.$isExpanded = z;
            this.this$0 = conversationNotificationManager;
            this.$entry = notificationEntry;
        }

        @Override // java.lang.Runnable
        public final void run() {
            ConversationNotificationManager.onEntryViewBound$updateCount(this.$isExpanded, this.this$0, this.$entry);
        }
    }

    public ConversationNotificationManager$onEntryViewBound$1(NotificationEntry notificationEntry, ConversationNotificationManager conversationNotificationManager) {
        this.$entry = notificationEntry;
        this.this$0 = conversationNotificationManager;
    }

    public final void onExpansionChanged(boolean z) {
        NotificationEntry notificationEntry = this.$entry;
        ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
        ConversationNotificationManager conversationNotificationManager = this.this$0;
        if (expandableNotificationRow == null || !expandableNotificationRow.isShown() || !z) {
            ConversationNotificationManager.onEntryViewBound$updateCount(z, conversationNotificationManager, notificationEntry);
            return;
        }
        ExpandableNotificationRow expandableNotificationRow2 = notificationEntry.row;
        expandableNotificationRow2.mOnIntrinsicHeightReachedRunnable = new AnonymousClass1(z, conversationNotificationManager, notificationEntry);
        if (expandableNotificationRow2.mActualHeight == expandableNotificationRow2.getIntrinsicHeight()) {
            expandableNotificationRow2.mOnIntrinsicHeightReachedRunnable.run();
            expandableNotificationRow2.mOnIntrinsicHeightReachedRunnable = null;
        }
    }
}
