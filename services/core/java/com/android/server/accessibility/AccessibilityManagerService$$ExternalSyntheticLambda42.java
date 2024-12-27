package com.android.server.accessibility;

import android.content.ComponentName;

import java.util.function.Predicate;

public final /* synthetic */ class AccessibilityManagerService$$ExternalSyntheticLambda42
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ComponentName f$0;

    public /* synthetic */ AccessibilityManagerService$$ExternalSyntheticLambda42(
            int i, ComponentName componentName) {
        this.$r8$classId = i;
        this.f$0 = componentName;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        ComponentName componentName = this.f$0;
        switch (i) {
            case 0:
                return componentName.equals((ComponentName) obj);
            case 1:
                return componentName.equals(ComponentName.unflattenFromString((String) obj));
            default:
                int i2 = AccessibilityManagerService.ManagerPackageMonitor.$r8$clinit;
                return ((String) obj).equals(componentName.getPackageName());
        }
    }
}
