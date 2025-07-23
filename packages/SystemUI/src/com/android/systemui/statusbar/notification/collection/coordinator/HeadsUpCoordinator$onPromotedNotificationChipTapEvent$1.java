package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator;
import java.util.LinkedHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class HeadsUpCoordinator$onPromotedNotificationChipTapEvent$1 implements Runnable {
    final /* synthetic */ NotificationEntry $entry;
    final /* synthetic */ HeadsUpCoordinator.PostedEntry $posted;
    final /* synthetic */ HeadsUpCoordinator this$0;

    public HeadsUpCoordinator$onPromotedNotificationChipTapEvent$1(HeadsUpCoordinator headsUpCoordinator, NotificationEntry notificationEntry, HeadsUpCoordinator.PostedEntry postedEntry) {
        this.this$0 = headsUpCoordinator;
        this.$entry = notificationEntry;
        this.$posted = postedEntry;
    }

    @Override // java.lang.Runnable
    public final void run() {
        LinkedHashMap linkedHashMap;
        HeadsUpCoordinator$mNotifPromoter$1 headsUpCoordinator$mNotifPromoter$1;
        linkedHashMap = this.this$0.mPostedEntries;
        linkedHashMap.put(this.$entry.mKey, this.$posted);
        headsUpCoordinator$mNotifPromoter$1 = this.this$0.mNotifPromoter;
        headsUpCoordinator$mNotifPromoter$1.invalidateList("onPromotedNotificationChipTapEvent: " + NotificationUtilsKt.getLogKey(this.$entry));
    }
}
