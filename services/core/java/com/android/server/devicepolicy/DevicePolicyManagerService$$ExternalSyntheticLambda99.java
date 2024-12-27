package com.android.server.devicepolicy;

import android.app.admin.IntegerPolicyValue;
import android.app.admin.PackageSetPolicyValue;
import android.util.ArraySet;

import java.util.List;
import java.util.function.BiConsumer;

public final /* synthetic */ class DevicePolicyManagerService$$ExternalSyntheticLambda99
        implements BiConsumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DevicePolicyManagerService f$0;

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda99(
            DevicePolicyManagerService devicePolicyManagerService, int i) {
        this.$r8$classId = i;
        this.f$0 = devicePolicyManagerService;
    }

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        int i = this.$r8$classId;
        DevicePolicyManagerService devicePolicyManagerService = this.f$0;
        ActiveAdmin activeAdmin = (ActiveAdmin) obj;
        switch (i) {
            case 0:
                EnforcingAdmin enforcingAdmin = (EnforcingAdmin) obj2;
                devicePolicyManagerService.getClass();
                int i2 = enforcingAdmin.mUserId;
                if (activeAdmin.mPasswordComplexity != 0) {
                    devicePolicyManagerService.mDevicePolicyEngine.setLocalPolicy(
                            PolicyDefinition.PASSWORD_COMPLEXITY,
                            enforcingAdmin,
                            new IntegerPolicyValue(activeAdmin.mPasswordComplexity),
                            i2,
                            false);
                }
                ActiveAdmin parentActiveAdmin = activeAdmin.getParentActiveAdmin();
                if (parentActiveAdmin != null && parentActiveAdmin.mPasswordComplexity != 0) {
                    devicePolicyManagerService.mDevicePolicyEngine.setLocalPolicy(
                            PolicyDefinition.PASSWORD_COMPLEXITY,
                            enforcingAdmin,
                            new IntegerPolicyValue(parentActiveAdmin.mPasswordComplexity),
                            devicePolicyManagerService.getProfileParentId(i2),
                            false);
                    break;
                }
                break;
            default:
                EnforcingAdmin enforcingAdmin2 = (EnforcingAdmin) obj2;
                devicePolicyManagerService.getClass();
                List list = activeAdmin.suspendedPackages;
                if (list != null && list.size() != 0) {
                    devicePolicyManagerService.mDevicePolicyEngine.setLocalPolicy(
                            PolicyDefinition.PACKAGES_SUSPENDED,
                            enforcingAdmin2,
                            new PackageSetPolicyValue(new ArraySet(activeAdmin.suspendedPackages)),
                            enforcingAdmin2.mUserId,
                            false);
                    break;
                }
                break;
        }
    }
}
