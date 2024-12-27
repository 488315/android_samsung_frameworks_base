package com.android.server.pm;

import com.android.server.pm.pkg.mutate.PackageStateMutator;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class IncrementalProgressListener$$ExternalSyntheticLambda1 implements Consumer {
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
