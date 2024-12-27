package com.android.systemui.slimindicator;

import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.CarrierText;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        RecyclerView$$ExternalSyntheticOutline0.m(this.mOriginalVisibility, "SlimIndicatorKeyguardCarrierTextHelper", new StringBuilder("updateQuickStarStyle() visibility:"));
        CarrierText carrierText = this.mCarrierTextView;
        if (carrierText == null) {
            return;
        }
        carrierText.setVisibility(this.mOriginalVisibility);
    }
}
