package com.android.systemui.statusbar.policy;

import android.view.accessibility.AccessibilityManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class AccessibilityManagerWrapper implements CallbackController {
    public final AccessibilityManager mAccessibilityManager;

    public AccessibilityManagerWrapper(AccessibilityManager accessibilityManager) {
        this.mAccessibilityManager = accessibilityManager;
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        this.mAccessibilityManager.addAccessibilityServicesStateChangeListener((AccessibilityManager.AccessibilityServicesStateChangeListener) obj);
    }

    public final int getRecommendedTimeoutMillis(int i, int i2) {
        return this.mAccessibilityManager.getRecommendedTimeoutMillis(i, i2);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        this.mAccessibilityManager.removeAccessibilityServicesStateChangeListener((AccessibilityManager.AccessibilityServicesStateChangeListener) obj);
    }
}
