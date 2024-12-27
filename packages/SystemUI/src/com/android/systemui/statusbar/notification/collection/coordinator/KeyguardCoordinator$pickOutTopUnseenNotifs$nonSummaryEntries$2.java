package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

final class KeyguardCoordinator$pickOutTopUnseenNotifs$nonSummaryEntries$2 extends Lambda implements Function1 {
    public static final KeyguardCoordinator$pickOutTopUnseenNotifs$nonSummaryEntries$2 INSTANCE = new KeyguardCoordinator$pickOutTopUnseenNotifs$nonSummaryEntries$2();

    public KeyguardCoordinator$pickOutTopUnseenNotifs$nonSummaryEntries$2() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Boolean invoke(NotificationEntry notificationEntry) {
        return Boolean.valueOf(notificationEntry.mRanking.getImportance() >= 3);
    }
}
