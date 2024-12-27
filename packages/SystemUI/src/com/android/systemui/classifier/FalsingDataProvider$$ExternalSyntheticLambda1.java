package com.android.systemui.classifier;

import java.util.ArrayList;
import java.util.function.Consumer;

public final /* synthetic */ class FalsingDataProvider$$ExternalSyntheticLambda1 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        BrightLineFalsingManager brightLineFalsingManager = BrightLineFalsingManager.this;
        brightLineFalsingManager.mLastProximityEvent = null;
        ((ArrayList) brightLineFalsingManager.mHistoryTracker.mBeliefListeners).remove(brightLineFalsingManager.mBeliefListener);
        brightLineFalsingManager.mClassifiers.forEach(new BrightLineFalsingManager$$ExternalSyntheticLambda0(2));
    }
}
