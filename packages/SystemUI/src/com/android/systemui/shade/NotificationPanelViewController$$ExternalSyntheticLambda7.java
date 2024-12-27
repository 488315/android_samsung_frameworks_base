package com.android.systemui.shade;

import com.android.systemui.statusbar.phone.KeyguardBottomAreaView;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
