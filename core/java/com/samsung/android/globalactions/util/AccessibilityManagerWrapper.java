package com.samsung.android.globalactions.util;

import android.content.Context;
import android.view.accessibility.AccessibilityManager;

public class AccessibilityManagerWrapper {
    AccessibilityManager mAccessibilityManager;

    public AccessibilityManagerWrapper(Context context) {
        this.mAccessibilityManager =
                (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
    }

    public boolean isVoiceAssistantMode() {
        return this.mAccessibilityManager != null
                && (this.mAccessibilityManager.semIsAccessibilityServiceEnabled(32)
                        || this.mAccessibilityManager.semIsAccessibilityServiceEnabled(16));
    }
}
