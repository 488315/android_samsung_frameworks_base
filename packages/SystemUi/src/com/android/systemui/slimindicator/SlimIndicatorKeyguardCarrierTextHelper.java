package com.android.systemui.slimindicator;

import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.CarrierText;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SlimIndicatorKeyguardCarrierTextHelper implements SlimIndicatorViewSubscriber {
    public CarrierText mCarrierTextView;
    public int mOriginalVisibility;
    public final SlimIndicatorViewMediator mSlimIndicatorViewMediator;

    public SlimIndicatorKeyguardCarrierTextHelper(SlimIndicatorViewMediator slimIndicatorViewMediator) {
        this.mSlimIndicatorViewMediator = slimIndicatorViewMediator;
    }

    @Override // com.android.systemui.slimindicator.SlimIndicatorViewSubscriber
    public final void updateQuickStarStyle() {
        RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("updateQuickStarStyle() visibility:"), this.mOriginalVisibility, "SlimIndicatorKeyguardCarrierTextHelper");
        CarrierText carrierText = this.mCarrierTextView;
        if (carrierText == null) {
            return;
        }
        carrierText.setVisibility(this.mOriginalVisibility);
    }
}
