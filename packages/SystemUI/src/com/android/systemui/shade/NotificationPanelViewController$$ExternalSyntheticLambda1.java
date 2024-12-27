package com.android.systemui.shade;

import android.graphics.Rect;
import java.util.function.BooleanSupplier;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationPanelViewController$$ExternalSyntheticLambda1 implements BooleanSupplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationPanelViewController f$0;

    public /* synthetic */ NotificationPanelViewController$$ExternalSyntheticLambda1(NotificationPanelViewController notificationPanelViewController, int i) {
        this.$r8$classId = i;
        this.f$0 = notificationPanelViewController;
    }

    @Override // java.util.function.BooleanSupplier
    public final boolean getAsBoolean() {
        int i = this.$r8$classId;
        NotificationPanelViewController notificationPanelViewController = this.f$0;
        switch (i) {
            case 0:
                return notificationPanelViewController.isOnKeyguard$1();
            case 1:
                return notificationPanelViewController.isTracking();
            case 2:
                return notificationPanelViewController.mQsController.mFullyExpanded;
            default:
                Rect rect = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
                return notificationPanelViewController.isOnKeyguard$1();
        }
    }
}
