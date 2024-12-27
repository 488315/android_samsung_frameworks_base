package com.android.systemui.shade;

import com.android.systemui.statusbar.phone.KeyguardBottomAreaView;

public final /* synthetic */ class NotificationPanelViewController$$ExternalSyntheticLambda7 implements KeyguardBottomAreaView.MessageDisplayer {
    public final /* synthetic */ NotificationPanelViewController f$0;

    public /* synthetic */ NotificationPanelViewController$$ExternalSyntheticLambda7(NotificationPanelViewController notificationPanelViewController) {
        this.f$0 = notificationPanelViewController;
    }

    public void onExpansionHeightSetToMax(boolean z) {
        NotificationPanelViewController notificationPanelViewController = this.f$0;
        if (z) {
            notificationPanelViewController.requestScrollerTopPaddingUpdate(false);
        }
        notificationPanelViewController.updateExpandedHeightToMaxHeight();
    }
}
