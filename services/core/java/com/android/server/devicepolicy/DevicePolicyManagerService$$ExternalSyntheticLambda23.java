package com.android.server.devicepolicy;

import com.android.internal.util.FunctionalUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
