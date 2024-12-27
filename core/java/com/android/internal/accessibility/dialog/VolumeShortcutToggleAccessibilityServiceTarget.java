package com.android.internal.accessibility.dialog;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;

class VolumeShortcutToggleAccessibilityServiceTarget extends AccessibilityServiceTarget {
    VolumeShortcutToggleAccessibilityServiceTarget(
            Context context, int shortcutType, AccessibilityServiceInfo serviceInfo) {
        super(context, shortcutType, 0, serviceInfo);
    }

    @Override // com.android.internal.accessibility.dialog.AccessibilityTarget,
              // com.android.internal.accessibility.dialog.OnTargetCheckedChangeListener
    public void onCheckedChanged(boolean isChecked) {
        if (getShortcutType() == 2) {
            super.onCheckedChanged(isChecked);
            return;
        }
        throw new IllegalStateException("Unexpected shortcut type");
    }
}
