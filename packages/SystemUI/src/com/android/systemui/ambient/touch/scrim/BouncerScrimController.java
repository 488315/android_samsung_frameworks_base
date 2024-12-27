package com.android.systemui.ambient.touch.scrim;

import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
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
