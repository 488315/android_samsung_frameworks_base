package com.samsung.android.globalactions.util;

import android.content.Context;
import android.view.accessibility.AccessibilityManager;

/* loaded from: classes6.dex */
public class AccessibilityManagerWrapper {
    AccessibilityManager mAccessibilityManager;

    public AccessibilityManagerWrapper(Context context) {
        this.mAccessibilityManager = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
    }

    public boolean isVoiceAssistantMode() {
        AccessibilityManager accessibilityManager = this.mAccessibilityManager;
        if (accessibilityManager != null) {
            return accessibilityManager.semIsAccessibilityServiceEnabled(32) || this.mAccessibilityManager.semIsAccessibilityServiceEnabled(16);
        }
        return false;
    }
}
