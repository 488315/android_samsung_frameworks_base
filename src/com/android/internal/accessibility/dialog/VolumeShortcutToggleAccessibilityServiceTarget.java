package com.android.internal.accessibility.dialog;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;

/* loaded from: classes5.dex */
class VolumeShortcutToggleAccessibilityServiceTarget extends AccessibilityServiceTarget {
    VolumeShortcutToggleAccessibilityServiceTarget(Context context, int i, AccessibilityServiceInfo accessibilityServiceInfo) {
        super(context, i, 0, accessibilityServiceInfo);
    }

    @Override // com.android.internal.accessibility.dialog.AccessibilityTarget, com.android.internal.accessibility.dialog.OnTargetCheckedChangeListener
    public void onCheckedChanged(boolean z) {
        if (getShortcutType() == 2) {
            super.onCheckedChanged(z);
            return;
        }
        throw new IllegalStateException("Unexpected shortcut type");
    }
}
