package com.android.server.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.ComponentName;

import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
