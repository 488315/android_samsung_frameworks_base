package com.android.server.pm;

import java.util.function.Function;

public final /* synthetic */ class InstallPackageHelper$$ExternalSyntheticLambda8
        implements Function {
    public final /* synthetic */ InstallPackageHelper f$0;

    public /* synthetic */ InstallPackageHelper$$ExternalSyntheticLambda8(
            InstallPackageHelper installPackageHelper) {
        this.f$0 = installPackageHelper;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return this.f$0.mPm.mUserManager.getUserInfo(((Integer) obj).intValue());
    }
}
