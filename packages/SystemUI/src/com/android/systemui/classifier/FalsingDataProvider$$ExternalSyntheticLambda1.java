package com.android.systemui.classifier;

import com.android.systemui.classifier.BrightLineFalsingManager;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class FalsingDataProvider$$ExternalSyntheticLambda1 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        BrightLineFalsingManager brightLineFalsingManager = BrightLineFalsingManager.this;
        brightLineFalsingManager.mLastProximityEvent = null;
        HistoryTracker historyTracker = brightLineFalsingManager.mHistoryTracker;
        BrightLineFalsingManager.AnonymousClass2 anonymousClass2 = brightLineFalsingManager.mBeliefListener;
        if (anonymousClass2 != null) {
            historyTracker.mBeliefListeners.remove(anonymousClass2);
        } else {
            historyTracker.getClass();
        }
        brightLineFalsingManager.mClassifiers.forEach(new BrightLineFalsingManager$$ExternalSyntheticLambda0(2));
    }
}
