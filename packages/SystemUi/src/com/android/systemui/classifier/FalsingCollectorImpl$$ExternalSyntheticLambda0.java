package com.android.systemui.classifier;

import android.view.MotionEvent;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class FalsingCollectorImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ FalsingDataProvider f$0;

    public /* synthetic */ FalsingCollectorImpl$$ExternalSyntheticLambda0(FalsingDataProvider falsingDataProvider) {
        this.f$0 = falsingDataProvider;
    }

    @Override // java.lang.Runnable
    public final void run() {
        FalsingDataProvider falsingDataProvider = this.f$0;
        if (falsingDataProvider.mRecentMotionEvents.isEmpty()) {
            return;
        }
        TimeLimitedMotionEventBuffer timeLimitedMotionEventBuffer = falsingDataProvider.mRecentMotionEvents;
        int actionMasked = ((MotionEvent) ((ArrayList) timeLimitedMotionEventBuffer.mMotionEvents).get(timeLimitedMotionEventBuffer.size() - 1)).getActionMasked();
        if (actionMasked == 1 || actionMasked == 3) {
            falsingDataProvider.completePriorGesture();
        }
    }
}
