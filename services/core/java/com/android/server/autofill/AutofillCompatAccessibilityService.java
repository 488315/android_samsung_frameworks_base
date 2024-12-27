package com.android.server.autofill;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

public final class AutofillCompatAccessibilityService extends AccessibilityService {
    @Override // android.accessibilityservice.AccessibilityService
    public final void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {}

    @Override // android.accessibilityservice.AccessibilityService
    public final void onInterrupt() {}
}
