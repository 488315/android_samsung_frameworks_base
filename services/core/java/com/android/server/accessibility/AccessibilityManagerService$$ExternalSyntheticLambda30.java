package com.android.server.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;

import java.util.function.Predicate;

public final /* synthetic */ class AccessibilityManagerService$$ExternalSyntheticLambda30
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return (((AccessibilityServiceInfo) obj).flags & 256) == 256;
    }
}
