package com.android.keyguard.biometrics;

import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;

public final class KeyguardBiometricViewController$setLockIconOnClickListener$2 extends View.AccessibilityDelegate {
    @Override // android.view.View.AccessibilityDelegate
    public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
        accessibilityNodeInfo.removeAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
    }
}
