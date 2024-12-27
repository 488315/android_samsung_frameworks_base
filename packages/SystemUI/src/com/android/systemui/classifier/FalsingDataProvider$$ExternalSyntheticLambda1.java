package com.android.systemui.classifier;

import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final /* synthetic */ class FalsingDataProvider$$ExternalSyntheticLambda1 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        BrightLineFalsingManager brightLineFalsingManager = BrightLineFalsingManager.this;
        brightLineFalsingManager.mLastProximityEvent = null;
        ((ArrayList) brightLineFalsingManager.mHistoryTracker.mBeliefListeners).remove(brightLineFalsingManager.mBeliefListener);
        brightLineFalsingManager.mClassifiers.forEach(new BrightLineFalsingManager$$ExternalSyntheticLambda0(2));
    }
}
