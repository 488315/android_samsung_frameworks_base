package com.samsung.android.util;

import android.content.Context;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;

/* loaded from: classes6.dex */
public class SemA11yEvent {
    public static void sendA11yEvent(Context context, String str, String str2) {
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        if (accessibilityManager == null || !accessibilityManager.isEnabled()) {
            return;
        }
        AccessibilityEvent accessibilityEvent = new AccessibilityEvent();
        accessibilityEvent.setEventType(8);
        accessibilityEvent.getText().clear();
        accessibilityEvent.getText().add(str2);
        accessibilityEvent.setPackageName(str);
        accessibilityManager.sendAccessibilityEvent(accessibilityEvent);
    }
}
