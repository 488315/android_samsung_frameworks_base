package com.android.systemui.shade;

import java.util.function.Supplier;

public final /* synthetic */ class NotificationPanelViewController$$ExternalSyntheticLambda0 implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationPanelViewController f$0;

    public /* synthetic */ NotificationPanelViewController$$ExternalSyntheticLambda0(NotificationPanelViewController notificationPanelViewController, int i) {
        this.$r8$classId = i;
        this.f$0 = notificationPanelViewController;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        int i = this.$r8$classId;
        NotificationPanelViewController notificationPanelViewController = this.f$0;
        switch (i) {
            case 0:
                return notificationPanelViewController.mView;
            case 1:
                return notificationPanelViewController.mView;
            case 2:
                return notificationPanelViewController.mHeightAnimator;
            case 3:
                return Float.valueOf(notificationPanelViewController.mExpandedFraction);
            case 4:
                return Float.valueOf(notificationPanelViewController.getFaceWidgetAlpha());
            default:
                return Boolean.valueOf(notificationPanelViewController.mPluginLockViewMode == 1);
        }
    }
}
