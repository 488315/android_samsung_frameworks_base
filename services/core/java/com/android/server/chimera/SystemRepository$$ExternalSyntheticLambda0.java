package com.android.server.chimera;

import android.accessibilityservice.AccessibilityServiceInfo;

import java.util.function.Predicate;

public final /* synthetic */ class SystemRepository$$ExternalSyntheticLambda0 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        AccessibilityServiceInfo accessibilityServiceInfo = (AccessibilityServiceInfo) obj;
        return (accessibilityServiceInfo == null
                        || accessibilityServiceInfo.getResolveInfo() == null
                        || accessibilityServiceInfo.getResolveInfo().serviceInfo == null)
                ? false
                : true;
    }
}
