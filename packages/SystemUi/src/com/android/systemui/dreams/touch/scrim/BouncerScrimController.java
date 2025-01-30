package com.android.systemui.dreams.touch.scrim;

import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BouncerScrimController implements ScrimController {
    public final StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;

    public BouncerScrimController(StatusBarKeyguardViewManager statusBarKeyguardViewManager) {
        this.mStatusBarKeyguardViewManager = statusBarKeyguardViewManager;
    }

    @Override // com.android.systemui.dreams.touch.scrim.ScrimController
    public final void expand(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
        this.mStatusBarKeyguardViewManager.onPanelExpansionChanged(shadeExpansionChangeEvent);
    }

    @Override // com.android.systemui.dreams.touch.scrim.ScrimController
    public final void reset() {
        this.mStatusBarKeyguardViewManager.reset(false);
    }

    @Override // com.android.systemui.dreams.touch.scrim.ScrimController
    public final void show() {
        this.mStatusBarKeyguardViewManager.showPrimaryBouncer(false);
    }
}
