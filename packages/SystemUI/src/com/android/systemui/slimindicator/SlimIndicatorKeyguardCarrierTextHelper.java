package com.android.systemui.slimindicator;

import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.CarrierText;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class SlimIndicatorKeyguardCarrierTextHelper implements SlimIndicatorViewSubscriber {
    public CarrierText mCarrierTextView;
    public int mOriginalVisibility;
    public final SlimIndicatorViewMediator mSlimIndicatorViewMediator;

    public SlimIndicatorKeyguardCarrierTextHelper(SlimIndicatorViewMediator slimIndicatorViewMediator) {
        this.mSlimIndicatorViewMediator = slimIndicatorViewMediator;
    }

    @Override // com.android.systemui.slimindicator.SlimIndicatorViewSubscriber
    public final void updateQuickStarStyle() {
        RecyclerView$$ExternalSyntheticOutline0.m(this.mOriginalVisibility, "SlimIndicatorKeyguardCarrierTextHelper", new StringBuilder("updateQuickStarStyle() visibility:"));
        CarrierText carrierText = this.mCarrierTextView;
        if (carrierText == null) {
            return;
        }
        carrierText.setVisibility(this.mOriginalVisibility);
    }
}
