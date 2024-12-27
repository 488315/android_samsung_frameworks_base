package com.android.systemui.classifier;

import android.os.Bundle;
import android.view.View;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
