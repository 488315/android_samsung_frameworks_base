package com.android.server.devicepolicy;

import android.content.pm.UserInfo;

import java.util.function.Predicate;

public final /* synthetic */ class DevicePolicyManagerService$$ExternalSyntheticLambda123
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DevicePolicyManagerService f$0;

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda123(
            DevicePolicyManagerService devicePolicyManagerService, int i) {
        this.$r8$classId = i;
        this.f$0 = devicePolicyManagerService;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        DevicePolicyManagerService devicePolicyManagerService = this.f$0;
        UserInfo userInfo = (UserInfo) obj;
        switch (i) {
            case 0:
                return !devicePolicyManagerService.mLockPatternUtils
                        .isSeparateProfileChallengeEnabled(userInfo.id);
            case 1:
                return !devicePolicyManagerService.mLockPatternUtils
                        .isSeparateProfileChallengeEnabled(userInfo.id);
            default:
                return devicePolicyManagerService.mLockPatternUtils.isProfileWithUnifiedChallenge(
                        userInfo.id);
        }
    }
}
