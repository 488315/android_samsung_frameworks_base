package com.android.server.devicepolicy;

import android.app.admin.PreferentialNetworkServiceConfig;
import android.content.pm.UserInfo;

import java.util.function.Predicate;

public final /* synthetic */ class DevicePolicyManagerService$$ExternalSyntheticLambda9
        implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                String[] strArr = DevicePolicyManagerService.DELEGATIONS;
                return false;
            case 1:
                return !((UserInfo) obj).isForTesting();
            case 2:
                return ((UserInfo) obj).isFull();
            case 3:
                return ((PreferentialNetworkServiceConfig) obj).isEnabled();
            default:
                String[] strArr2 = DevicePolicyManagerService.DELEGATIONS;
                return true;
        }
    }
}
