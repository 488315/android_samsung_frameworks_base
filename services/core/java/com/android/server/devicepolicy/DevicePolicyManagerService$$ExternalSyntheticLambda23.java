package com.android.server.devicepolicy;

import com.android.internal.util.FunctionalUtils;

public final /* synthetic */ class DevicePolicyManagerService$$ExternalSyntheticLambda23
        implements FunctionalUtils.ThrowingSupplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DevicePolicyManagerService f$0;
    public final /* synthetic */ long f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda23(
            DevicePolicyManagerService devicePolicyManagerService, long j, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = devicePolicyManagerService;
        this.f$1 = j;
        this.f$2 = i;
    }

    public final Object getOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                DevicePolicyManagerService devicePolicyManagerService = this.f$0;
                return Boolean.valueOf(
                        devicePolicyManagerService.mLockPatternUtils.isEscrowTokenActive(
                                this.f$1, this.f$2));
            default:
                DevicePolicyManagerService devicePolicyManagerService2 = this.f$0;
                long j = this.f$1;
                int i = this.f$2;
                if (j != 0) {
                    return Boolean.valueOf(
                            devicePolicyManagerService2.mLockPatternUtils.removeEscrowToken(j, i));
                }
                devicePolicyManagerService2.getClass();
                return Boolean.FALSE;
        }
    }
}
