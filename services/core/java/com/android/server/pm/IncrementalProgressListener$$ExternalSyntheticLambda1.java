package com.android.server.pm;

import com.android.server.pm.pkg.mutate.PackageStateMutator;

import java.util.function.Consumer;

public final /* synthetic */ class IncrementalProgressListener$$ExternalSyntheticLambda1
        implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        long currentTimeMillis = System.currentTimeMillis();
        PackageSetting packageSetting = ((PackageStateMutator.StateWriteWrapper) obj).mState;
        if (packageSetting != null) {
            packageSetting.mLoadingCompletedTime = currentTimeMillis;
            packageSetting.onChanged$2();
        }
    }
}
