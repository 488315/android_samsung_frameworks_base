package com.android.systemui.ambient.touch.scrim;

import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class BouncerScrimController implements ScrimController {
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
    public final void show$2() {
        this.mStatusBarKeyguardViewManager.showPrimaryBouncer("BouncerScrimController#show", false);
    }
}
