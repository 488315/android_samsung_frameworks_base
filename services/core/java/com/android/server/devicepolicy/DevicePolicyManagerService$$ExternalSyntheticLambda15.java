package com.android.server.devicepolicy;

import android.app.admin.DevicePolicyDrawableResource;
import android.app.admin.DevicePolicyStringResource;
import android.content.pm.PackageInfo;
import android.view.accessibility.AccessibilityManager;

import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class DevicePolicyManagerService$$ExternalSyntheticLambda15
        implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((AccessibilityManager) obj).getInstalledAccessibilityServiceList();
            case 1:
                return Integer.valueOf(((ActiveAdmin) obj).mPasswordPolicy.lowerCase);
            case 2:
                return ((PackageInfo) obj).packageName;
            case 3:
                return Integer.valueOf(((ActiveAdmin) obj).mPasswordPolicy.letters);
            case 4:
                return ((AccessibilityManager) obj).getEnabledAccessibilityServiceList(-1);
            case 5:
                return Integer.valueOf(((ActiveAdmin) obj).mPasswordPolicy.nonLetter);
            case 6:
                return Integer.valueOf(((ActiveAdmin) obj).mPasswordPolicy.upperCase);
            case 7:
                return Integer.valueOf(((ActiveAdmin) obj).passwordHistoryLength);
            case 8:
                return Integer.valueOf(((ActiveAdmin) obj).mPasswordPolicy.numeric);
            case 9:
                return ((DevicePolicyDrawableResource) obj).getDrawableId();
            case 10:
                return ((DevicePolicyStringResource) obj).getStringId();
            case 11:
                return Integer.valueOf(((ActiveAdmin) obj).mPasswordPolicy.length);
            case 12:
                return Integer.valueOf(((ActiveAdmin) obj).mPasswordPolicy.symbols);
            default:
                return ((PackageInfo) obj).packageName;
        }
    }
}
