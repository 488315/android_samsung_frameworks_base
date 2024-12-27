package com.android.server.pm;

import com.android.server.pm.pkg.mutate.PackageStateMutator;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */
class PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda21 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        PackageSetting packageSetting = ((PackageStateMutator.StateWriteWrapper) obj).mState;
        if (packageSetting != null) {
            packageSetting.setUpdateOwnerPackage(null);
        }
    }
}
