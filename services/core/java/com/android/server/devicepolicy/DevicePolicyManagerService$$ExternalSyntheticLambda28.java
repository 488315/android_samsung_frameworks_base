package com.android.server.devicepolicy;

import android.os.PowerManagerInternal;

import com.android.internal.util.FunctionalUtils;
import com.android.server.LocalServices;

public final /* synthetic */ class DevicePolicyManagerService$$ExternalSyntheticLambda28
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DevicePolicyManagerService f$0;
    public final /* synthetic */ DevicePolicyData f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda28(
            DevicePolicyManagerService devicePolicyManagerService,
            int i,
            DevicePolicyData devicePolicyData) {
        this.$r8$classId = 1;
        this.f$0 = devicePolicyManagerService;
        this.f$2 = i;
        this.f$1 = devicePolicyData;
    }

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda28(
            DevicePolicyManagerService devicePolicyManagerService,
            DevicePolicyData devicePolicyData,
            int i,
            int i2) {
        this.$r8$classId = i2;
        this.f$0 = devicePolicyManagerService;
        this.f$1 = devicePolicyData;
        this.f$2 = i;
    }

    public final void runOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                DevicePolicyManagerService devicePolicyManagerService = this.f$0;
                DevicePolicyData devicePolicyData = this.f$1;
                int i = this.f$2;
                devicePolicyManagerService.getClass();
                devicePolicyData.mFailedPasswordAttempts = 0;
                devicePolicyData.mFailedPasswordAttemptsFromGateKeeper = 0;
                devicePolicyManagerService.mInjector.getClass();
                if (i != 0 && devicePolicyManagerService.isManagedProfile(i)) {
                    devicePolicyData.mFailedBiometricAttempts = 0;
                }
                devicePolicyData.mPasswordOwner = -1;
                devicePolicyManagerService.saveSettingsLocked(i, false, false, false);
                if (devicePolicyManagerService.mHasFeature) {
                    devicePolicyManagerService.sendAdminCommandForLockscreenPoliciesLocked(
                            1, i, "android.app.action.ACTION_PASSWORD_SUCCEEDED");
                    break;
                }
                break;
            case 1:
                DevicePolicyManagerService devicePolicyManagerService2 = this.f$0;
                int i2 = this.f$2;
                DevicePolicyData devicePolicyData2 = this.f$1;
                devicePolicyManagerService2.mInjector.getClass();
                ((PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class))
                        .setMaximumScreenOffTimeoutFromDeviceAdmin(
                                i2, devicePolicyData2.mLastMaximumTimeToLock);
                break;
            default:
                DevicePolicyManagerService devicePolicyManagerService3 = this.f$0;
                DevicePolicyData devicePolicyData3 = this.f$1;
                int i3 = this.f$2;
                devicePolicyManagerService3.getClass();
                devicePolicyData3.mFailedBiometricAttempts = 0;
                devicePolicyManagerService3.saveSettingsLocked(i3, false, false, false);
                break;
        }
    }
}
