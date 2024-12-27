package com.android.server.pm;

import com.android.server.pm.pkg.mutate.PackageStateMutator;

import java.util.function.Consumer;

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
