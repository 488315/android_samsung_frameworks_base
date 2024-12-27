package com.android.systemui.biometrics.ui.binder;

import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import com.android.systemui.biometrics.Utils;

public final class BiometricViewSizeBinder {
    public static final BiometricViewSizeBinder INSTANCE = new BiometricViewSizeBinder();

    private BiometricViewSizeBinder() {
    }

    public static final void access$bind$notifyAccessibilityChanged(AccessibilityManager accessibilityManager, View view) {
        ViewGroup viewGroup = (ViewGroup) view;
        int i = Utils.$r8$clinit;
        if (accessibilityManager.isEnabled()) {
            AccessibilityEvent obtain = AccessibilityEvent.obtain();
            obtain.setEventType(2048);
            obtain.setContentChangeTypes(1);
            viewGroup.sendAccessibilityEventUnchecked(obtain);
            viewGroup.notifySubtreeAccessibilityStateChanged(viewGroup, viewGroup, 1);
        }
    }
}
