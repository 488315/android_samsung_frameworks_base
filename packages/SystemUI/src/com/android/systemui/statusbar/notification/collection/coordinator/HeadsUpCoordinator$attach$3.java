package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class HeadsUpCoordinator$attach$3 {
    final /* synthetic */ HeadsUpCoordinator this$0;

    public HeadsUpCoordinator$attach$3(HeadsUpCoordinator headsUpCoordinator) {
        this.this$0 = headsUpCoordinator;
    }

    public void turnToHeadsUp(NotificationEntry notificationEntry) {
        this.this$0.bindForAsyncHeadsUp(notificationEntry);
    }
}
