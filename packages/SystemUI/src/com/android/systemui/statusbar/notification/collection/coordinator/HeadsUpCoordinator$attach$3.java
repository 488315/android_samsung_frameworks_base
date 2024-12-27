package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;

public final class HeadsUpCoordinator$attach$3 {
    final /* synthetic */ HeadsUpCoordinator this$0;

    public HeadsUpCoordinator$attach$3(HeadsUpCoordinator headsUpCoordinator) {
        this.this$0 = headsUpCoordinator;
    }

    public void turnToHeadsUp(NotificationEntry notificationEntry) {
        this.this$0.bindForAsyncHeadsUp(notificationEntry);
    }
}
