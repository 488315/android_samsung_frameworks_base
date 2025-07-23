package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class HeadsUpCoordinator$attach$4 {
    final /* synthetic */ HeadsUpCoordinator this$0;

    public HeadsUpCoordinator$attach$4(HeadsUpCoordinator headsUpCoordinator) {
        this.this$0 = headsUpCoordinator;
    }

    public void turnToHeadsUp(NotificationEntry notificationEntry) {
        this.this$0.bindForAsyncHeadsUp(notificationEntry);
    }
}
