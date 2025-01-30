package com.android.systemui.biometrics;

import com.android.keyguard.BouncerPanelExpansionCalculator;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.ShadeExpansionListener;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class UdfpsKeyguardViewControllerLegacy$shadeExpansionListener$1 implements ShadeExpansionListener {
    public final /* synthetic */ UdfpsKeyguardViewControllerLegacy this$0;

    public UdfpsKeyguardViewControllerLegacy$shadeExpansionListener$1(UdfpsKeyguardViewControllerLegacy udfpsKeyguardViewControllerLegacy) {
        this.this$0 = udfpsKeyguardViewControllerLegacy;
    }

    @Override // com.android.systemui.shade.ShadeExpansionListener
    public final void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
        UdfpsKeyguardViewControllerLegacy udfpsKeyguardViewControllerLegacy = this.this$0;
        boolean isPrimaryBouncerInTransit = udfpsKeyguardViewControllerLegacy.keyguardViewManager.isPrimaryBouncerInTransit();
        float f = shadeExpansionChangeEvent.fraction;
        if (isPrimaryBouncerInTransit) {
            f = BouncerPanelExpansionCalculator.aboutToShowBouncerProgress(f);
        }
        udfpsKeyguardViewControllerLegacy.panelExpansionFraction = f;
        udfpsKeyguardViewControllerLegacy.updateAlpha();
        udfpsKeyguardViewControllerLegacy.updatePauseAuth();
    }
}
