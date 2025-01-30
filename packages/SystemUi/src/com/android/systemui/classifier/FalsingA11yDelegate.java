package com.android.systemui.classifier;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FalsingA11yDelegate extends View.AccessibilityDelegate {
    public final FalsingCollector falsingCollector;

    public FalsingA11yDelegate(FalsingCollector falsingCollector) {
        this.falsingCollector = falsingCollector;
    }

    @Override // android.view.View.AccessibilityDelegate
    public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
        if (i == 16) {
            FalsingCollectorImpl falsingCollectorImpl = (FalsingCollectorImpl) this.falsingCollector;
            MotionEvent motionEvent = falsingCollectorImpl.mPendingDownEvent;
            if (motionEvent != null) {
                motionEvent.recycle();
                falsingCollectorImpl.mPendingDownEvent = null;
            }
            FalsingDataProvider falsingDataProvider = falsingCollectorImpl.mFalsingDataProvider;
            falsingDataProvider.completePriorGesture();
            falsingDataProvider.mA11YAction = true;
        }
        return super.performAccessibilityAction(view, i, bundle);
    }
}
