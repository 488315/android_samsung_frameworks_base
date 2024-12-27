package com.android.systemui.qs;

import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.statusbar.phone.ui.StatusBarIconController;
import com.android.systemui.statusbar.phone.ui.TintedIconManager;
import com.android.systemui.util.ViewController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SecQuickStatusBarHeaderController extends ViewController {
    public boolean mListening;
    public final SecQuickQSPanelController mQuickQSPanelController;

    public SecQuickStatusBarHeaderController(SecQuickStatusBarHeader secQuickStatusBarHeader, StatusBarIconController statusBarIconController, DemoModeController demoModeController, SecQuickQSPanelController secQuickQSPanelController, FeatureFlags featureFlags, TintedIconManager.Factory factory, ShadeHeaderController shadeHeaderController) {
        super(secQuickStatusBarHeader);
        this.mQuickQSPanelController = secQuickQSPanelController;
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        setListening(false);
    }

    public final void setListening(boolean z) {
        if (z == this.mListening) {
            return;
        }
        this.mListening = z;
        this.mQuickQSPanelController.setListening(z);
        ((SecQuickStatusBarHeader) this.mView).updateContentsPadding();
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
    }
}
