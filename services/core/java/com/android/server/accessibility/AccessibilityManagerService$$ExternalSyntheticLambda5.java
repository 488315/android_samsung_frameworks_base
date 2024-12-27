package com.android.server.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.ComponentName;

import java.util.function.Function;

public final /* synthetic */ class AccessibilityManagerService$$ExternalSyntheticLambda5
        implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return (String) obj;
            case 1:
                return ComponentName.unflattenFromString((String) obj);
            case 2:
                return ((AccessibilityServiceInfo) obj).getComponentName().flattenToString();
            case 3:
                return ComponentName.unflattenFromString((String) obj);
            case 4:
                return ((ComponentName) obj).flattenToString();
            default:
                return ComponentName.unflattenFromString((String) obj);
        }
    }
}
