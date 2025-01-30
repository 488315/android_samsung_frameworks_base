package com.android.systemui.shade;

import com.android.systemui.plugins.p013qs.InterfaceC1922QS;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class QuickSettingsController$$ExternalSyntheticLambda2 implements InterfaceC1922QS.HeightListener, InterfaceC1922QS.ScrollListener {
    public final /* synthetic */ QuickSettingsController f$0;

    public /* synthetic */ QuickSettingsController$$ExternalSyntheticLambda2(QuickSettingsController quickSettingsController) {
        this.f$0 = quickSettingsController;
    }

    @Override // com.android.systemui.plugins.p013qs.InterfaceC1922QS.HeightListener
    public final void onQsHeightChanged() {
        this.f$0.onHeightChanged();
    }

    @Override // com.android.systemui.plugins.p013qs.InterfaceC1922QS.ScrollListener
    public final void onQsPanelScrollChanged(int i) {
        QuickSettingsController quickSettingsController = this.f$0;
        ShadeHeaderController shadeHeaderController = quickSettingsController.mShadeHeaderController;
        if (shadeHeaderController.qsScrollY != i) {
            shadeHeaderController.qsScrollY = i;
            if (!shadeHeaderController.largeScreenActive) {
                shadeHeaderController.header.setScrollY(i);
            }
        }
        if (i <= 0 || quickSettingsController.mFullyExpanded) {
            return;
        }
        ((NotificationPanelViewController) quickSettingsController.mPanelViewControllerLazy.get()).expandToQs();
    }
}
