package com.android.systemui.shade;

import com.android.systemui.plugins.qs.QS;

public final /* synthetic */ class QuickSettingsControllerImpl$$ExternalSyntheticLambda2 implements QS.ScrollListener, QS.HeightListener {
    public final /* synthetic */ QuickSettingsControllerImpl f$0;

    public /* synthetic */ QuickSettingsControllerImpl$$ExternalSyntheticLambda2(QuickSettingsControllerImpl quickSettingsControllerImpl) {
        this.f$0 = quickSettingsControllerImpl;
    }

    @Override // com.android.systemui.plugins.qs.QS.HeightListener
    public void onQsHeightChanged() {
        this.f$0.onHeightChanged();
    }

    @Override // com.android.systemui.plugins.qs.QS.ScrollListener
    public void onQsPanelScrollChanged(int i) {
        QuickSettingsControllerImpl quickSettingsControllerImpl = this.f$0;
        ShadeHeaderController shadeHeaderController = quickSettingsControllerImpl.mShadeHeaderController;
        if (shadeHeaderController.qsScrollY != i) {
            shadeHeaderController.qsScrollY = i;
            if (!shadeHeaderController.largeScreenActive) {
                shadeHeaderController.header.setScrollY(i);
            }
        }
        if (i <= 0 || quickSettingsControllerImpl.mFullyExpanded) {
            return;
        }
        ((NotificationPanelViewController) quickSettingsControllerImpl.mPanelViewControllerLazy.get()).expandToQs();
    }
}
