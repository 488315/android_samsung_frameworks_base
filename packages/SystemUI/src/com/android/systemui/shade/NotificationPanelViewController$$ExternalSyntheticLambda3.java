package com.android.systemui.shade;

import java.util.function.IntSupplier;

public final /* synthetic */ class NotificationPanelViewController$$ExternalSyntheticLambda3 implements IntSupplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationPanelViewController f$0;

    public /* synthetic */ NotificationPanelViewController$$ExternalSyntheticLambda3(NotificationPanelViewController notificationPanelViewController, int i) {
        this.$r8$classId = i;
        this.f$0 = notificationPanelViewController;
    }

    @Override // java.util.function.IntSupplier
    public final int getAsInt() {
        int i = this.$r8$classId;
        NotificationPanelViewController notificationPanelViewController = this.f$0;
        switch (i) {
            case 0:
                return notificationPanelViewController.mQsController.mMinExpansionHeight;
            default:
                return notificationPanelViewController.getMaxPanelHeight();
        }
    }
}
