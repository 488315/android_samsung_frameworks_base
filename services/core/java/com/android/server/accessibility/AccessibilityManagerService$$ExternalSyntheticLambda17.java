package com.android.server.accessibility;

import android.content.ComponentName;
import android.util.ArraySet;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public final /* synthetic */ class AccessibilityManagerService$$ExternalSyntheticLambda17
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ AccessibilityManagerService$$ExternalSyntheticLambda17(
            int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                return ((Map) obj2).containsKey(ComponentName.unflattenFromString((String) obj));
            case 1:
                return ((List) obj2)
                        .stream()
                                .anyMatch(
                                        new AccessibilityManagerService$$ExternalSyntheticLambda7(
                                                (String) obj, 1));
            case 2:
                return !((ArraySet) obj2).contains((ComponentName) obj);
            default:
                int i2 = AccessibilityManagerService.ManagerPackageMonitor.$r8$clinit;
                return Arrays.stream((String[]) obj2)
                        .anyMatch(
                                new AccessibilityManagerService$$ExternalSyntheticLambda42(
                                        2, (ComponentName) obj));
        }
    }
}
