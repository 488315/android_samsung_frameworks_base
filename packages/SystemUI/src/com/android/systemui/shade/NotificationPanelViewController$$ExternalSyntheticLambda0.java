package com.android.systemui.shade;

import android.graphics.Rect;

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
