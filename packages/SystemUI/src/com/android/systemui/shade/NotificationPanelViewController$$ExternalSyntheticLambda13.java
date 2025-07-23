package com.android.systemui.shade;

import android.graphics.Rect;
import java.util.function.BooleanSupplier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationPanelViewController$$ExternalSyntheticLambda13 implements BooleanSupplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationPanelViewController f$0;

    public /* synthetic */ NotificationPanelViewController$$ExternalSyntheticLambda13(NotificationPanelViewController notificationPanelViewController, int i) {
        this.$r8$classId = i;
        this.f$0 = notificationPanelViewController;
    }

    @Override // java.util.function.BooleanSupplier
    public final boolean getAsBoolean() {
        int i = this.$r8$classId;
        NotificationPanelViewController notificationPanelViewController = this.f$0;
        switch (i) {
            case 0:
                return notificationPanelViewController.isTracking();
            case 1:
                Rect rect = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
                return notificationPanelViewController.isOnKeyguard();
            default:
                return notificationPanelViewController.mQsController.mFullyExpanded;
        }
    }
}
