package com.android.server.pm;

import android.os.UserManager;

import com.android.internal.util.FunctionalUtils;

public final /* synthetic */ class CrossProfileAppsServiceImpl$$ExternalSyntheticLambda1
        implements FunctionalUtils.ThrowingSupplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CrossProfileAppsServiceImpl f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ CrossProfileAppsServiceImpl$$ExternalSyntheticLambda1(
            CrossProfileAppsServiceImpl crossProfileAppsServiceImpl, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = crossProfileAppsServiceImpl;
        this.f$1 = i;
    }

    public final Object getOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                CrossProfileAppsServiceImpl crossProfileAppsServiceImpl = this.f$0;
                return Boolean.valueOf(
                        ((UserManager)
                                        crossProfileAppsServiceImpl.mContext.getSystemService(
                                                UserManager.class))
                                .isManagedProfile(this.f$1));
            default:
                CrossProfileAppsServiceImpl crossProfileAppsServiceImpl2 = this.f$0;
                return crossProfileAppsServiceImpl2
                        .mInjector
                        .getDevicePolicyManagerInternal()
                        .getProfileOwnerAsUser(this.f$1);
        }
    }
}
