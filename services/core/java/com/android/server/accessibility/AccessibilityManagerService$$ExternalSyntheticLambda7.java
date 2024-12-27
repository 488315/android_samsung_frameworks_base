package com.android.server.accessibility;

import android.content.ComponentName;

import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AccessibilityManagerService$$ExternalSyntheticLambda7
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ String f$0;

    public /* synthetic */ AccessibilityManagerService$$ExternalSyntheticLambda7(
            String str, int i) {
        this.$r8$classId = i;
        this.f$0 = str;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        String str = this.f$0;
        switch (i) {
            case 0:
                ComponentName componentName = (ComponentName) obj;
                if (componentName != null && componentName.getPackageName().equals(str)) {
                    break;
                }
                break;
            case 1:
                break;
            case 2:
                ComponentName componentName2 = (ComponentName) obj;
                int i2 = AccessibilityManagerService.ManagerPackageMonitor.$r8$clinit;
                if (componentName2 != null && componentName2.getPackageName().equals(str)) {
                    break;
                }
                break;
            default:
                ComponentName componentName3 = (ComponentName) obj;
                int i3 = AccessibilityManagerService.ManagerPackageMonitor.$r8$clinit;
                if (componentName3 != null && componentName3.getPackageName().equals(str)) {
                    break;
                }
                break;
        }
        return true;
    }
}
