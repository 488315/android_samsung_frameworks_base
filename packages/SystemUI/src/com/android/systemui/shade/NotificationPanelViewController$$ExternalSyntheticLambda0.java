package com.android.systemui.shade;

import android.graphics.Rect;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationPanelViewController$$ExternalSyntheticLambda0 {
    public final /* synthetic */ NotificationPanelViewController f$0;

    public void onExpansionHeightSetToMax(boolean z) {
        Rect rect = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
        NotificationPanelViewController notificationPanelViewController = this.f$0;
        if (z) {
            notificationPanelViewController.requestScrollerTopPaddingUpdate();
        }
        notificationPanelViewController.updateExpandedHeightToMaxHeight();
    }
}
