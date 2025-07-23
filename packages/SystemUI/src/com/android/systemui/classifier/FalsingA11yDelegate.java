package com.android.systemui.classifier;

import android.os.Bundle;
import android.view.View;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class FalsingA11yDelegate extends View.AccessibilityDelegate {
    public final FalsingCollector falsingCollector;

    public FalsingA11yDelegate(FalsingCollector falsingCollector) {
        this.falsingCollector = falsingCollector;
    }

    @Override // android.view.View.AccessibilityDelegate
    public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
        if (i == 16) {
            this.falsingCollector.onA11yAction();
        }
        return super.performAccessibilityAction(view, i, bundle);
    }
}
