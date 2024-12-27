package com.android.server.devicepolicy;

import android.app.admin.PasswordPolicy;
import android.content.ComponentName;

import com.android.internal.util.FunctionalUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class DevicePolicyManagerService$$ExternalSyntheticLambda68
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DevicePolicyManagerService f$0;
    public final /* synthetic */ ComponentName f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ int f$3;

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda68(
            DevicePolicyManagerService devicePolicyManagerService,
            ComponentName componentName,
            int i,
            int i2,
            int i3) {
        this.$r8$classId = i3;
        this.f$0 = devicePolicyManagerService;
        this.f$1 = componentName;
        this.f$2 = i;
        this.f$3 = i2;
    }

    public final void runOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                DevicePolicyManagerService devicePolicyManagerService = this.f$0;
                ComponentName componentName = this.f$1;
                int i = this.f$2;
                int i2 = this.f$3;
                ActiveAdmin activeAdminForCallerLockedMDM =
                        devicePolicyManagerService.getActiveAdminForCallerLockedMDM(
                                0, i, componentName);
                devicePolicyManagerService.ensureMinimumQuality(
                        i, activeAdminForCallerLockedMDM, 393216, "semSetPasswordMinimumNonLetter");
                PasswordPolicy passwordPolicy = activeAdminForCallerLockedMDM.mPasswordPolicy;
                if (passwordPolicy.nonLetter != i2) {
                    passwordPolicy.nonLetter = i2;
                    devicePolicyManagerService.updatePasswordValidityCheckpointLocked(i, false);
                    devicePolicyManagerService.saveSettingsLocked(i, false, false, false);
                }
                devicePolicyManagerService.logPasswordQualitySetIfSecurityLogEnabled(
                        componentName, i, false, passwordPolicy);
                break;
            case 1:
                DevicePolicyManagerService devicePolicyManagerService2 = this.f$0;
                ComponentName componentName2 = this.f$1;
                int i3 = this.f$2;
                int i4 = this.f$3;
                ActiveAdmin activeAdminForCallerLockedMDM2 =
                        devicePolicyManagerService2.getActiveAdminForCallerLockedMDM(
                                0, i3, componentName2);
                devicePolicyManagerService2.ensureMinimumQuality(
                        i3,
                        activeAdminForCallerLockedMDM2,
                        393216,
                        "semSetPasswordMinimumUpperCase");
                PasswordPolicy passwordPolicy2 = activeAdminForCallerLockedMDM2.mPasswordPolicy;
                if (passwordPolicy2.upperCase != i4) {
                    passwordPolicy2.upperCase = i4;
                    devicePolicyManagerService2.updatePasswordValidityCheckpointLocked(i3, false);
                    devicePolicyManagerService2.saveSettingsLocked(i3, false, false, false);
                }
                devicePolicyManagerService2.logPasswordQualitySetIfSecurityLogEnabled(
                        componentName2, i3, false, passwordPolicy2);
                break;
            case 2:
                DevicePolicyManagerService devicePolicyManagerService3 = this.f$0;
                ComponentName componentName3 = this.f$1;
                int i5 = this.f$2;
                int i6 = this.f$3;
                ActiveAdmin activeAdminForCallerLockedMDM3 =
                        devicePolicyManagerService3.getActiveAdminForCallerLockedMDM(
                                0, i5, componentName3);
                PasswordPolicy passwordPolicy3 = activeAdminForCallerLockedMDM3.mPasswordPolicy;
                if (passwordPolicy3.quality != i6) {
                    passwordPolicy3.quality = i6;
                    activeAdminForCallerLockedMDM3.mPasswordComplexity = 0;
                    devicePolicyManagerService3.resetInactivePasswordRequirementsIfRPlus(
                            i5, activeAdminForCallerLockedMDM3);
                    devicePolicyManagerService3.updatePasswordValidityCheckpointLocked(i5, false);
                    devicePolicyManagerService3.updatePasswordQualityCacheForUserGroup(i5);
                    devicePolicyManagerService3.saveSettingsLocked(i5, false, false, false);
                }
                devicePolicyManagerService3.logPasswordQualitySetIfSecurityLogEnabled(
                        componentName3, i5, false, passwordPolicy3);
                break;
            case 3:
                DevicePolicyManagerService devicePolicyManagerService4 = this.f$0;
                ComponentName componentName4 = this.f$1;
                int i7 = this.f$2;
                int i8 = this.f$3;
                ActiveAdmin activeAdminForCallerLockedMDM4 =
                        devicePolicyManagerService4.getActiveAdminForCallerLockedMDM(
                                0, i7, componentName4);
                if (activeAdminForCallerLockedMDM4.passwordHistoryLength != i8) {
                    activeAdminForCallerLockedMDM4.passwordHistoryLength = i8;
                    devicePolicyManagerService4.updatePasswordValidityCheckpointLocked(i7, false);
                    devicePolicyManagerService4.saveSettingsLocked(i7, false, false, false);
                    break;
                }
                break;
            case 4:
                DevicePolicyManagerService devicePolicyManagerService5 = this.f$0;
                ComponentName componentName5 = this.f$1;
                int i9 = this.f$2;
                int i10 = this.f$3;
                ActiveAdmin activeAdminForCallerLockedMDM5 =
                        devicePolicyManagerService5.getActiveAdminForCallerLockedMDM(
                                0, i9, componentName5);
                devicePolicyManagerService5.ensureMinimumQuality(
                        i9,
                        activeAdminForCallerLockedMDM5,
                        393216,
                        "semSetPasswordMinimumLowerCase");
                PasswordPolicy passwordPolicy4 = activeAdminForCallerLockedMDM5.mPasswordPolicy;
                if (passwordPolicy4.lowerCase != i10) {
                    passwordPolicy4.lowerCase = i10;
                    devicePolicyManagerService5.updatePasswordValidityCheckpointLocked(i9, false);
                    devicePolicyManagerService5.saveSettingsLocked(i9, false, false, false);
                }
                devicePolicyManagerService5.logPasswordQualitySetIfSecurityLogEnabled(
                        componentName5, i9, false, passwordPolicy4);
                break;
            default:
                DevicePolicyManagerService devicePolicyManagerService6 = this.f$0;
                ComponentName componentName6 = this.f$1;
                int i11 = this.f$2;
                int i12 = this.f$3;
                ActiveAdmin activeAdminForCallerLockedMDM6 =
                        devicePolicyManagerService6.getActiveAdminForCallerLockedMDM(
                                0, i11, componentName6);
                devicePolicyManagerService6.ensureMinimumQuality(
                        i11, activeAdminForCallerLockedMDM6, 131072, "semSetPasswordMinimumLength");
                PasswordPolicy passwordPolicy5 = activeAdminForCallerLockedMDM6.mPasswordPolicy;
                if (passwordPolicy5.length != i12) {
                    passwordPolicy5.length = i12;
                    devicePolicyManagerService6.updatePasswordValidityCheckpointLocked(i11, false);
                    devicePolicyManagerService6.saveSettingsLocked(i11, false, false, false);
                }
                devicePolicyManagerService6.logPasswordQualitySetIfSecurityLogEnabled(
                        componentName6, i11, false, passwordPolicy5);
                break;
        }
    }
}
