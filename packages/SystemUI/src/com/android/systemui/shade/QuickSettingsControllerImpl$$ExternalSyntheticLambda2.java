package com.android.systemui.shade;

import com.android.systemui.plugins.qs.QS;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
