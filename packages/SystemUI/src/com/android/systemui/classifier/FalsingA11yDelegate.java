package com.android.systemui.classifier;

import android.os.Bundle;
import android.view.View;

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
