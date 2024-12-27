package com.android.systemui.statusbar.policy;

import android.view.accessibility.AccessibilityManager;

public final class AccessibilityManagerWrapper implements CallbackController {
    public final AccessibilityManager mAccessibilityManager;

    public AccessibilityManagerWrapper(AccessibilityManager accessibilityManager) {
        this.mAccessibilityManager = accessibilityManager;
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        this.mAccessibilityManager.addAccessibilityServicesStateChangeListener((AccessibilityManager.AccessibilityServicesStateChangeListener) obj);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        this.mAccessibilityManager.removeAccessibilityServicesStateChangeListener((AccessibilityManager.AccessibilityServicesStateChangeListener) obj);
    }
}
