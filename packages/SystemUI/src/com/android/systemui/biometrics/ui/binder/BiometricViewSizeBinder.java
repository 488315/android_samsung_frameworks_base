package com.android.systemui.biometrics.ui.binder;

import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import com.android.systemui.biometrics.Utils;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
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
