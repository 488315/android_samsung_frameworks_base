package com.android.systemui.statusbar.policy;

import android.view.accessibility.AccessibilityManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class AccessibilityManagerWrapper implements CallbackController {
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
