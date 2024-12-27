package com.android.server.pm;

import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class InstallPackageHelper$$ExternalSyntheticLambda8 implements Function {
    public final /* synthetic */ InstallPackageHelper f$0;

    public /* synthetic */ InstallPackageHelper$$ExternalSyntheticLambda8(InstallPackageHelper installPackageHelper) {
        this.f$0 = installPackageHelper;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return this.f$0.mPm.mUserManager.getUserInfo(((Integer) obj).intValue());
    }
}
