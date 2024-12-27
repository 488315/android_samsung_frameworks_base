package com.android.systemui.ambient.touch.scrim;

import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;

public final class BouncerScrimController implements ScrimController {
    public final StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;

    public BouncerScrimController(StatusBarKeyguardViewManager statusBarKeyguardViewManager) {
        this.mStatusBarKeyguardViewManager = statusBarKeyguardViewManager;
    }

    @Override // com.android.systemui.ambient.touch.scrim.ScrimController
    public final void expand(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
        this.mStatusBarKeyguardViewManager.onPanelExpansionChanged(shadeExpansionChangeEvent);
    }

    @Override // com.android.systemui.ambient.touch.scrim.ScrimController
    public final void reset$1() {
        this.mStatusBarKeyguardViewManager.reset(false);
    }

    @Override // com.android.systemui.ambient.touch.scrim.ScrimController
    public final void show() {
        this.mStatusBarKeyguardViewManager.showPrimaryBouncer(false);
    }
}
