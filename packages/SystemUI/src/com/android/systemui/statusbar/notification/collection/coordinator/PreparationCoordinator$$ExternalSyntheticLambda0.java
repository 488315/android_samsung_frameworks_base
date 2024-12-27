package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.inflation.NotifInflater;
import com.android.systemui.statusbar.notification.collection.render.NotifViewController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
