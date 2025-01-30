package com.android.systemui.shade;

import java.util.function.BooleanSupplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationPanelViewController$$ExternalSyntheticLambda17 implements BooleanSupplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationPanelViewController f$0;

    public /* synthetic */ NotificationPanelViewController$$ExternalSyntheticLambda17(NotificationPanelViewController notificationPanelViewController, int i) {
        this.$r8$classId = i;
        this.f$0 = notificationPanelViewController;
    }

    @Override // java.util.function.BooleanSupplier
    public final boolean getAsBoolean() {
        int i = this.$r8$classId;
        NotificationPanelViewController notificationPanelViewController = this.f$0;
        switch (i) {
            case 0:
                return notificationPanelViewController.isOnKeyguard();
            case 1:
                return notificationPanelViewController.mHeightAnimator != null;
            case 2:
                return notificationPanelViewController.isKeyguardShowing();
            case 3:
                return notificationPanelViewController.mTracking;
            case 4:
                return notificationPanelViewController.mPanelExpanded;
            default:
                return notificationPanelViewController.mQsController.mFullyExpanded;
        }
    }
}
