package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.inflation.NotifInflater;
import com.android.systemui.statusbar.notification.collection.render.NotifViewController;

public final /* synthetic */ class PreparationCoordinator$$ExternalSyntheticLambda0 implements NotifInflater.InflationCallback {
    public final /* synthetic */ PreparationCoordinator f$0;

    public /* synthetic */ PreparationCoordinator$$ExternalSyntheticLambda0(PreparationCoordinator preparationCoordinator) {
        this.f$0 = preparationCoordinator;
    }

    @Override // com.android.systemui.statusbar.notification.collection.inflation.NotifInflater.InflationCallback
    public final void onInflationFinished(NotificationEntry notificationEntry, NotifViewController notifViewController) {
        this.f$0.onInflationFinished(notificationEntry, notifViewController);
    }
}
